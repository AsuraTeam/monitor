package service

import (
	"util/redisUtil"
	"util/monitorUtil"
	"configure"
	"io/ioutil"
	"encoding/json"
	"util/commonUtil"
	"time"
	"strings"
	"strconv"
	"util/hostUtil"
	"path/filepath"
)

/**
 * agent检查 60分钟一次
 */
func checkAgentStatus() {

	initTimeMap("checkAgentStatus")
	if checkTimeMap("checkAgentStatus", 60) {
		Debug("开始检查agent线程启动")
		go checkMonitorUpdate()
		Debug("开始检查agent线程结束")
		putTimeMap("checkAgentStatus")
	}
}

/**
 * 发送agent报警信息
 *
 * @param host
 */
func sendAgentAlarm(host string) {

	server := redisUtil.Get_del("get", configure.CacheHostIdToIp+host)

	// 如果ping失败，属于严重，发送到所有联系方式到管理员组
	ping := execPing(server)

	if !strings.Contains(strings.ToUpper(ping), "TTL") {
		alarmKey := configure.CacheAgentAlarmNumber + host
		alarmTimeKey := configure.CacheAgentAlarmNumber + host + "_time"
		alarmNumber := redisUtil.Get_del("get", alarmKey)
		alarmTimeData := redisUtil.Get_del("get", alarmTimeKey)
		messagesEntity := getAgentMessages(host)
		messagesEntity.SevertityId = 2
		messagesEntity.AlarmCount = 1
		messagesEntity.Ip = server
		if monitorUtil.CheckString(alarmNumber) {
			if commonUtil.ToInt64(alarmNumber) >= 4 {
				Info("agent check 报警超过4次")
				return
			} else {
				if monitorUtil.CheckString(alarmTimeData) && time.Now().Unix()-commonUtil.ToInt64(alarmTimeData) > 600 {
					messagesEntity.AlarmCount = commonUtil.ToInt64(alarmNumber) + 1
					alarmNumber := commonUtil.ToInt64(alarmNumber) + 1
					redisUtil.Setex(alarmKey, 86400, commonUtil.ToStr64(alarmNumber))
					redisUtil.Setex(alarmTimeKey, 86400, commonUtil.ToStr64(time.Now().Unix()))
				} else {
					Info("agent check 报警间隔不够")
					return
				}
			}
		} else {
			alarmNumber = "1"
			redisUtil.Setex(alarmKey, 86400, commonUtil.ToStr64(1))
			redisUtil.Setex(alarmTimeKey, 86400, commonUtil.ToStr64(time.Now().Unix()))
		}
		hostname := monitorUtil.GetIpHostName(server)
		if len(hostname) < 5 {
			Error("无法获取主机名 " + server)
			return
		}
		if len(ping) < 5 {
			Error("ping 结果错误" + ping)
			return
		}

		stringBuilder := "agent check 报警:" + alarmNumber + " ping " + server +
			" " + hostname + " " + ping + " " +
			commonUtil.GetDate() + "检查服务器:" + hostUtil.Hostname()
		messagesEntity.Messages = stringBuilder
		pushMessagesToServer(commonUtil.ToString(messagesEntity))
	}
}


/**
 * 分布式检查监控agent是否存活，如果5分钟内没有
 * 执行数据上报，就判断agent已经死亡，并将报警信息
 * 发送给web页面，恢复后从web页面删除, 报警信息发送
 * 给系统管理员
 */
func checkMonitorUpdate() {
	if !IS_MONITOR && !IS_DEFAULT {
		return
	}
	initTimeMap("agentCheckTime")
	if checkTimeMap("agentCheckTime", 60) {
		putTimeMap("agentCheckTime")
	} else {
		return
	}
	isCheck := commonUtil.Configure("agentActiveCheck")
	if isCheck == "false" {
		return
	}
	groupsMap := getGroups()
	groupsList := make([]string, 0)
	groupsKey := make([]string, len(groupsMap))
	groupsCount := 0
	for key := range groupsMap {
		groupsList = append(groupsList, key)
		groupsKey[groupsCount] = configure.CacheCheckMonitorLock + key
		groupsCount += 1
	}

	Debug("mget " + commonUtil.ToString(groupsKey))
	groupsData := redisUtil.Mget(strings.Join(groupsKey, " "))
	start := time.Now().Unix()
	groupsCount = 0
	for _, result := range groupsData {
		Debug("开始检查agent是否存活"+ result)
		if len(groupsList) <= groupsCount {
			continue
		}
		groupsId := groupsList[groupsCount]
		groupsCount += 1
		CACHE_CHECK_MONITOR_LOCK = false
		lockKey := configure.CacheCheckMonitorLock + groupsId
		if len(result) < 3 {
			monitorLockEntity := MonitorLockEntity{}
			monitorLockEntity.ServerId = HOST_IDS
			monitorLockEntity.CheckTime = time.Now().Unix()
			redisUtil.Setex(lockKey, 60, commonUtil.ToString(monitorLockEntity))
			CACHE_CHECK_MONITOR_LOCK = true
		}
		if !CACHE_CHECK_MONITOR_LOCK {
			continue
		}
		if time.Now().Unix()-start > 60 {
			return
		}
		checkHostIsUpdate(groupsId)
	}
}

/**
 * 获取组信息
 *
 * @return
 */
func getGroups() map[string]string {
	groups := ""
	fileName := filepath.Join(monitorUtil.GetTempDir("cache") ,"cache_groups_name")
	if monitorUtil.CheckCacheFileTime("cache_groups_name", 1800) {
		groups = redisUtil.Get_del("get", configure.CacheGroupName)
		ioutil.WriteFile(fileName, []byte(groups), 0755)

	}
	data,err := ioutil.ReadFile(fileName)
	groupsMap := make(map[string]string)
	if err == nil {
		groups = string(data)
		Debug("获取到grous " + groups)
		if monitorUtil.CheckString(groups) {
			json.Unmarshal([]byte(groups), &groupsMap)
		}
	}
	return groupsMap
}

