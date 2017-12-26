package service

import (
	"util/redisUtil"
	"configure"
	"util/monitorUtil"
	"encoding/json"
	"strconv"
	"util/commonUtil"
	"time"
	"encoding/base64"
)

/**
 * @param messages
 */
func pushMessagesToServer(messages string) {

	// 报警开关
	alarmSwitch := redisUtil.Get_del("get", configure.CacheAlarmSwitch)
	if monitorUtil.CheckString(alarmSwitch) && alarmSwitch != "1" {
		Info("报警开关关闭,不能发送报警信息...")
		return
	}

	Info("发送报警消息为: " + messages)
	// 报警发送到redis队列里
	isOk := false
	for i := 0; i < 4; i++ {
		push := redisUtil.Lpush(configure.CacheAlarmQueueKey, messages)
		if monitorUtil.CheckString(push) {
			isOk = true
			break
		}
	}
	// 通知服务端做报警处理
	url := "/monitor/api/send/messages"
	if isOk {
		Info("发送报警消息" + HttpPost(url, "") + " " + url)
	} else {
		sendPostMessages(messages, url)
	}
}

/**
   * 直接发送报警消息
   * @param messages
*/
func sendPostMessages(messages string, url string) {
	entity := MonitorMessagesEntity{}
	json.Unmarshal([]byte(messages), &entity)
	mess := "alarmCount=" + strconv.FormatInt(entity.AlarmCount, 10) +
		"&serverId=" + strconv.FormatInt(entity.ServerId, 10) +
		"&groupsId=" + strconv.FormatInt(entity.GroupsId, 10) +
		"&scriptsId=" + strconv.FormatInt(entity.ScriptsId, 10) +
		"&severtityId=" + strconv.FormatInt(entity.SevertityId, 10) +
		"&messages=" + entity.Messages +
		"&messagesTime=" + entity.MessagesTime
	if monitorUtil.CheckString(entity.Mobile) {
		mess += "&mobile=" + entity.Mobile
	}
	if monitorUtil.CheckString(entity.Email) {
		mess += "&email=" + entity.Email
	}
	if monitorUtil.CheckString(entity.Ding) {
		mess += "&ding=" + entity.Ding
	}
	if monitorUtil.CheckString(entity.Weixin) {
		mess += "&weixin=" + entity.Weixin
	}
	Info(mess)
	Info(HttpPost(url, mess))
}

/**
  * 生成报警信息
  *
  * @param scriptId
  * @param status
  */
