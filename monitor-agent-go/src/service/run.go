package service

import (
	"encoding/json"
	"os/exec"
	"bytes"
	"time"
	"util/commonUtil"
	"strings"
	"util/monitorUtil"
	"strconv"
	"path/filepath"
)

func getPushEntityIp(entity PushEntity) PushEntity {
	if monitorUtil.CheckString(entity.Ip) {
		entity.Ip = entity.Ip
	} else {
		entity.Ip = LOCAL_IP
	}
	return entity
}

func run(command string, timeOut int64) []PushEntity {
	result := runScript(command, timeOut)
	list := make([]PushEntity, 0)
	listStrig := make([]PushEntity, 0)
	listFlot64 := make([]PushEntityValue, 0)
	json.Unmarshal([]byte(result), &listStrig)
	var isString = true
	for _, entity := range listStrig {
		if commonUtil.CheckString(entity.Value) {
			entity.Name = strings.Replace(entity.Name,"_",".",-1)
			entity.Groups = strings.Replace(entity.Groups,"_",".",-1)
			entity = getPushEntityIp(entity)
			list = append(list, entity)
		} else {
			isString = false
		}
	}
	if ! isString {
		json.Unmarshal([]byte(result), &listFlot64)
	}
	for _, entity := range listFlot64 {
		tempEntity := PushEntity{}
		tempEntity.Value = strconv.FormatFloat(entity.Value, 'E', -1, 64)
		if !monitorUtil.CheckString(tempEntity.Value) {
			continue
		}
		if tempEntity.Value == "0E+00" {
			tempEntity.Value = "0"
		}
		if tempEntity.Value == "1E+00" {
			tempEntity.Value = "1"
		}
		tempEntity.Status = entity.Status
		tempEntity.Ip = entity.Ip
		tempEntity.Command = entity.Command
		tempEntity = getPushEntityIp(tempEntity)
		tempEntity.Messages = entity.Messages
		tempEntity.Server = entity.Server
		tempEntity.Name = strings.Replace(entity.Name,"_",".",-1)
		tempEntity.Groups = strings.Replace(entity.Groups,"_",".",-1)
		list = append(list, tempEntity)
	}
	Info("脚本执行结果 " + command + " " + result)
	return list
}

func runScript(command string, timeOut int64) string {
	c1 := make(chan string)
	go func() {
		Info("运行脚本" + command)
		c1 <- exec_shell(command)
	}()
	if timeOut < 1 {
		timeOut = 600
	}
	select {
	case res := <-c1:
		return res
	case <-time.After(time.Second * time.Duration(timeOut)):
		return "[]"
	}
}

func exec_shell(s string) string {
	cmd := exec.Command("sh", "-c", s)
	var out bytes.Buffer

	cmd.Stdout = &out
	err := cmd.Run()

	if err != nil {
		Error(err.Error())
		return "[]"
	}

	if out.Len() > 5 {
		return out.String()
	}
	return "[]"
}

func runStart() {

	success := make([]PushEntity, 0)
	faild := make([]PushEntity, 0)
	Info("SCRIPT_TIME " + commonUtil.ToString(SCRIPT_TIME))
	for _, time := range SCRIPT_TIME {
		Info("String time " + time)
		success, faild = runScriptPush(time, success, faild)
	}
	if len(faild) > 0 {
		Info("获取到失败状态" + commonUtil.ToString(faild) + FAILD_API_URL)
		pushMonitor(faild, FAILD_API_URL, false)
	}
	Info("获取到成功消息大小为" + strconv.Itoa(len(success)))
	if len(success) > 0 {
		pushMonitor(success, SUCCESS_API_URL, true)
	}
}

/**
	* 运行时间
	*
	* @param time
	* @param success
	* @param faild
	*/
