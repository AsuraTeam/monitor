package service

import (
	"net/http"
	"time"
	"util/commonUtil"
	"io"
	"path/filepath"
	"encoding/json"
)

func InitMonitor() {
	Info("接收到初始化命令开始")
	initMonitor()
	Info("接收到初始化命令完成")
}

func getParam(req *http.Request, key string) string {
	req.ParseForm()
	var id = ""
	if len(req.Form[key]) > 0 {
		id = req.Form[key][0]
	}
	return id
}

/**
更新支持程序
 */
func update(w http.ResponseWriter, req *http.Request)  {
	username := getParam(req,"username")
	password := getParam(req, "password")
	r := AgentUpdate(username , password)
	io.WriteString(w, r)
}

/**
 * 获取实时数据
 * 主要是性能参数的获取，传递脚本ID
 */
//@RequestMapping("/api/realtime")
func realtime(w http.ResponseWriter, req *http.Request) {
	id := getParam(req,"scriptId")
	Info("获取到realtime脚本ID" +  id)
	isCache := false
	var result string
	if _, ok := REAL_TIME_CACHE[id]; ok {
		times := REAL_TIME_CACHE[id]["time"]
		if time.Now().Unix()-commonUtil.ToInt64(times) < 5 {
			isCache = true
		}
	}
	if isCache {
		result = REAL_TIME_CACHE[id]["data"]
	} else {
		dataMap := make(map[string]string)
		stringBuilder := filepath.Join(tempDir, id)
		d, _ := json.Marshal(run(stringBuilder, 10))
		result = string(d)
		dataMap["time"] = commonUtil.ToStr64(time.Now().Unix())
		dataMap["data"] = result
		REAL_TIME_CACHE[id] = dataMap
	}
	io.WriteString(w, result)
}