func pushMessages(scriptId int64, status int64, pushEntity PushEntity) {
	Info("pushMessages 获取到pushentity: " + commonUtil.ToString(pushEntity))
	entity := MonitorMessagesEntity{}
	entity.ScriptsId = scriptId
	entity.SevertityId = status
	entity.MessagesTime = GetStamp()
	if len(pushEntity.Ip) > 0 {
		entity.Ip = pushEntity.Ip
	} else {
		entity.Ip = LOCAL_IP
	}
	entity.Value = pushEntity.Value
	entity.IndexName = pushEntity.Name

	// 报警服务器设置
	serverId := HOST_IDS
	alarmId := pushEntity.ConfigId + "_" + strconv.FormatInt(scriptId, 10) + "_" + serverId + "_" + pushEntity.Groups + "_" + pushEntity.Name
	Info("pushMessages 获取到ALARM_COUNT id: " + alarmId + " " + commonUtil.ToString(ALARM_COUNT))
	if monitorUtil.CheckString(pushEntity.AlarmId) {
		alarmId = pushEntity.AlarmId
		Info("pushMessages 获取到ALARM_COUNT id pushEntity: " + alarmId)
	}

	if _, ok := ALARM_COUNT.Get(alarmId); ok {
		entity.AlarmCount = ALARM_COUNT.GetV(alarmId)
	} else {
		entity.AlarmCount = 1
	}
	itemEntity := MonitorItemEntity{}
	json.Unmarshal([]byte(SCRIPT_ITEM.GetV(commonUtil.ToStr64(scriptId))), &itemEntity)

	if entity.AlarmCount < 1 && entity.SevertityId > 1 {
		entity.AlarmCount = 1
	}

	// 报警不恢复处理
	if itemEntity.IsRecover == 0 && status == 1 {
		Info("获取到不需 要发送恢复的监控项目,退出报警恢复" + commonUtil.ToString(pushEntity))
		return
	}

	var recover string
	var message string
	if itemEntity.ItemId > 0 {
		Info("获取到script_item:" + commonUtil.ToString(itemEntity))
		recover = itemEntity.RecoverMessages
		message = itemEntity.AlarmMessages
	} else {
		recover = "报警恢复了"
		message = "报警啦"
	}

	//
	configureEntity, _ := CONFIGS.Get(pushEntity.ConfigId)
	// 在项目中配置发送给管理员的项目全部发报警给管理员
	adminGroup := getAdminGroup(itemEntity)

	// 获取额外配置的组
	Info("获取监控配置额外的组 start")
	alarmGroup := getAlarmGroups(commonUtil.ToStr64(itemEntity.ItemId), entity.Ip)
	Info("获取监控配置额外的组 end " + alarmGroup)
	if len(alarmGroup) > 1 {
		adminGroup = adminGroup + alarmGroup
		Info("获取到额外配置的监控主:" + alarmGroup)
	}
	mobileGroups := configureEntity.MobileGroups + "," + adminGroup
	emailGroups := configureEntity.EmailGroups + "," + adminGroup
	dingGroups := configureEntity.DingGroups + "," + adminGroup
	weixinGroups := configureEntity.WeixinGroups + "," + adminGroup
	all := configureEntity.AllGroups
	mobile := ""
	email := ""
	ding := ""
	weixin := ""
	upList := make([]int, 0)
	if monitorUtil.CheckString(all) {
		mobile = getContact(all+","+adminGroup, "mobile")
		email = getContact(all+","+adminGroup, "email")
		ding = getContact(all+","+adminGroup, "ding")
		weixin = getContact(all+","+adminGroup, "weixin")
		mobile = getAlarmUpGroups(all, entity.AlarmCount, "mobile", mobile, upList)
		email = getAlarmUpGroups(all, entity.AlarmCount, "email", email, upList)
		ding = getAlarmUpGroups(all, entity.AlarmCount, "ding", ding, upList)
		weixin = getAlarmUpGroups(all, entity.AlarmCount, "weixin", weixin, upList)
	} else {
		if monitorUtil.CheckString(mobileGroups) {
			mobile = getContact(mobileGroups, "mobile")
			mobile = getAlarmUpGroups(mobileGroups, entity.AlarmCount, "mobile", mobile, upList)
		}
		if monitorUtil.CheckString(emailGroups) {
			email = getContact(emailGroups, "email")
			email = getAlarmUpGroups(emailGroups, entity.AlarmCount, "email", email, upList)
		}
		if monitorUtil.CheckString(dingGroups) {
			ding = getContact(dingGroups, "ding")
			ding = getAlarmUpGroups(dingGroups, entity.AlarmCount, "ding", ding, upList)
		}
		if monitorUtil.CheckString(weixinGroups) {
			weixin = getContact(weixinGroups, "weixin")
			weixin = getAlarmUpGroups(weixinGroups, entity.AlarmCount, "weixin", weixin, upList)
		}
	}

	// 设置报警对象
	if len(weixin) > 0 {
		entity.Weixin = getUniqContact(weixin)
	}
	if len(mobile) > 0 {
		entity.Mobile = getUniqContact(mobile)
	}
	if len(ding) > 0 {
		entity.Ding = getUniqContact(ding)
	}
	if len(email) > 0 {
		entity.Email = getUniqContact(email)
	}
	// 添加报警升级信息
	Info("获取到报警升级" + strconv.Itoa(len(upList)))
	if len(upList) > 0 {
		up := upList[0]
		message = message + "->报警已升级为第" + string(up) + "级别"
		Info("获取到报警升级信息" + message)
	}

	// 报警信息中添加业务线和设备类型
	if monitorUtil.CheckString(entity.Ip) {

		hostInfo := redisUtil.Get_del("get", configure.CacheIpHostInfo+entity.Ip)
		if monitorUtil.CheckString(hostInfo) && len(hostInfo) > 10 {
			mapData := make(map[string]string)
			json.Unmarshal([]byte(hostInfo), &mapData)
			if len(mapData) > 0 {
				message += " \n业务线:" + mapData["groupsName"] + " \n类型:" + mapData["typeName"] + " \n环境:" + mapData["entName"] + " \n负责人:" + mapData["userName"]
			}
		}
	}

	if status == 1 {
		entity.Messages = recover
	} else {
		entity.Messages = message
	}

	// 将没有ID或者是非本机的写入ip地址，发送报警使用
	if monitorUtil.CheckString(pushEntity.Ip) {
		entity.IpAddress = pushEntity.Ip
	}

	// 业务线设置
	groupsId := HOST_GROUP
	groups := commonUtil.ToInt64(groupsId)
	server := commonUtil.ToInt64(serverId)
	entity.GroupsId = groups
	entity.ServerId = server
	messages := getMessages(pushEntity, entity)
	entity.Messages = messages
	pushMessagesToServer(commonUtil.ToString(entity))
}

/**
 * 删除MAP
 * @param id
 */
