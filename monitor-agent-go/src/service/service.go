package service

import (
	"configure"
	"util/redisUtil"
	"encoding/json"
	"fmt"
	"util/hostUtil"
	"util/monitorUtil"
	"runtime"
	"strconv"
	"time"
	"github.com/jakecoffman/cron"
	"strings"
	"os"
	"io/ioutil"
	"util/commonUtil"
	"container/list"
	"path/filepath"
)

var (
	VERSION                  string = "go-1.0.0.0"
	IS_MONITOR               bool   = true
	IS_DEFAULT               bool   = true
	IS_DEBUG                 bool   = true
	CACHE_CHECK_MONITOR_LOCK bool   = false
	SCRIPT_RUNTIME                  = MapLockInt64{}
	MONITOR_LOCK                    = MapLockInt64{}
	AGENT_ALARM_MAP                 = MapLockString{}
	HOST_IDS                 string
	LOCAL_IP                 string
	HOST_GROUP               string
	ALARM_COUNT              = MapLockInt64{}
	TIME_MAP                 = MapLockInt64{}
	GROUPS_IDS               []string
	HOST_CONFIGS             []string // 存储ID
	//存放本机拥有的所有项目id
	HOST_ITEMS  []string
	GROUP_ITEMS []string
	SCRIPTS     []string
	SCRIPT_TIME []string

	HOST_CONFIGS_DATA     = make(map[string][]MonitorConfigureEntity)
	GROUP_CONFIGS_DATA    = make(map[string][]MonitorConfigureEntity)
	ITEM_CONFIGS          = make(map[string][]MonitorItemEntity)
	SCRIPT_CONFIGS        = MapLockMonitorScriptEntity{}
	CONFIGS               = MapLockMonitorConfigureEntity{}
	SCRIPT_ITEM_CONFIG    = MapLockMonitorConfigureEntity{}
	ALARM_LAST_TIME       = MapLockInt64{}
	ALARM_INTERVAL        = MapLockInt64{}
	ARGV_HOST_MAP         = MapLockString{}
	SCRIPT_CHECK_INTERVAL = MapLockString{}
	SCRIPT_ITEM           = MapLockString{}
	SCRIPT_ARGV           = MapLockString{}
	SCRIPT_TO_ITEM        = MapLockMonitorItemEntity{}
	SCRIPT_RETRY_MAP      = MapLockMonitorItemEntity{}
	SCRIPT_STATUS         = MapLockPushEntity{}
	SCRIPT_TIME_MAP       = MapLockStringList{}
	tempDir               = monitorUtil.GetTempDir("script")
	INIT_TIME             int64
	SECURITY_SERVER       []string
	QUEUE                 = list.New()
	ALARM_MAP             = MapLockInt64{}
	// 存放实时执行的时间
	REAL_TIME_CACHE = make(map[string]map[string]string)
	PROCESS_PID     string
	// 存储上报过http的信息
	PUSH_NAME_MAP = MapLockString{}
	PID_FILE        = filepath.Join(monitorUtil.GetTempDir("cache"), "pid")
	LOG_PATH        string
)

const (
	CheckRedisKey   = "monitor_check_redis_active"
	SUCCESS_API_URL = "/monitor/api/success"
	FAILD_API_URL   = "/monitor/api/faild"
)

func Start(pid string) {
	PROCESS_PID = pid
	writePid(pid)
	initMonitor()
	Info(HOST_IDS)
	cronStart()
	runFile := filepath.Join(monitorUtil.GetTempDir("cache"), "running")
	for {
		run := commonUtil.ToStr64(time.Now().Unix())
		ioutil.WriteFile(runFile, []byte(run), 0755)
		//checkRunning()
		time.Sleep(time.Second * 10)
		if PROCESS_PID != commonUtil.ReadFile(PID_FILE) {
			Info("获取到新的PID为" + commonUtil.ReadFile(PID_FILE) + " 停止老的进程" + PROCESS_PID)
			break
		}
	}
	os.Exit(0)
}
func SetLogPath(logPath string) {
	LOG_PATH = logPath
}


func writePid(pid string) {
	ioutil.WriteFile(PID_FILE, []byte(pid), 0755)
	Info("启动进程PID为" + pid)
}

func cronStart() {
	cron := cron.New()
	cron.AddFunc("*/3 * * * * ?", func() { scheduledStart() }, "scheduledStart")
	cron.Start()
}

