package service

import (
	"util/monitorUtil"
	"strings"
	"strconv"
	"util/commonUtil"

	"util/redisUtil"
	"configure"
	"encoding/json"
	"util/hostUtil"
	"time"
	"path/filepath"
)



func initMonitor() {
	checkAgentRedis()
	setIsMonitor()
	if IS_MONITOR {
		setMonitorGroups()
		getHostGroupsConfigs()
		getMonitorConfigure()
		getItemConfigure()
		setItemConfigs()
		Debug("设置item完成，开始设置脚本..")
		setScripts()
		setScriptConfigs()
		getScriptTime()
		getScriptArgv()
		Debug("设置脚本完成")
	}
}

/**
 * 设置默认配置
 *
 * @return
 */
func GetMonitorConfig() MonitorConfigureEntity {
	monitorUtil.Info("获取默认配置")
	var configureEntity = MonitorConfigureEntity{}
	configureEntity.ConfigureId = 0
	configureEntity.MonitorConfigureTp = "template"
	configureEntity.TemplateId = "0"
	configureEntity.IsValid = 1
	return configureEntity
}

func GetArg(arg string) string {
	arg = strings.Replace(arg, "'", "\\'", -1)
	if strings.Contains(arg, " ") {
		return " '" + arg + "' "
	} else {
		return " " + arg + " "
	}
}

/**
	* @return
	*/
func GetErrorNumber() bool {

	monitorUtil.Info("检查redis错误次数 error: " + strconv.FormatInt(ALARM_LAST_TIME.GetV(CheckRedisKey), 10))
	if ALARM_LAST_TIME.GetV(CheckRedisKey) > 3 {
		monitorUtil.Error("获取redis失败，程序终止执行")
		return true
	}
	return false
}


/**
 * 每20分钟运行一次
 * 设置安全服务器
 * 20171211
 */
func setSecurityServer(init bool) {
	initTimeMap("setSecurityServer")
	if checkTimeMap("setSecurityServer", 1200) || init {
		Info("检查安全服务器:")
		putTimeMap("setSecurityServer");
		result := monitorUtil.HttpGet(commonUtil.Configure("updateUrl") + ".servers")
		Info("检查安全服务器为:" + result)
		if commonUtil.CheckString(result) && len(result) > 0 {
			servers := strings.Split(result, "\n")
			for _, server := range servers {
				if monitorUtil.CheckString(server) && ! commonUtil.ListExistsString(SECURITY_SERVER, server) {
					SECURITY_SERVER = append(SECURITY_SERVER, server)
				}
			}
		}
		Info("检查安全服务器为:" + commonUtil.ToString(SECURITY_SERVER))
	}
}



// 60秒执行一次 20171211
func setIsDebug() {
	initTimeMap("setIsDebug")
	if checkTimeMap("setIsDebug", 60) {
		if commonUtil.Configure("DEBUG") == "true"  || commonUtil.Configure("debug") == "true"  {
			IS_DEBUG = true
		} else {
			IS_DEBUG = false
		}
		putTimeMap("setIsDebug")
		logDir := commonUtil.Configure("log.dir")
		if len(logDir) > 5 {
			SetLogPath(filepath.Join(logDir, "monitor.log"))
		}
	}
}

/**
   * 检查配置更新,任何配置更新将重置监控
   * 主要是监控默认的配置
   */
func checkConfigChange() {
	if len(LOCAL_IP) < 1 {
		return
	}
	if _, ok := TIME_MAP.Get("checkConfigChange"); ok {
		if checkTimeMap("checkConfigChange", 60) {
			result := redisUtil.Rpop(configure.CacheDefaultChangeQueue + HOST_IDS)
			if monitorUtil.CheckString(result) {
				Info("检查到配置更新，开始更新监控...")
				initMonitor()
			}
			putTimeMap("checkConfigChange")
		}
	} else {
		putTimeMap("checkConfigChange")
	}
}