/**
  * @param groupsId
  * @param jedis
  * @throws Exception
  */
func checkHostIsUpdate(groupsId string) {
	okMap := MapLockString{}
	faildMap := MapLockString{}
	Debug("获取监控主机 hostList" + groupsId)
	hostList := getHosts(groupsId)
	setAgentAlarmStatus(hostList)
	host := ""
	count := 0
	Debug("hostList: " + commonUtil.ToString(hostList))
	skip := false
	for _, date := range getHostStatus(hostList, false) {
		host = hostList[count]
		count += 1
		Info(host + " " + date)
		if len(date) < 2 {
			continue
		}
		currDate := time.Now().Unix()
		lastDate := commonUtil.ToInt64(date)
		// 防止本机时间差距太大
		if currDate-lastDate >= 240 {
			if skip {
				Debug("跳出agent检查 ")
				continue
			}

			// 重试8次，每隔5秒
			key := configure.CacheHostIsUpdate + host
			for retry := 0; retry < 8; retry++ {
				hostDate := redisUtil.Get_del("get", key)
				Debug("重试检查agent " + strconv.Itoa(retry) + " " + host)
				if monitorUtil.CheckString(hostDate) {
					ctime, _ := strconv.ParseInt(hostDate, 10, 64)
					if time.Now().Unix()-ctime > 300 {
						faildMap.Set(host, date)
					} else {
						okMap.Set(host, date)
						faildMap.Delete(host)
						continue
					}
				}
				time.Sleep(time.Second * 5)
			}

			Debug("host date " + date)
			faildMap.Set(host, date)

			Debug("获取到监控超时服务..." + host)
			// 异常数据放到web页面
			// 报警信息生成
			Debug("检查到agent失败了，开始检查ping")
			sendAgentAlarm(host)
			// 每次就检查一个报错
			skip = true
			continue
		} else {
			Debug("set is ok " + host)
			okMap.Set(host, date)
			Debug("agent_alarm_map :" + commonUtil.ToString(AGENT_ALARM_MAP))
			if _, ok := AGENT_ALARM_MAP.Get(host); ok {
				AGENT_ALARM_MAP.Delete(host)
				server := redisUtil.Get_del("get", configure.CacheHostIdToIp+host)
				hostname := monitorUtil.GetIpHostName(server)
				if len(server) < 5 {
					continue
				}
				messagesEntity := getAgentMessages(host)
				messagesEntity.SevertityId = 1
				messagesEntity.AlarmCount = 0
				messagesEntity.Ip = server
				key := configure.CacheAgentAlarmNumber + host
				result := redisUtil.Get_del("get", key)
				if monitorUtil.CheckString(result) {
					redisUtil.Get_del("del", key)
					messagesEntity.Messages = "agent 检查 ping 恢复  " + server + " " + hostname + " " + commonUtil.GetDate() + " agent server " + hostUtil.Hostname()
					pushMessagesToServer(commonUtil.ToString(messagesEntity))
				}
			}
		}
	}
	redisUtil.Setex(configure.CacheAgentIsOk+"_"+groupsId, 300, commonUtil.ToString(okMap.Data))
	redisUtil.Setex(configure.CacheAgentUnreachable+"_"+groupsId, 300, commonUtil.ToString(faildMap.Data))
}

/**
   * @param groupsId
   * @return
   */
func getGroupsHosts(groupsId string) [] string {
	fileName := filepath.Join(monitorUtil.GetTempDir("cache"),"cache_groups_hosts_" + groupsId)
	if monitorUtil.CheckCacheFileTime("cache_groups_hosts_"+groupsId, 1800) {
		result := redisUtil.Get_del("get", configure.CacheGroupsHosts+groupsId)
		ioutil.WriteFile(fileName, []byte(result), 0755)
	}
	result := commonUtil.ReadFile(fileName)
	Debug("agent ping 获取到组信息 "+ groupsId + " " + result)
	if monitorUtil.CheckString(result) {
		d := make([]string, 0)
		json.Unmarshal([]byte(result), &d)
		return d
	}
	return make([]string, 0)
}

/**
  * @param groupsId
  * @return
  */
func getHosts(groupsId string) []string {
	hosts := getGroupsHosts(groupsId)
	hostList := make([]string, 0)
	for _, host := range hosts {
		if ! commonUtil.ListExistsString(hostList, host) {
			hostList = append(hostList, host)
		}
	}
	return hostList
}

/**
   * 设置每个agent的报警信息, map
   *
   * @param hosts
   */
func setAgentAlarmStatus(hosts []string) {
	list := getHostStatus(hosts, true)
	count := 0
	var host string
	Debug("setAgentAlarmStatus " + commonUtil.ToString(list))
	for _, alarm := range list {
		host = hosts[count]
		count += 1
		if monitorUtil.CheckString(alarm) {
			Debug(host + "is alarmis alarm " + alarm)
			AGENT_ALARM_MAP.Set(host, alarm)
		}
	}
}

/**
   * @param hosts
   * @return
   */
func getHostStatus(hosts []string, isAlarm bool) []string {
	stringsData := make([]string, len(hosts))
	count := 0
	key := ""
	for _, host := range hosts {
		if isAlarm {
			key = configure.CacheAgentAlarmNumber + host
		} else {
			key = configure.CacheHostIsUpdate + host
		}
		stringsData[count] = key
		count += 1
	}
	if len(stringsData) > 0 {
		result := redisUtil.Mget(strings.Join(stringsData, " "))
		return result
	}
	return make([]string, 0)
}