/**
 * 检查是否有脚本要执行
 * 每3秒检查一次
 */
func checkExecScript() {
	initTimeMap("checkExecScript")
	if checkTimeMap("checkExecScript", 3) {
		checkAgentRedis()
		if GetErrorNumber() {
			return
		}

		// 检查是否初始化过
		if 0 == INIT_TIME {
			// 初始化监控
			INIT_TIME = time.Now().Unix()
			Info("init monitor ....")
			initMonitor()
		}

		if !IS_MONITOR {
			initMonitor()
			Error("没有监控项目退出监控1")
			return
		}

		// 如果没有开启监控，则不执行任何东西
		if !IS_MONITOR || len(SCRIPT_TIME) < 1 {
			Error("没有监控项目退出监控2")
			return
		}
		Debug("cron 启动任务计划 " + time.Now().String())
		if 0 == len(SCRIPT_TIME) {
			initMonitor()
			return
		}
		runStart()
		putTimeMap("checkExecScript")
	}
}

/**
  * 任务计划管理
  */
func scheduledStart() {
	commonUtil.ClearLog(LOG_PATH)
	checkAgentRedis()
	if GetErrorNumber() {
		return
	}
	// 每200秒一次
	resetMonitorTime()
	// 设置安全服务器

	setSecurityServer(false)
	// 设置主机正在运行状态  10秒一次
	setHostActive()
	// checkAlarmQueue 10秒钟一次
	checkAlarmQueue()
	// 检查配置文件修改
	checkConfigChange()
	// 设置是否打开DEBUG
	setIsDebug()
	// 清除无效数据
	clearRealTimeCACHE()
	checkIsMonitor()
	setAgentServerInfoCron()
	// agent数据上报
	// 上报服务器信息
	//pushServerInfoCron()
	// 获取上报服务器的地址
	setPushServer()
	// 检查监控配置
	checkMonitorIsValid()
	//1分钟
	checkAgentStatus()
	// 每天初始化数据
	setClearMonitorTime()
	checkMonitorAlarm()
	checkExecScript()
}

func setIsMonitor() {
	hosts := getIsValidHosts()
	for _, ip := range hostUtil.GetHostIp() {
		hostId := getHostId(ip)
		if monitorUtil.CheckString(hostId) {
			HOST_IDS = hostId
			LOCAL_IP = ip
			Info("获取到本机IP地址 " + ip + " id: " + hostId)
			redisUtil.Set(configure.CacheAgentVersion+ip, VERSION)
			redisUtil.Set(configure.CacheAgentCpu+ip, strconv.Itoa(runtime.NumCPU()))
			for _, id := range hosts {
				if id == hostId {
					IS_MONITOR = true
					break
				}
			}
		}
	}
	group := redisUtil.Get_del("get", configure.GetCacheHostGroupsKey+LOCAL_IP)
	if monitorUtil.CheckString(group) {
		Info("set host group is " + group)
		IS_MONITOR = true
		HOST_GROUP = group
	}
	if !IS_MONITOR {
		IS_DEFAULT = false
	}
}

/**
 * 每5分钟
 * 重新设置redis里的数据
 * 检查脚本是否有更新
 */
func resetMonitorTime() {
	initTimeMap("resetMonitorTime")
	if checkTimeMap("resetMonitorTime", 300) {
		if !IS_MONITOR {
			return
		}
		Debug("resetMonitorTime .. start")
		queueKey := configure.CacheHostUpdateQueue + HOST_IDS
		Debug("queue key" + queueKey)
		qlen := redisUtil.Llen(queueKey)
		var i int64
		Debug("获取到队列大小 " + commonUtil.ToStr64(qlen))
		for i = 0; i < qlen; i++ {
			data := redisUtil.Rpop(queueKey)
			Debug("获取到更新状态 " + data)
			if monitorUtil.CheckString(data) {
				// 有更新的化就更新所有信息
				initMonitor()
			}
		}
		Debug("resetMonitorTime .. end")
		putTimeMap("resetMonitorTime")
	}
}

func getIsValidHosts() []string {
	var data = redisUtil.Get_del("get", configure.CacheAllHostIsValid)
	if monitorUtil.CheckString(data) {
		var result []string
		json.Unmarshal([]byte(data), &result)
		return result
	}
	return make([]string, 0)
}

func getHostId(ip string) string {
	return redisUtil.Get_del("get", configure.HostsIdKey+ip)
}