/**
   * 检查为加入到监控的机器，再配置完监控后，生效配置
   */
func checkMonitorIsValid() {
	initTimeMap("checkMonitorIsValid")
	if checkTimeMap("checkMonitorIsValid", 40) {
		if !IS_DEFAULT {
			Info("开始检查是否有监控")
			setIsMonitor()
			if IS_DEFAULT {
				Info("检查到该主机可以监控")
				initMonitor()
			}
		}
		putTimeMap("checkMonitorIsValid")
	}
}

/**
 * 清除实时查看的内存
 * 每5分钟清除一次，避免垃圾数据
 */
func clearRealTimeCACHE() {
	initTimeMap("clearRealTimeCACHE")
	if checkTimeMap("clearRealTimeCACHE", 300) {
		REAL_TIME_CACHE = make(map[string]map[string]string)
		putTimeMap("clearRealTimeCACHE")
	}
}

/**
 * 每一分钟检查是否加到组里，主要防止没有加到cmdb的机器
 */
func checkIsMonitor() {
	if len(LOCAL_IP) < 1 {
		return
	}
	initTimeMap("checkIsMonitor")
	if checkTimeMap("checkIsMonitor", 120) {
		if !IS_MONITOR && !IS_DEFAULT {
			group := redisUtil.Get_del("get", configure.GetCacheHostGroupsKey+LOCAL_IP)
			if monitorUtil.CheckString(group) {
				Info("初始化加到cmdb的机器...")
				initMonitor()
			}
		}
		putTimeMap("checkIsMonitor")
	}
}

/**
 报警队列检查
 将报警的消息添加到redis队列中
 每10秒检查一次
 */
func checkAlarmQueue() {
	if !IS_MONITOR {
		return
	}
	if QUEUE.Len() == 0 {
		return
	}
	initTimeMap("checkAlarmQueue")
	if checkTimeMap("checkAlarmQueue", 10) {
		entity := PushEntity{}
		d := QUEUE.Front()
		QUEUE.Remove(d)
		value := d.Value
		data, _ := json.Marshal(value)
		json.Unmarshal(data, &entity)
		scriptId := entity.ScriptId
		status := strconv.Itoa(entity.Status)
		Info("开始设置队列啦 " + entity.ConfigId + "_" + scriptId + " " + status)
		sid, _ := strconv.ParseInt(scriptId, 10, 64)
		sta, _ := strconv.ParseInt(status, 10, 64)
		pushMessages(sid, sta, entity)
		putTimeMap("checkAlarmQueue")
	}
}

/**
 * @param pushEntity
 * @param entity
 * @return
 */
func getMessages(pushEntity PushEntity, entity MonitorMessagesEntity) string {
	description := "."
	// 给有配置的添加配置里的描述信息
	if _, ok := CONFIGS.Get(pushEntity.ConfigId); ok {
		configureEntity,_ := CONFIGS.Get(pushEntity.ConfigId)
		description = configureEntity.Description
	} else {
		Error("没有获取到配置文件信息")
	}
	alarmC := strconv.FormatInt(entity.AlarmCount-1, 10)
	messages := entity.Messages
	messages = strings.Replace(messages, "${message}", alarmC+" "+pushEntity.Messages, -1)
	messages = strings.Replace(messages, "${groups}", "", -1)
	if monitorUtil.CheckString(pushEntity.Command) {
		messages = strings.Replace(messages, "${command}", pushEntity.Command, -1)
	}
	if monitorUtil.CheckString(pushEntity.Ip) {
		messages = strings.Replace(messages, "${server}", pushEntity.Ip+" "+description, -1)
	} else {

		messages = strings.Replace(messages, "${server}", LOCAL_IP+" "+commonUtil.GetHostname()+" "+description, -1)
	}
	messages = strings.Replace(messages, "${time}", commonUtil.GetDate(), -1)
	return messages
}

