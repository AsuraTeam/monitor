package commonUtil

import (
	"os"
	"log"
	"encoding/json"
	"strings"
	"time"
	"strconv"
	"io/ioutil"
	"crypto/md5"
	"net/http"
	"io"
)

var (
	IsDebug     bool = true
	CONFIG_PATH string
	LOG_SIZE    int64
	FUNC_LOCK   = MapLockString{}
)

func SetConfigPath(conf string) {
	CONFIG_PATH = conf
}

func CheckString(str string) bool {
	if "" != str && len(str) > 0 {
		return true
	}
	return false
}

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

func ListExistsString(arr []string, value string) bool {

	for _, v := range arr {
		if v == value {
			return true
		}
	}
	return false
}

func ToString(v interface{}) string {
	if _, ok := FUNC_LOCK.Get("ToString"); ok {
		return ""
	}
	FUNC_LOCK.Set("ToString", "1")
	b, err := json.Marshal(v)
	if err == nil {
		FUNC_LOCK.Delete("ToString")
		return string(b)
	}
	FUNC_LOCK.Delete("ToString")
	return ""
}

func GetDate() string {
	return strings.Split(time.Now().Local().String(), ".")[0]
}

func GetHostname() string {
	name, _ := os.Hostname()
	return name
}

func MapContains(mapData map[string]interface{}, key string) bool {
	if _, ok := mapData[key]; ok {
		return true
	}
	return false
}

func MapContainsString(mapData map[string]string, key string) bool {
	if _, ok := mapData[key]; ok {
		return true
	}
	return false
}

func MapContainsInt64(mapData map[string]int64, key string) bool {
	if _, ok := mapData[key]; ok {
		return true
	}
	return false
}

func ToInt64(str string) int64 {
	r, _ := strconv.ParseInt(str, 10, 64)
	return r
}

func ToStr64(n int64) string {
	return strconv.FormatInt(n, 10)
}

func ReadFile(fileName string) string {
	d, err := ioutil.ReadFile(fileName)
	if err == nil {
		return string(d)
	} else {
		log.Println("读取文件失败 " + err.Error())
	}
	return ""
}

func MapToString(dataMap map[string]string) string {
	d, err := json.Marshal(dataMap["ok"])
	if err != nil {
		return string(d)
	}
	return ""
}

func Replace(ip string) string {
	data := strings.Split(" ,{,},;,&", ",")
	for i := 0; i < len(data); i++ {
		ip = strings.Replace(ip, data[i], "", -1)
	}
	return ip
}

func Configure(key string) string {
	data, err := ioutil.ReadFile(CONFIG_PATH)
	log.Println(CONFIG_PATH)
	if err != nil {
		Error("配置文件读取错误 " + err.Error())
		os.Exit(1)
	}
	strData := string(data)
	datas := strings.Split(strData, "\n")
	for _, d := range datas {
		if strings.Contains(d, "#") {
			continue
		}
		args := strings.Split(d, "=")
		if args[0] == key {
			return strings.TrimSpace(strings.TrimSpace(args[1]))
		}
	}
	return ""
}

func ClearLog(LOG_PATH string) {
	file, err := os.Stat(LOG_PATH)
	if err != nil {
		return
	}
	if LOG_SIZE == 0 {
		size := Configure("log.size")
		if CheckString(size) {
			LOG_SIZE = ToInt64(size)
		} else {
			LOG_SIZE = 100000000
		}
		log.Println("获取到日志文件大小设置为:" + ToStr64(LOG_SIZE))
	}

	logSize := os.FileInfo(file).Size()
	if logSize >= LOG_SIZE {
		os.Truncate(LOG_PATH, 0)
		log.Println("清空日志文件")
	}
}

func GetMd5ByFile(file string) string {
	f, err := os.Open(file)
	if err != nil {
		Error("Open" + err.Error())
		return ""
	}

	defer f.Close()
	body, err := ioutil.ReadAll(f)
	if err != nil {
		Error("ReadAll" + err.Error())
		return ""
	}
	r := md5.Sum(body)
	return string(r[:])
}

func DownloadNet(url string, file string) {
	res, err := http.Get(url)
	if err == nil {
		f, err := os.Create(file)
		if err != nil {
			io.Copy(f, res.Body)
		}
	}
}