func initTimeMap(key string) {
	if _, ok := TIME_MAP.Get(key); ok {
	} else {
		TIME_MAP.Set(key, time.Now().Unix())
	}
}

func checkTimeMap(key string, expired int64) bool {
	if _, ok := TIME_MAP.Get(key); ok {
		v := TIME_MAP.GetV(key)
		if time.Now().Unix()-v > expired {
			return true
		}
	}
	return false
}

func putTimeMap(key string) {
	TIME_MAP.Set(key, time.Now().Unix())
}

func setHostActive() {
	if !IS_MONITOR && IS_DEFAULT {
		return
	}
	key := "setHostActive"
	initTimeMap(key)
	if checkTimeMap(key, 10) {
		host_key := configure.CacheHostIsUpdate + HOST_IDS
		redisUtil.Setex(host_key, 86400, commonUtil.ToStr64(time.Now().Unix()))
		putTimeMap(key)
	}
}

/**
   * 获取和设置本机所属的组
 */
func setMonitorGroups() {
	data := redisUtil.Get_del("get", configure.CacheHostGroupsKey+HOST_IDS)
	if monitorUtil.CheckString(data) {
		var result []string
		json.Unmarshal([]byte(data), &result)
		for _, gid := range result {
			if monitorUtil.CheckString(gid) {
				GROUPS_IDS = append(GROUPS_IDS, gid)
			}
		}
	}
}

/**
* 获取每个host和group拥有的所有配置文件的id
*/
func getHostGroupsConfigs() {
	HOST_CONFIGS = setHostGroupsConfigs(configure.CacheHostConfigKey, HOST_IDS, HOST_CONFIGS)
	for _, id := range GROUPS_IDS {
		Info("获取组配置信息, 组ID为" + id)
		HOST_CONFIGS = setHostGroupsConfigs(configure.CacheGroupConfigKey, id, HOST_CONFIGS)
	}
}

/**
* 获取每个host和group拥有的所有配置文件的id
*/
func setHostGroupsConfigs(key string, id string, HOST_CONFIGS []string) []string {
	HOST_CONFIGS = append(HOST_CONFIGS, "0")
	Info("获取配置文件...")
	if !IS_DEFAULT {
		Debug("除默认配置外，没有找到已配置的项目,删除配置..")
		if id != "0" {
			redisUtil.Get_del("del", key+id)
		}
	} else {
		result := redisUtil.Get_del("get", key+id)
		Debug("setHostGroupsConfigs" + result)
		if monitorUtil.CheckString(result) {
			var configs []string
			json.Unmarshal([]byte(result), &configs)
			if len(configs) > 0 {
				for _, c := range configs {
					if !commonUtil.ListExistsString(HOST_CONFIGS, c) {
						HOST_CONFIGS = append(HOST_CONFIGS, c)
					}
				}
			}
		}
	}
	return HOST_CONFIGS
}

/**
 * 获取本机的监控数据
 */
func getMonitorConfigure() {
	HOST_CONFIGS_DATA = setMonitorConfigs(HOST_CONFIGS_DATA, HOST_IDS, configure.CacheHostCnfigureKey, HOST_CONFIGS)
	for _, id := range GROUPS_IDS {
		HOST_CONFIGS_DATA = setMonitorConfigs(HOST_CONFIGS_DATA, id, configure.CacheGroupsConfigureKey, HOST_CONFIGS)
	}
}