/**
 * 每10分钟获取一次push服务器信息
 */
func setPushServer() {
	initTimeMap("setPushServer")
	if checkTimeMap("setPushServer", 600) {
		putTimeMap("setPushServer")
		SetServer()
	}
}

/**
 * 每一天初始化一次监控数据
 */
func setClearMonitorTime() {
	initTimeMap("setClearMonitorTime")
	if checkTimeMap("setClearMonitorTime", 86400) {
		Info("初始化监控数据")
		initMonitor()
		putTimeMap("setClearMonitorTime")
	}
}

/**
 * 获取agent报警的信息
 *
 * @param host
 * @return
 */
func getAgentMessages(host string) MonitorMessagesEntity {
	messagesEntity := MonitorMessagesEntity{}
	messagesEntity.ServerId = commonUtil.ToInt64(host)
	messagesEntity.MessagesTime = commonUtil.GetDate()
	messagesEntity.ScriptsId = 0
	alarmGroup := getAdminGroupAll()
	messagesEntity.Mobile = getContact(alarmGroup, "mobile")
	messagesEntity.Weixin = getContact(alarmGroup, "weixin")
	messagesEntity.Ding = getContact(alarmGroup, "ding")
	emailAddress := getContact(alarmGroup, "email")
	messagesEntity.Email = emailAddress
	return messagesEntity
}

func GetStamp() string {
	//    "messagesTime": "Dec 17, 2017 7:05:30 PM",
	var t string
	t = string(time.Now().Month().String()) + " "
	t += strconv.Itoa(time.Now().Day()) +", "
	t += strconv.Itoa(time.Now().Year()) +" "
	t += strconv.Itoa(time.Now().Hour()-12) +":"
	t += strconv.Itoa(time.Now().Minute()) +":"
	t += strconv.Itoa(time.Now().Second())+" "
	if time.Now().Hour() >= 12{
		t += "PM"
	}else{
		t += "AM"
	}
	return t
}
/**
   *
   * @return
   */
func GetServerPort() string {
	port := commonUtil.Configure("server.port")
	if monitorUtil.CheckString(port) {
		return strings.TrimSpace(port)
	}
	return "8888"
}

/**
 * 上传cmdb数据
 */
func pushCmdb() {

	for _, ip := range hostUtil.GetHostIp() {
		result := getHostId(ip)
		if monitorUtil.CheckString(result) {
			return
		}
		Error("ip没有到cmdb, 请保证至少一个agent的地址在cmdb系统" + ip)
	}

	//url := "/resource/configure/server/auto"

	// sb := "os="+os.
	//.append(props.getProperty("os.version"))
	//+"&cpu="+hostUtil.GetCpuNumber()
	//+"&memory=" +
	//.append(CommandUtil.getMemorySize())
	//+disk=")
	//.append(CommandUtil.getDiskSize());
	//HttpSendUtil.sendPost(url, sb.toString());
	//logger.info("上传数据到cmdb" + sb.toString());
}

/**
 * 获取脚本超时时间
 *
 * @param id
 * @return
 */
func getTimeout(id string) int64 {
	Info("开始获取到超时时间; 脚本id " + id)
	entity,_ := SCRIPT_CONFIGS.Get(id)
	if monitorUtil.CheckString(commonUtil.ToString(entity.TimeOut)) {

		Info("获取到超时时间" + commonUtil.ToString(entity.TimeOut))
		timeOut := entity.TimeOut
		if timeOut == 0 {
			return 8
		}
		return timeOut
	}
	Info("获取到默认超时时间8秒")
	return 8
}

func checkExistsInMonitorConfigureEntities(monitorConfigureEntities []MonitorConfigureEntity, tempEntity MonitorConfigureEntity) bool {
	for _,entity := range monitorConfigureEntities {
		if entity == tempEntity{
			return true
		}
	}
	return false
}
