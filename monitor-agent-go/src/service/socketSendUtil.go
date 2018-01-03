package service

import (
	"util/redisUtil"
	"configure"
	"encoding/json"
	"math/rand"
	"strconv"
	"strings"
	"util/commonUtil"
)

var (
	serverList []string
	ports      []string
	Url        string
)

type PushServerEntity struct {
	Ip string
}

func Init() {
	if len(serverList) < 1 {
		setServerList()
	}
	//num := rand.Intn(len(serverList))
	if len(ports) < 1 {
		ports = make([]string, 0)
		for i := 50000; i < 50300; i++ {
			ports = append(ports, strconv.Itoa(i))
		}
	}
}

/**
   * 先优先使用redis的
   */
func setServerList() {
	if len(serverList) < 1 {
		serverList = make([]string, 0)
	}
	pushServers := redisUtil.Get_del("get", configure.CachePushServer)
	if commonUtil.CheckString(pushServers) {
		var serverEntities []PushServerEntity
		json.Unmarshal([]byte(pushServers), &serverEntities)
		for _, serverEntity := range serverEntities {
			addr := serverEntity.Ip
			for _, ip := range serverList {
				if ip == addr {
					continue
				}
			}
			Info("添加push服务Redis " + addr)
			serverList = append(serverList, addr)
		}
	}
}

/**
 * 随机链接服务器
 *
 * @return
 */
func GetServer(address string) string {
	if GetErrorNumber() {
		Info("获取到redis失败，程序不再执行")
		return ""
	}
	if len(serverList) > 0 {
		if address == "" {
			id := rand.Intn(len(serverList))
			return serverList[id]
		}
		// 去除坏了的连接
		for i := 0; i < len(serverList); i++ {
			if serverList[i] != address {
				return serverList[i]
			}
		}
	} else {
		serverList = make([]string, 0)
		setServerList()
		return ""
	}
	return ""
}

/**
 * 每10分钟重新获取一次
 * 设置上传服务器
 */
func SetServer() {
	serverList = make([]string, 0)
	key := configure.CachePushServer
	addrs := redisUtil.Get_del("get", key)
	if len(addrs) > 8 {
		var list = []PushServerEntity{}
		json.Unmarshal([]byte(addrs), &list)
		for _, entity := range list {
			address := entity.Ip
			isExists := false
			for _, add := range serverList {
				if add == address {
					isExists = true
				}
			}
			if isExists {
				continue
			}
			if SendData("[{}]", address, strconv.Itoa(50329)) && checkServerValid(address) {
				Info("获取到PUSH服务器地址" + entity.Ip)
				serverList = append(serverList, address)
			}
		}
	}
}

func checkServerValid(addr string) bool {
	port := redisUtil.Get_del("get", configure.GetCachePushServerPort+addr)
	port = strings.Replace(port, "/", "", -1)
	port = strings.Replace(port, " ", "", -1)
	if commonUtil.CheckString(port) {
		return true
	}
	return false
}

/**
 * 初始化server
 */
func SetHttpServer(failServer string) {
	address := GetServer(failServer)
	if !commonUtil.CheckString(address) {
		return
	}
	//Server := address
	port := redisUtil.Get_del("get", configure.GetCachePushServerPort+address)
	Url = "http://" + address + ":" + port + "/"
	Info(Url)
}

func getServerListSize() int {
	return len(serverList)
}