func runScriptPush(timeS string, success []PushEntity, faild []PushEntity) ([]PushEntity, []PushEntity) {
	var command string
	var lastTime int64

	entitys := make([]PushEntity, 0)
	scriptTime, _ := SCRIPT_TIME_MAP.Get(timeS)

	// 获取这个时间保护的时间
	Info("runScript " + timeS )
	var lockKey string
	var checkInterval int64
	for _, id := range scriptTime {
		ids := strings.Split(id, "_")
		s := ids[1]

		// 获取脚本的最近运行时间
		lastTime = SCRIPT_RUNTIME.GetV(id)
		if lastTime == 0 {
			lastTime = 1
		}
		nowTime := time.Now().Unix()

		checkInterval = commonUtil.ToInt64(SCRIPT_CHECK_INTERVAL.GetV(id))
		lockKey = "locked" + id
		if nowTime-lastTime >= checkInterval-1 {
			Debug("获取配置文件信息" + ids[0])

			if MONITOR_LOCK.GetV(lockKey) > 0 {
				Debug("MONITOR IS LOCK ....  " + id + " " + commonUtil.ToStr64(MONITOR_LOCK.GetV(lockKey)))
				continue
			} else {
				Debug(timeS + " 添加锁 .... " + id)
				MONITOR_LOCK.Set(lockKey, nowTime)
			}

			if MONITOR_LOCK.GetV(lockKey) > 0 && (nowTime-MONITOR_LOCK.GetV(lockKey) >= (checkInterval * 2)) {
				Debug(timeS + " 锁时间超过删除锁 .... " + id)
				MONITOR_LOCK.Delete(lockKey)
			}

			Debug("SCRIPT_RUNTIME put " + id)
			SCRIPT_RUNTIME.Set(id, time.Now().Unix())
			command = filepath.Join(tempDir, s) + SCRIPT_ARGV.GetV(id)
			Debug("start exec script " + command)
			entitys = run(command, getTimeout(s))
			Debug(timeS + " 脚本完成删除锁 .... " + id)
			MONITOR_LOCK.Delete(lockKey)
			if len(entitys) < 1 {
				Error("执行脚本失败 " + command)
				continue
			}
			Debug("RUN entitys 大小为" + strconv.Itoa(len(entitys)))
			var alarmId string
			for _, entity := range entitys {
				alarmId = id + "_" + getServerId(entity) + "_" + entity.Groups + "_" + entity.Name
				Debug("RUN entitys 大小为" + strconv.Itoa(len(entitys)) + " value " + entity.Value)
				if monitorUtil.CheckString(entity.Value) {
					entity.Command = s
					entity.ScriptId = s
					// 设置业务线
					entity.ConfigId = ids[0]
					entity.GroupsName = HOST_GROUP
					if !monitorUtil.CheckString(entity.Ip) {
						entity.Server = HOST_IDS
					} else {
						entity.Server = getServerId(entity)
					}
					// 画图的使用的 不做报警处理
					if entity.Status == 0 {
						Debug("画图使用数据:" + commonUtil.ToString(entity))
						success = append(success, entity)
					}
					Debug("获取到数据状态为 " + strconv.Itoa(entity.Status))
					// 判断脚本运行状态成功
					if entity.Status == 1 {
						Debug("status 1 PushEntity " + commonUtil.ToString(entity))
						success = append(success, entity)
						if ALARM_COUNT.GetV(alarmId) > 0 {
							if getAlarmStatus(alarmId) {
								ALARM_COUNT.Delete(alarmId)
								Debug("获取到报警状态为 " + strconv.Itoa(entity.Status) + " ALARM_COUNT.remove " + alarmId)

								if !(ALARM_LAST_TIME.GetV(alarmId) > 0) {
									Debug("初始化 ALARM_LAST_TIME ...")
									ALARM_LAST_TIME.Set(alarmId, time.Now().Unix())
								}
								//  发送报警信息, 报警后40秒内恢复,不发送恢复信息, 去掉多余的报警
								if ALARM_LAST_TIME.GetV(alarmId) > 0 && time.Now().Unix()-ALARM_LAST_TIME.GetV(alarmId) > 40 {
									monitorItemEntity, _ := SCRIPT_TO_ITEM.Get(s + "_" + ids[0])
									if monitorItemEntity.ItemId > 0 {
										if monitorItemEntity.IsRecover == 1 {
											QUEUE.PushFront(entity)
											Debug("添加恢复报警到队列啦...1")
										}
									} else {
										QUEUE.PushFront(entity)
										Debug("添加恢复报警到队列啦...2")
									}
								} else {
									Info("恢复时间太短,跳出恢复报警...")
								}
								Debug("remove ALARM_COUNT: " + alarmId)
							}
						}
					} else {
						// 统计非正常状态的信息
						Debug("获取非正常状态数据 " + strconv.Itoa(entity.Status))
						if entity.Status > 1 {
							entity.AlarmId = alarmId
							status := strconv.Itoa(entity.Status)
							Debug("获取到失败状态 " + commonUtil.ToString(entity) + " " + alarmId)
							faild = append(faild, entity)
							if !commonUtil.MapContainsInt64(ALARM_COUNT.GetData(), alarmId) {
								Debug("获取到报警状态为 " + status + " init ALARM_COUNT 1 " + alarmId + " " + commonUtil.ToString(ALARM_COUNT))
								ALARM_COUNT.Set(alarmId, 1)

							}
							if ALARM_COUNT.GetV(alarmId) == 0 {
								Debug("获取到报警状态为 " + status + " init ALARM_COUNT 2 " + alarmId + " " + commonUtil.ToString(ALARM_COUNT))
								ALARM_COUNT.Set(alarmId, 1)
							}
							SCRIPT_STATUS.Set(alarmId, entity)
						}
					}
				}
			}
		}
	}
	return success, faild
}

func getAlarmStatus(id string) bool {
	if getAlarmCount(id) > 1 {
		return true
	}
	return false
}

func getAlarmCount(id string) int64 {
	if !(ALARM_COUNT.GetV(id) > 0) {
		Debug("getAlarmCount: " + id + ",  count:" + commonUtil.ToStr64(ALARM_COUNT.GetV(id)))
		ALARM_COUNT.Set(id, 0)
	}
	return ALARM_COUNT.GetV(id)
}