func removeMap(id string) {
	Info("removeMap " + id)
	ALARM_MAP.Delete(id)
}

/**
 * 报警状态设置
 * 当报警超过配置的数值后进行报警
 *
 * @param type
 * @param ids
 */
func setAlarmMap(tp string, ids string) {
	id := ids + "_" + tp
	var old = ALARM_MAP.GetV(id) + 1
	ALARM_MAP.Set(id, old)
	Info("setAlarmMap: " + ids + "  type:" + tp)
	if tp != "ok" {
		removeMap(ids + "_ok")
	}
	if tp != "faild" {
		if tp != "warning" {
			removeMap(ids + "_faild")
		}
	}
	if tp != "warning" {
		if tp != "faild" {
			removeMap(ids + "_warning")
		}
	}
	if tp != "unknown" {
		removeMap(ids + "_unknown")
	}
}

/**
 * @param pushEntity
 * @param id
 */
func setStatus(pushEntity PushEntity, id string) {
	Info("setStatus pushEntity " + commonUtil.ToString(pushEntity))
	switch pushEntity.Status {
	case 1:
		setAlarmMap("ok", id)
		break
	case 2:
		// 获取到老的值，然后加一
		setAlarmMap("faild", id)
		break
	case 3:
		setAlarmMap("warning", id)
		break
	case 4:
		setAlarmMap("unknown", id)
		break
	default:
		break
	}
}

type PushEntityData struct {
	alarmId    string
	ip         string
	name       string
	groups     string
	value      string
	messages   string
	status     string
	command    string
	time       string
	groupsName string
	configId   string
	server     string
	scriptId   string
	serverId   string
}

func getPushData(entity []PushEntity) string {
	var datas []map[string]string
	for _, data := range entity {
		d := data.Value
		dataEntity := make(map[string]string)
		dataEntity["value"] = d
		dataEntity["status"] = strconv.Itoa(data.Status)
		dataEntity["scriptId"] = data.ScriptId
		dataEntity["configId"] = data.ConfigId
		dataEntity["ip"] = data.Ip
		dataEntity["serverId"] = data.ServerId
		dataEntity["name"] = data.Name
		dataEntity["groupsName"] = data.GroupsName
		dataEntity["groups"] = data.Groups
		dataEntity["messages"] = data.Messages
		dataEntity["command"] = data.Command
		dataEntity["server"] = data.Server
		//dataEntity["time"] = commonUtil.ToStr64(time.Now().Unix())
		datas = append(datas, dataEntity)
	}
	d, _ := json.Marshal(datas)
	data := string(d)
	return data
}

/**
  * 上报监控结果
  *
  * @param entity
  */
func pushMonitor(entity []PushEntity, url string, status bool) {

	if GetErrorNumber() {
		return
	}

	ok := make([]string, 0)
	fail := make([]string, 0)
	unknown := make([]string, 0)
	warning := make([]string, 0)

	Info(strconv.Itoa(len(entity)))
	if len(entity) > 0 {
		data := getPushData(entity)
		if len(data) < 5 {
			Info("获取到数据为空，退出pushMonitor")
			return
		}
		// 发送监控数据
		if status {
			var oks = false
			// 前30次走http方式发送数据
			for _, e:= range entity{
				if _,ok := PUSH_NAME_MAP.Get( e.Groups + e.Name);ok{
					oks = ok
					break
				}
			}
			Debug("判断PUSHNAME " + commonUtil.ToString(oks))
			if oks {
				Info("通过udp的socket端口上传数据 ")
				SendSplitData(data)
			}else{
				pushData(url, data, entity)
			}
		} else {
			// 发送失败的数据
			Info("上传失败的数据 " + data)
			pushData(url, data, entity)
		}
		Info("entity size: " + strconv.Itoa(len(entity)))

		for _, pushEntity := range entity {
			id := pushEntity.ConfigId + "_" + pushEntity.ScriptId + "_" + getServerId(pushEntity) + "_" + pushEntity.Groups + "_" + pushEntity.Name
			Info("PushEntity " + id)
			switch pushEntity.Status {
			case 1:
				ok = append(ok, id)
				setAlarmMap("ok", id)
				if getAlarmStatus(id) {
					//  发送报警信息
					Debug("remove ALARM_COUNT: " + id)
					ALARM_COUNT.Delete(id)
					Info("添加恢复报警到队列啦...")
					QUEUE.PushFront(pushEntity)
				}
				break
			case 2:
				// 获取到老的值，然后加一
				setAlarmMap("fail", id)
				fail = append(fail, id)
				break
			case 3:
				setAlarmMap("warning", id)
				warning = append(warning, id)
				break
			case 4:
				setAlarmMap("unknown", id)
				unknown = append(unknown, id)
				break
			default:
				break
			}
		}
	}

	if len(ok) > 0 {
		Debug("set ok data ")
		setGroupsData("ok", ok)
	}
	if len(fail) > 0 {
		Debug("set faild data ")
		setGroupsData("faild", fail)
	}
	if len(warning) > 0 {
		Debug("set warning data ")
		setGroupsData("warning", warning)
	}
	if len(unknown) > 0 {
		Info("set unknown data ")
		setGroupsData("unknown", unknown)
	}
}

