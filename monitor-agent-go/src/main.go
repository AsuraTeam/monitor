package main

import (
	"service"
	_ "godaemon"
	"os"
	"util/commonUtil"
	"path/filepath"
	"os/exec"
	"util/hostUtil"
	"strconv"

)

var (
	PROCESS_PID string
)

func setLogPath() {
	logDir := commonUtil.Configure("log.dir")
	_, err := os.Stat(logDir)
	if err != nil || !commonUtil.CheckString(logDir) {
		file, _ := exec.LookPath(os.Args[0])
		logDir = filepath.Dir(file)
	}

	service.SetLogPath(filepath.Join(logDir, "monitor.log"))
	service.Info("设置日志路径为 " + service.LOG_PATH)
}

func main() {
	var CONFIG_PATH string
	if len(os.Args) > 1 {
		CONFIG_PATH = os.Args[1]
		commonUtil.Info("获取到配置文件路径为" + CONFIG_PATH)
	} else {
		home, _ := hostUtil.Home()
		CONFIG_PATH = filepath.Join(home, "monitor.conf")
		commonUtil.Info("获取到配置文件路径为空,使用默认配置文件路径" + CONFIG_PATH)
	}

	PROCESS_PID = strconv.Itoa(os.Getpid())
	commonUtil.SetConfigPath(CONFIG_PATH)
	setLogPath()
	go func() {
		service.HttpServer()
	}()
	service.Start(PROCESS_PID)
}