func setMonitorConfigs(hostConfigsData map[string][]MonitorConfigureEntity, id string, key string, hostConfigs []string) map[string][]MonitorConfigureEntity {
	var monitorConfigureEntities []MonitorConfigureEntity = make([]MonitorConfigureEntity, 0)
	for _, cid := range hostConfigs {
		configString := key + id + "_" + cid
		configId := redisUtil.Get_del("get", configString)
		Info("get config " + id + " " + cid + " " + configId)
		if monitorUtil.CheckString(configId) {
			if !IS_DEFAULT {
				Info("除默认配置外，没有找到已配置的项目,删除配置..")
				redisUtil.Get_del("del", configure.CacheHostConfigKey+id)
				continue
			}
			configId = strings.Replace(configId, "\"", "", -1)
			Info("获取到configId" + configId)
			config := redisUtil.Get_del("get", configure.CacheConfigureKey+configId)
			if !monitorUtil.CheckString(config) {
				Debug("获取到失败的配置,跳出: " + configId)
				continue
			}

			Debug("get config data configId " + configId + " " + config)
			configureEntity := MonitorConfigureEntity{}
			json.Unmarshal([]byte(config), &configureEntity)
			Debug("configureEntity +" + configureEntity.LastModifyTime)
			// 配置文件按id存放
			CONFIGS.Set(configId, configureEntity)
			//var monitorConfigureEntities []MonitorConfigureEntity
			if _, ok := hostConfigsData[id]; ok {
				monitorConfigureEntities = hostConfigsData[id]
			}
			if monitorUtil.CheckString(configureEntity.GroupsId) {
				HOST_GROUP = configureEntity.GroupsId
			}
			monitorConfigureEntities = append(monitorConfigureEntities, configureEntity)
			hostConfigsData[id] = monitorConfigureEntities
		} else {
			Error("configs " + id + " " + cid + " not found from redis")
			tempEntity := GetMonitorConfig()
			if !checkExistsInMonitorConfigureEntities(monitorConfigureEntities, tempEntity) {
				monitorConfigureEntities = append(monitorConfigureEntities, tempEntity)
				hostConfigsData[id] = monitorConfigureEntities
				fmt.Println(hostConfigsData)
				fmt.Println(monitorConfigureEntities)
				Info("添加默认配置..." + commonUtil.ToString(monitorConfigureEntities))
			}
		}
	}
	return hostConfigsData
}

/**
 * 获取拥有的项目的信息
 */
func getItemConfigure() {
	HOST_ITEMS = setItemConfigure(HOST_CONFIGS_DATA, HOST_ITEMS)
	Debug("HOST_ITEMS 1 大小为: " + strconv.Itoa(len(HOST_ITEMS)))
	HOST_ITEMS = setItemConfigure(GROUP_CONFIGS_DATA, HOST_ITEMS)
	Debug("HOST_ITEMS 2 大小为: " + strconv.Itoa(len(HOST_ITEMS)))
}

/**
  * 获取项目的配置信息
  */
func setItemConfigure(maps map[string][]MonitorConfigureEntity, items []string) []string {
	var item string
	for _, mapData := range maps {
		Debug("mapsData id " +  commonUtil.ToString(mapData))
		for _, entity := range mapData {
			configId := entity.ConfigureId
			configTp := entity.MonitorConfigureTp
			if configTp == "item" {
				item = entity.ItemId
				key := commonUtil.ToStr64(configId) + "_" + item
				if !commonUtil.ListExistsString(items, key) {
					Info("获取到监控项目, ID:" + key)
					items = append(items, key)
				}
				SCRIPT_ITEM_CONFIG.Set(item, entity)
			}

			// 添加模板里的项目
			if configTp == "template" {
				templates := entity.TemplateId
				if monitorUtil.CheckString(templates) {
					Debug("获取到templates " + templates)
					temps := strings.Split(templates, ",")
					for temp := range temps {
						items = setItems(strconv.Itoa(temp), items, configId, entity)
					}
				}
			}
		}
	}
	return items
}

func setItems(temp string, items []string, configId int64, monitorConfigureEntity MonitorConfigureEntity) []string {
	templateResult := redisUtil.Get_del("get", configure.CacheTemplateKey+temp)
	if monitorUtil.CheckString(templateResult) {
		templateEntity := MonitorTemplateEntity{}
		json.Unmarshal([]byte(templateResult), &templateEntity)
		itemsList := templateEntity.Items
		for _, item2 := range strings.Split(itemsList, ",") {
			Info("通过模板配置item...")
			items = append(items, strconv.FormatInt(configId, 10)+"_"+item2)
			SCRIPT_ITEM_CONFIG.Set(item2, monitorConfigureEntity)
		}
	}
	// 设置默认监控项目
	defaultItems := redisUtil.Get_del("get", configure.CacheIsDefault)
	Info("获取到defaultItems " + defaultItems)
	if monitorUtil.CheckString(defaultItems) {
		var listData []string
		json.Unmarshal([]byte(defaultItems), &listData)
		configureEntity := GetMonitorConfig()
		Info("添加默认配置... {} " + commonUtil.ToString(configureEntity))
		for _, item := range listData {
			if !commonUtil.ListExistsString(items, "0_"+item) {
				items = append(items, "0_"+item)
				Debug("添加默认ITEM " + "0_" + item)
			}
			SCRIPT_ITEM_CONFIG.Set(item, configureEntity)
		}
		CONFIGS.Set("0", configureEntity)
	}
	Debug("SCRIPT_ITEM_CONFIG 大小为 " + strconv.Itoa(len(SCRIPT_ITEM_CONFIG.GetData())))
	return items
}

