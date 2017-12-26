package monitorUtil

import (
	"log"
	"os"
	"util/hostUtil"
	"path/filepath"
	"time"
	"net/http"
	"io/ioutil"
	"net"
	"util/redisUtil"
	"configure"
	"encoding/json"

)

var (
	IsDebug bool = true
)

func Info(message string) {
	hostname, _ := os.Hostname()
	log.SetPrefix(hostname + " INFO ")
	if IsDebug {
		log.Println(message)
	}
}

func Error(message string) {
	hostname, _ := os.Hostname()
	log.SetPrefix(hostname + " ERROR ")
	log.Println(message)
}

func CheckString(str string) bool {
	if "" != str && len(str) > 0 {
		return true
	}
	return false
}

func GetTempDir(key string) string {
	home, _ := hostUtil.Home()
	path := filepath.Join(home, "runtime", "monitor", key)
	_,err := os.Stat(path)
	if err != nil {
		Info("创建运行目录 " + path)
		os.MkdirAll(path, 0755)
	}
	return path
}

func Mkdir(dir string) {
	_, err := os.Stat(dir)
	if err != nil {
		os.Mkdir(dir, 1755)
	}
}

func HttpGet(url string) string {
	c := http.Client{
		Transport: &http.Transport{
			Dial: func(netw, addr string) (net.Conn, error) {
				deadline := time.Now().Add(5 * time.Second)
				c, err := net.DialTimeout(netw, addr, time.Second*10)
				if err != nil {
					return nil, err
				}
				c.SetDeadline(deadline)
				return c, nil
			},
		},
	}

	req, err := c.Get(url)
	if err != nil {
		Info("http 请求出错 " + url + err.Error())
		return ""
	}
	body, err := ioutil.ReadAll(req.Body)
	return string(body)
}

/**
 * 检查缓存文件，并获取内容
 *
 * @param name
 * @param interval
 * @return
 */
func CheckCacheFileTime(name string, interval int64) bool {
	fileName := filepath.Join(GetTempDir("cache"), name)
	s, err := os.Stat(fileName)
	if err != nil{
		return true
	}
	modify := s.ModTime().Unix()
	if os.IsNotExist(err) {
		if time.Now().Unix()-modify > interval {
			return true
		} else {
			return false
		}
	} else {
		return true
	}
}

/**
	* @param server
	* @return
	*/
func GetIpHostName(server string) string {
	serverId := redisUtil.Get_del("get", configure.HostsIdKey+server)
	if !CheckString(serverId) {
		return ""
	}
	portData := redisUtil.Get_del("get", configure.CacheAgentServerInfo+serverId)
	if len(portData) > 8 {
		mapData := make(map[string]string)
		json.Unmarshal([]byte(portData), &mapData)
		if len(mapData) > 0 {
			if _, ok := mapData["hostname"]; ok {
				return mapData["hostname"]
			}
		}
	}
	return ""
}

