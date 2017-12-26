package service

import (
	"util/commonUtil"
	"util/monitorUtil"
	"strings"
	"time"
	"path/filepath"
)

func checkMonitorAlarm() {
	if !IS_MONITOR || len(ALARM_MAP.GetData()) < 1 {
		return
	}

	if GetErrorNumber() {
		return
	}

	Info("报警发送检查....")
	for key, value := range ALARM_MAP.GetData() {
		if !monitorUtil.CheckString(key) {
			Debug("ALARM_MAP key is null ... ")
			continue
		}
		keys := strings.Split(key, "_")
		// 获取配置文件的ID
		configId := keys[0]
		entity,stat := CONFIGS.Get(configId)
		if stat == false {
			Debug("CONFIGS 信息为" + commonUtil.ToString(CONFIGS))
			Debug("获取到空数据 COFNIGS.get " + configId + commonUtil.ToString(keys))
			continue
		}
		if len(keys) < 6 {
			Debug("获取到key小于6... " + commonUtil.ToString(keys))
			continue
		}
		if len(keys) > 6 {
			Debug("返回数据有问题,name不能有_,请用.代替")
			continue
		}
		Debug("ALARM_NEW_MAP " + key + " " + keys[5])
		if keys[5] == "ok" || keys[5] == "warning" || keys[5] == "unknown" {
			continue
		}
		scriptId := keys[1]
		id := configId + "_" + scriptId
		alarmId := id + "_" + keys[2] + "_" + keys[3] + "_" + keys[4]
		// 如果小于配置文件的重试次数，则执行重试

		// 获取重试次数
		var retry int64
		if entity.MonitorConfigureTp == "item" {
			retry = entity.Retry
		} else {
			//itemEntity := MonitorItemEntity{}
			//d, _ := json.Marshal(SCRIPT_RETRY_MAP.Get(id))
			//json.Unmarshal(d, &itemEntity)
			itemEntity, _ := SCRIPT_RETRY_MAP.Get(id)
			retry = itemEntity.Retry
		}

		if 0 == retry {
			Info("获取到重试次数为0,删除报警信息")
			ALARM_MAP.Delete( key)
		}

		alarmIdTime := alarmId + ".time"

		Debug("获取到结果为 "+ commonUtil.ToString(value))
		// 如果retry为0则不执行重试
		if value >= 1 && value <= retry+1 && retry > 0 {

			if ALARM_INTERVAL.GetV(alarmIdTime) == 0{
				ALARM_INTERVAL.Set(alarmIdTime, time.Now().Unix())
			}

			if time.Now().Unix()-ALARM_INTERVAL.GetV(alarmIdTime) < 10 {
				Info("跳出重试,间隔太小了" + commonUtil.ToString(value))
				continue
			}
			Debug("获取到重试数据" + commonUtil.ToString(value))
			command := filepath.Join(tempDir, scriptId )+ SCRIPT_ARGV.GetV(id)

			isOk := true
			Debug("checkMonitorAlarm " + command + " " + alarmId + " retry -> " + commonUtil.ToStr64(value))
			pushEntities := run(command, getTimeout(scriptId))
			for _, pushEntity := range pushEntities {
				if len(pushEntity.Value) > 0 {
					pushEntity.AlarmId = alarmId
					pushEntity.ScriptId = scriptId
					pushEntity.ConfigId = configId
					// 脚本数据如果没有指定IP参数,就为本机的脚本内容
					if monitorUtil.CheckString(pushEntity.Ip) {
						pushEntity.Server = HOST_IDS
					} else {
						pushEntity.Server = getServerId(pushEntity)
					}
					if pushEntity.Status == 2 {
						isOk = false
						ALARM_MAP.Set(alarmId, value + 1)
					}
					ALARM_INTERVAL.Set(alarmIdTime, time.Now().Unix())
					Debug("ALARM_INTERVAL " + command + " " + alarmId + " retry put time -> " + commonUtil.ToStr64(time.Now().Unix()))
					SCRIPT_STATUS.Set(alarmId, pushEntity)
				}
			}
			if isOk {
				pushEntity := PushEntity{}
				pushEntity.Status = 1
				setStatus(pushEntity, alarmId)
				Info("成功退出重试" + " " + alarmId + " retry put time break ")
				ALARM_MAP.Delete(alarmId)
			}
		} else {

			// 判断报警间隔
			currTime := time.Now().Unix()

			if ALARM_INTERVAL.GetV(alarmId) == 0 {
				Debug("初始化 ALARM_INTERVAL ... ")
				ALARM_INTERVAL.Set(alarmId, currTime)
			}

			var alarmInterval int64
			if entity.MonitorConfigureTp == "item" {
				alarmInterval = entity.AlarmInterval * 60
			} else {
				itemEntity,_ := SCRIPT_RETRY_MAP.Get(id)
				alarmInterval = itemEntity.AlarmInterval * 60
			}
			if len(ALARM_COUNT.GetData()) < 1 {
				Debug("没有ALARM_COUNT, 退出ALARM_COUNT")
				return
			}

			Info("获取到报警间隔为:" + commonUtil.ToStr64(alarmInterval))
			if ! monitorUtil.CheckString(commonUtil.ToStr64(ALARM_COUNT.GetV(alarmId))) {
				Debug("ALARM_COUNT is null ... " + alarmId)
				continue
			}

			lastSendTime := ALARM_INTERVAL.GetV(alarmId)
			Debug("获取到ALARM_COUNT 是" + commonUtil.ToStr64(ALARM_COUNT.GetV(alarmId)))
			currTime = time.Now().Unix()
			if currTime-lastSendTime < alarmInterval && currTime > lastSendTime {
				// 第一次不记数，直接发送
				if alarmInterval > 0 && retry > 0 {
					Debug("不能发送报警信息,报警间隔太短:" + alarmId + " 还差 " + commonUtil.ToStr64(alarmInterval-(currTime-lastSendTime)))
					continue
				}
			}

			if commonUtil.MapContainsInt64(ALARM_COUNT.Data, alarmId) && ALARM_COUNT.GetV(alarmId) >= 1 {
				Debug("ALARM_COUNT add number " + commonUtil.ToStr64(ALARM_COUNT.GetV(alarmId)))
				ALARM_COUNT.Set(alarmId, ALARM_COUNT.GetV(alarmId) + 1)
			}

			if 0 == ALARM_COUNT.GetV(alarmId) {
				Debug("ALARM_COUNT id is continue " + commonUtil.ToStr64(ALARM_COUNT.GetV(alarmId)))
				continue
			}

			Debug("alarm count to number " + commonUtil.ToStr64(ALARM_COUNT.GetV(alarmId)))
			var alarmCount int64
			if "item" == entity.MonitorConfigureTp {
				alarmCount = entity.AlarmCount
			} else { // (MonitorItemEntity)
				itemEntity,_ := SCRIPT_RETRY_MAP.Get(id)
				alarmCount = itemEntity.AlarmCount
			}

			// 判断报警次数
			if ALARM_COUNT.GetV(alarmId) >= alarmCount+2 {
				if alarmCount > 1 {
					Info("alarm count to max ")
					continue
				}
			}

			if 0 == retry && alarmCount > 0 {
				Debug("获取到重试次数为0，删除ALARM_COUNT,跳出检查")
				ALARM_COUNT.Delete(alarmId)
				continue
			}

			if _, ok := SCRIPT_STATUS.Get(alarmId); ok {
				//  发送报警信息
				Info("添加报警到队列啦... " + alarmId)
				Info("添加到报警队列script_status" + commonUtil.ToString(SCRIPT_STATUS.GetV(alarmId)))
				Info("初始化时间报警记数器:" + alarmId)
				ALARM_INTERVAL.Set(alarmId, time.Now().Unix())
				QUEUE.PushFront(SCRIPT_STATUS.GetV(alarmId))
			}

			if 0 == retry {
				Info("获取到重试次数为0，删除SCRIPT_STATUS")
				SCRIPT_STATUS.Delete(alarmId)
			} else {
				ALARM_LAST_TIME.Set(alarmId, time.Now().Unix())
			}
		}
	}
}



/**
 * 获取参数中的IP地址的ID
 * 所有配置的IP地址必须在资源库中
 *
 * @return
 */
func getServerId(pushEntity PushEntity) string {
	if pushEntity.Ip == LOCAL_IP {
		return HOST_IDS
	}
	if !monitorUtil.CheckString(pushEntity.Ip) {
		return HOST_IDS
	}
	ip := pushEntity.Ip
	_, ok := ARGV_HOST_MAP.Get(ip)
	if !ok {
		result := getHostId(ip)
		if monitorUtil.CheckString(result) {
			ARGV_HOST_MAP.Set(ip, result)
		} else {
			ARGV_HOST_MAP.Set(ip, strings.Replace(ip, ".", "999", -1))
		}
	}
	Debug("ARGV_HOST_MAP " + ip + " " + ARGV_HOST_MAP.GetV(ip))
	return ARGV_HOST_MAP.GetV(ip)
}