/**
   * 获取每个项目的配置信息
*/
func setItemConfigs() {
	ITEM_CONFIGS = setItemConfigsData(ITEM_CONFIGS, HOST_ITEMS)
	Debug("set item configs 1 大小为 " + strconv.Itoa(len(ITEM_CONFIGS)))
	ITEM_CONFIGS = setItemConfigsData(ITEM_CONFIGS, GROUP_ITEMS)
	Debug("set item configs 2 大小为 " + strconv.Itoa(len(ITEM_CONFIGS)))

}

/**
 * 获取每个项目的配置信息
 */
func setItemConfigsData(itemConfigs map[string][]MonitorItemEntity, items []string) map[string][]MonitorItemEntity {
	var itemsConfig []MonitorItemEntity
	for _, ids := range items {
		id := strings.Split(ids, "_")[1]
		Info("get item configure " + id)
		result := redisUtil.Get_del("get", configure.CacheItemKey+id)
		if monitorUtil.CheckString(result) {
			Debug("get item configure " + id + " " + result)
			monitorItemEntity := MonitorItemEntity{}
			json.Unmarshal([]byte(result), &monitorItemEntity)
			if _, ok := itemConfigs[id]; ok {
				itemsConfig = itemConfigs[id]
			} else {
				itemsConfig = make([]MonitorItemEntity, 0)
			}
			if monitorUtil.CheckString(commonUtil.ToStr64(monitorItemEntity.ItemId)) && monitorItemEntity.ItemId > 0 {
				Debug("获取到ITEMCONFIG" + commonUtil.ToString(itemsConfig))
				itemsConfig = append(itemsConfig, monitorItemEntity)
				itemConfigs[id] = itemsConfig
				// 脚本对应的项目ID，然后发信息时
				sid := commonUtil.ToStr64(monitorItemEntity.ScriptId)
				Info("set SCRIPT_ITEM " + sid)
				SCRIPT_ITEM.Set(sid, commonUtil.ToString(monitorItemEntity))
			}
		} else {
			Error("item " + id + " not found from redis")
		}
	}
	return itemConfigs
}

/**
   * 获取脚本信息
   */
func setScripts() {
	for _, item := range HOST_ITEMS {
		Info("set scripts " + item)
		// 获取项目ID和配置ID
		items := strings.Split(item, "_")
		itemId := items[1]
		configId := items[0]
		for _, entry := range ITEM_CONFIGS {
			for _, monitorItemEntity := range entry {
				if commonUtil.ToStr64(monitorItemEntity.ItemId) == itemId {
					scriptId := commonUtil.ToStr64(monitorItemEntity.ScriptId)
					key := configId + "_" + scriptId
					if !commonUtil.ListExistsString(SCRIPTS, key) {
						SCRIPTS = append(SCRIPTS, key)
					}
					 _, ok := SCRIPT_TO_ITEM.Get(key)
					if ! ok {
						SCRIPT_TO_ITEM.Set(key, monitorItemEntity)
						Info("SCRIPTS add " + configId + "_" + scriptId)
					}
				}
			}
		}
	}
}

/**
 * 获取拥有脚本的脚本内容
 */
func setScriptConfigs() {
	monitorUtil.Mkdir(tempDir)
	monitorScriptsEntity := MonitorScriptsEntity{}
	var file string
	for _, ids := range SCRIPTS {
		id := strings.Split(ids, "_")[1]
		monitorScriptsEntity = getScripts(id)
		if monitorScriptsEntity.ScriptsId > 0 {
			file = filepath.Join(tempDir, id)
			SCRIPT_CONFIGS.Set(id, monitorScriptsEntity)
			Info(file)
			ioutil.WriteFile(file, []byte(monitorScriptsEntity.Content), 0755)
		} else {
			Error("脚本 " + id + " 没有找到配置" + commonUtil.ToString(monitorScriptsEntity))
		}
	}
}

/**
 * 获取脚本内容
 * 2017-12-10
 */
