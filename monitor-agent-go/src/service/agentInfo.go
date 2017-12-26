package service

import (
	"util/redisUtil"
	"util/commonUtil"
	"configure"
)

/**
 * 每30分钟设置一次
 */
func setAgentServerInfoCron() {
	key := "setAgentServerInfoCron"
	initTimeMap(key)
	if _, ok := TIME_MAP.Get(key); ok {
		pushCmdb()
		initTimeMap(key)
		return
	}
	if checkTimeMap(key, 1800) {
		setAgentServerInfo()
		putTimeMap(key)
	}
}




/**
 * 缓存服务启动端口和主机名
 */
func setAgentServerInfo() {
 mapData := make(map[string]string)
 mapData["port"] = GetServerPort()
 mapData["hostname"] =  commonUtil.GetHostname()
 redisUtil.Setex(configure.CacheAgentServerInfo + HOST_IDS ,86400, commonUtil.ToString(mapData))
}