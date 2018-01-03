package redisUtil

import (
	"github.com/garyburd/redigo/redis"
	"encoding/json"
	"strings"
	"util/commonUtil"
	"os"
	"log"
)

const APP = "monitor"

var (
	URL string = ""
	pool *redis.Pool
)

func newPool() *redis.Pool {
	return &redis.Pool{
		MaxIdle: 80,
		MaxActive: 12000, // max number of connections
		Dial: func() (redis.Conn, error) {
			c, err := redis.Dial("tcp", URL)
			if err != nil {
				log.Println(err.Error())
			}
			return c, err
		},
	}

}
/**
* connect redis
 */
func getC() redis.Conn {
	if len(URL) < 1 {
		server := commonUtil.Configure("redis.server")
		if !commonUtil.CheckString(server) {
			log.Fatal("没有找到redis服务器配置,程序退出...")
			os.Exit(2)
		}
		port := commonUtil.Configure("redis.port")
		if !commonUtil.CheckString(port) {
			port = "6379"
		}
		URL = server + ":" + port
		pool = newPool()
	}
	c := pool.Get()
	return c
}

/**
 */
func Get_del(action string, key string) string {
	c := getC()
	if c != nil {
		r, err := redis.String(c.Do(action, APP+"_"+key))
		commonUtil.Info("get " + APP + "_" + key)
		if err != nil {
		//	c.Close()
			return ""
		}
		return r
	}
	return ""
}

func Get(key string, c redis.Conn) string {
	if c != nil {
		r, err := redis.String(c.Do("get", APP+"_"+key))
		if err != nil {
		//	c.Close()
			return ""
		}
		return r
	}
	return ""
}

func Mget(key string) []string {
	c := getC()
	result := make([]string, 0)
	keys := strings.Split(key, " ")
	for _, k := range keys {
		r := Get(k, c)
		result = append(result, r)
	}
	return result
}

func Llen(key string) int64 {
	c := getC()
	if c != nil {
		data, err := c.Do("llen", APP+"_"+key)
		commonUtil.Info("llen " + APP + "_" + key)
		if err == nil {
			v, _ := json.Marshal(data)
		//	c.Close()
			return commonUtil.ToInt64(string(v))
		}
	}
	return 0
}

func Set(key string, value string) string {
	c := getC()
	if c != nil {
		_, err := c.Do("set", APP+"_"+key, value)
		commonUtil.Info("set " + APP + "_" + key)
		if err != nil {
			return "ok"
		//	c.Close()
		}
	}
	return "err"
}

func Setex(key string, expired int, value string) {
	c := getC()
	if c != nil {
		c.Do("setex", APP+"_"+key, expired, value)
		commonUtil.Info("setex " + APP + "_" + key + " " + value)
	}
}

func Rpop(key string) string {
	c := getC()
	if c != nil {
		data, err := c.Do("rpop", APP+"_"+key)
		commonUtil.Info("rpop " + APP + "_" + key)
		if err != nil {
		//	c.Close()
			d, err := json.Marshal(data)
			if err == nil {
				return string(d)
			}
		}
	}
	return ""
}

func Lpush(key string, value string) string {
	c := getC()
	if c != nil {
		data, err := c.Do("lpush", APP+"_"+key, value)
		commonUtil.Info("lpush " + APP + "_" + key)
		if err == nil {
		//	c.Close()
			d, err := json.Marshal(data)
			if err == nil {
				return string(d)
			}
		}
	}
	return ""
}