func getScripts(scriptsId string) MonitorScriptsEntity {
	Debug("获取脚本数据,脚本ID为" + scriptsId)
	scriptsEntity := MonitorScriptsEntity{}
	for i := 0; i < 5; i ++ {
		script := HttpPost("monitor/scripts/api/scripts", "scriptsId="+scriptsId)
		Debug("脚本内容为:  " + scriptsId + script)
		if monitorUtil.CheckString(script) {
			json.Unmarshal([]byte(script), &scriptsEntity)
			Debug("获取到转换后脚本内容" + commonUtil.ToString(scriptsEntity))
			return scriptsEntity
		}
		Debug("获取脚本重试" + strconv.Itoa(i))
	}
	return scriptsEntity
}

/**
  * 每8秒检查一次，连续10次失败，程序先停止
  * 主要针对redis用域名连接域名失败的情况
  */
func checkAgentRedis() {
	initTimeMap(CheckRedisKey)
	Debug("开始检查redis可用")
	if checkTimeMap(CheckRedisKey, 9) {
		result := redisUtil.Get_del("get", CheckRedisKey)
		if ! monitorUtil.CheckString(result) {
			if ALARM_LAST_TIME.GetV(CheckRedisKey) > 10 {
				Error("redis  失败, 不执行set操作 " + strconv.FormatInt(ALARM_LAST_TIME.GetV(CheckRedisKey), 10))
				return
			}
			set := redisUtil.Set(CheckRedisKey, strconv.FormatInt(time.Now().Unix(), 10))
			if set == "err" {
				if _, ok := ALARM_LAST_TIME.Get(CheckRedisKey); ok {
					Error("redis  失败 " + strconv.FormatInt(ALARM_LAST_TIME.GetV(CheckRedisKey), 10))
					ALARM_LAST_TIME.Set(CheckRedisKey, ALARM_LAST_TIME.GetV(CheckRedisKey)+1)
				} else {
					ALARM_LAST_TIME.Set(CheckRedisKey, 1)
				}
			}
			putTimeMap(CheckRedisKey)
			return
		}
		putTimeMap(CheckRedisKey)
		ALARM_LAST_TIME.Set(CheckRedisKey, 0)
	}
}

/**
 * 脚本执行时间归类
 * 脚本执行的时间归类
 * 脚本执行时间要执行的脚本归类
 */
func getScriptTime() {
	var checkTime []string
	for _, item := range HOST_ITEMS {
		// 获取项目ID和配置ID
		items := strings.Split(item, "_")
		itemId, _ := strconv.ParseInt(items[1], 10, 64)
		configId := items[0]
		for _, entityEntry := range ITEM_CONFIGS {
			for _, entity := range entityEntry {
				if entity.ItemId != itemId {
					continue
				}
				configureEntity, _ := CONFIGS.Get(configId)
				Debug("配置监控时间" + commonUtil.ToString(configureEntity))
				// 为模板时从item获取配置
				checkTime = make([]string, 0)
				if configureEntity.MonitorConfigureTp == "template" {
					Debug("通过模板获取到item配置" + commonUtil.ToStr64(itemId))
					SCRIPT_CHECK_INTERVAL.Set(commonUtil.ToStr64(configureEntity.ConfigureId)+"_"+commonUtil.ToStr64(entity.ScriptId), entity.CheckInterval)
					// 监控的时间,单位为秒
					if !commonUtil.ListExistsString(SCRIPT_TIME, entity.CheckInterval) {
						SCRIPT_TIME = append(SCRIPT_TIME, entity.CheckInterval)
					}

					if _, ok := SCRIPT_TIME_MAP.Get(entity.CheckInterval); ok {
						checkTime, _ = SCRIPT_TIME_MAP.Get(entity.CheckInterval)
					}

					checkTimeV := configId + "_" + commonUtil.ToStr64(entity.ScriptId)
					if ! commonUtil.ListExistsString(checkTime, checkTimeV) {
						Debug("checkTime add 1 " + checkTimeV)
						checkTime = append(checkTime, checkTimeV)
					}

					//// 每个map里存放每个时间点要执行的脚本id
					//_, ok := SCRIPT_TIME_MAP.Get(entity.CheckInterval)
					//if ! ok {
						SCRIPT_TIME_MAP.Set(entity.CheckInterval, checkTime)
						SCRIPT_RETRY_MAP.Set(checkTimeV, entity)
					//}
				}
				checkTime = make([]string, 0)
				if configureEntity.MonitorConfigureTp == "item" {
					CheckInterval := commonUtil.ToStr64(configureEntity.CheckInterval)

					if !commonUtil.ListExistsString(SCRIPT_TIME, CheckInterval) {
						Info("通过item去配置SCRIPT_TIME_MAP")
						SCRIPT_TIME = append(SCRIPT_TIME, CheckInterval)
						Info("SCTIPT_TIME set " + commonUtil.ToString(SCRIPT_TIME))
					}
					if _, ok1 := SCRIPT_TIME_MAP.Get(CheckInterval); ok1 {
						checkTime, _ = SCRIPT_TIME_MAP.Get(CheckInterval)
						Info("SCRIPT_TIME_MAP 包含 " + commonUtil.ToString(checkTime))
					}
					timeKey := commonUtil.ToStr64(configureEntity.ConfigureId) + "_" + commonUtil.ToStr64(entity.ScriptId)
					SCRIPT_CHECK_INTERVAL.Set(timeKey, CheckInterval)
					Info("checkTime add 2 " + timeKey)
					if !commonUtil.ListExistsString(checkTime, timeKey) {
						checkTime = append(checkTime, timeKey)
						SCRIPT_TIME_MAP.Set(CheckInterval, checkTime)
						monitorItemEntity := MonitorItemEntity{}
						monitorItemEntity.AlarmCount = configureEntity.AlarmCount
						monitorItemEntity.Retry = configureEntity.Retry
						monitorItemEntity.AlarmInterval = configureEntity.AlarmInterval
						SCRIPT_RETRY_MAP.Set(timeKey, monitorItemEntity)
					}
				}
			}

		}
	}
}