func pushData(url string, data string, entitys []PushEntity) {
	if GetErrorNumber() {
		return
	}
	datab64 := base64.StdEncoding.EncodeToString([]byte(data))
	r := HttpPost(url, "lentity="+string(datab64))
	if commonUtil.CheckString(r){
		Info("上传数据完成"+r)
		for _,entity := range entitys  {
			n := entity.Groups + entity.Name
			PUSH_NAME_MAP.Set(n,"1")
		}
	}
}

/**
 * 业务线告警状态设置
 *
 * @param type
 * @param ids
 */
func setGroupsData(tp string, ids []string) {
	initTimeMap("setGroupsData")
	if !checkTimeMap("setGroupsData", 10) {
		Debug("跳出监控数据更新...")
		return
	}
	Debug("监控数据更新 1" + commonUtil.ToString(ids))
	result := redisUtil.Get_del("get", configure.CacheHostStatusMap+HOST_IDS)
	redisData := make(map[string]string)
	okData := make(map[string]string)
	faildData := make(map[string]string)
	warningData := make(map[string]string)
	unknownData := make(map[string]string)
	if monitorUtil.CheckString(result) {
		mapData := make(map[string]string)
		json.Unmarshal([]byte(result), &mapData)
		json.Unmarshal([]byte(mapData["ok"]), &okData)
		json.Unmarshal([]byte(mapData["faild"]), &faildData)
		json.Unmarshal([]byte(mapData["warning"]), &warningData)
		json.Unmarshal([]byte(mapData["unknown"]), &unknownData)
	}
	okData = clearExpiredMap(okData)
	faildData = clearExpiredMap(faildData)
	warningData = clearExpiredMap(warningData)
	unknownData = clearExpiredMap(unknownData)
	dataMap := getGroupData(faildData, okData, warningData, unknownData, tp, ids)
	Debug("监控数据更新 dataMap " + commonUtil.ToString(dataMap))
	ok,_ := json.Marshal(dataMap["ok"])
	redisData["ok"] = string(ok)
	faild,_ := json.Marshal(dataMap["faild"])
	redisData["faild"] = string(faild)
	warning,_ := json.Marshal(dataMap["warning"])
	redisData["warning"] = string(warning)
	redisData["faild"] = string(faild)
	unknown,_ := json.Marshal(dataMap["unknown"])
	redisData["unknown"] = string(unknown)
	s,_ := json.Marshal(redisData)
	redisUtil.Setex(configure.CacheHostStatusMap+HOST_IDS, 3600, string(s))
	putTimeMap("setGroupsData")
}

/**
	* 清除过期的报警数据
	*
	* @param dataMap
	* @return
	*/
func clearExpiredMap(dataMap map[string]string) map[string]string {
	if len(dataMap) < 1 {
		return make(map[string]string)
	}
	newMap := make(map[string]string)
	for id, value := range dataMap {

		if time.Now().Unix()-commonUtil.ToInt64(value) > 3600 {
			Debug("移除过期的报警数据 " + id)
		} else {
			newMap[id] = value
		}
	}
	return newMap
}

/**
 * @param faildData
 * @param okData
 * @param warningData
 * @param unknownData
 * @param type
 * @param ids
 */
func getGroupData(faildData map[string]string, okData map[string]string, warningData map[string]string, unknownData map[string]string, tp string, ids []string) map[string]map[string]string {
	mapData := make(map[string]map[string]string)
	currTime := commonUtil.ToStr64(time.Now().Unix())
	for _, id := range ids {
		switch tp {
		case "ok":
			okData[id] = currTime
			delete(faildData, id)
			delete(unknownData, id)
			delete(warningData, id)
			break
		case "faild":
			faildData[id] = currTime
			delete(unknownData, id)
			delete(warningData, id)
			delete(okData, id)
			break
		case "warning":
			warningData[id] = currTime
			delete(faildData, id)
			delete(unknownData, id)
			delete(okData, id)
			break
		case "unknown":
			unknownData[id] = currTime
			delete(faildData, id)
			delete(warningData, id)
			delete(okData, id)
			break
		default:
			break
		}
	}
	mapData["ok"] = okData
	mapData["faild"] = faildData
	mapData["warning"] = warningData
	mapData["unknown"] = unknownData
	return mapData
}