/**
 * 设置脚本的参数
 */
func getScriptArgv() {
	Debug("HOST_ITEMS 大小为 " + strconv.Itoa(len(HOST_ITEMS)))
	Debug("获取到ITEM CONFIGS 大小为 " + strconv.Itoa(len(ITEM_CONFIGS)))
	Debug("获取到ITEM CONFIG 内容为" + commonUtil.ToString(ITEM_CONFIGS))
	for _, item := range HOST_ITEMS {
		// 获取项目ID和配置ID
		items := strings.Split(item, "_")
		itemId := items[1]
		configId := items[0]
		for _, entityEntry := range ITEM_CONFIGS {
			for _, entity := range entityEntry {
				if commonUtil.ToStr64(entity.ItemId) != itemId {
					continue
				}
				scriptId := configId + "_" + commonUtil.ToStr64(entity.ScriptId)
				configureEntity, _ := CONFIGS.Get(configId)
				var args string
				if configureEntity.MonitorConfigureTp == "item" {
					Info("获取通过item配置的参数....")
					args = getArg(configureEntity, MonitorItemEntity{})
				} else {
					Info("获取通过template配置的参数....")
					args = getArg(MonitorConfigureEntity{}, entity)
				}
				SCRIPT_ARGV.Set(scriptId, args)
				Debug("SCRIPT_ARGV put " + scriptId + " " + args)
			}
		}
	}
}

/**
 * 获取脚本的参数
 *
 * @return
 */
func getArg(configureEntity MonitorConfigureEntity, itemEntity MonitorItemEntity) string {
	var entityString string
	if configureEntity.CheckInterval > 0 {
		entityString = commonUtil.ToString(configureEntity)
	} else {
		entityString = commonUtil.ToString(itemEntity)
	}
	entity := ArgsEntity{}
	json.Unmarshal([]byte(entityString), &entity)
	Info("getArg " + commonUtil.ToString(entity))
	Info("getArg " + entityString)
	var args string
	if monitorUtil.CheckString(entity.Arg1) {
		args += GetArg(entity.Arg1)
	}
	if monitorUtil.CheckString(entity.Arg2) {
		args += GetArg(entity.Arg2)
	}
	if monitorUtil.CheckString(entity.Arg3) {
		args += GetArg(entity.Arg3)
	}
	if monitorUtil.CheckString(entity.Arg4) {
		args += GetArg(entity.Arg4)
	}
	if monitorUtil.CheckString(entity.Arg5) {
		args += GetArg(entity.Arg5)
	}
	if monitorUtil.CheckString(entity.Arg6) {
		args += GetArg(entity.Arg6)
	}
	if monitorUtil.CheckString(entity.Arg7) {
		args += GetArg(entity.Arg7)
	}
	if monitorUtil.CheckString(entity.Arg8) {
		args += GetArg(entity.Arg8)
	}
	// 最后一个参数为IP地址
	args += " " + LOCAL_IP
	Info("获取到args为 " + args)
	return args
}
