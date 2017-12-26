package main

import (
	_"godaemon"
	"path/filepath"
	"os"
	"io/ioutil"
	"time"
	"fmt"
	"os/user"
	"runtime"
	"bytes"
	"os/exec"
	"strings"
	"errors"
	"strconv"
	"log"
)

// Home returns the home directory for the executing user.
//
// This uses an OS-specific method for discovering the home directory.
// An error is returned if a home directory cannot be detected.
func Home() (string, error) {
	user, err := user.Current()
	if nil == err {
		return user.HomeDir, nil
	}

	// cross compile support
	if "windows" == runtime.GOOS {
		return homeWindows()
	}

	// Unix-like system, so just assume Unix
	return homeUnix()
}

func homeUnix() (string, error) {
	// First prefer the HOME environmental variable
	if home := os.Getenv("HOME"); home != "" {
		return home, nil
	}

	// If that fails, try the shell
	var stdout bytes.Buffer
	cmd := exec.Command("sh", "-c", "eval echo ~$USER")
	cmd.Stdout = &stdout
	if err := cmd.Run(); err != nil {
		return "", err
	}

	result := strings.TrimSpace(stdout.String())
	if result == "" {
		return "", errors.New("blank output when reading home directory")
	}

	return result, nil
}

func homeWindows() (string, error) {
	drive := os.Getenv("HOMEDRIVE")
	path := os.Getenv("HOMEPATH")
	home := drive + path
	if drive == "" || path == "" {
		home = os.Getenv("USERPROFILE")
	}
	if home == "" {
		return "", errors.New("HOMEDRIVE, HOMEPATH, and USERPROFILE are blank")
	}

	return home, nil
}

func Info(info string) {
	info = strings.Split(time.Now().String(), ".")[0] + " INFO " + info + "\n"
	fmt.Println(info)
}


func GetTempDir(key string) string {
	home, _ :=Home()
	path := filepath.Join(home, "runtime", "monitor", key)
	_,err := os.Stat(path)
	if err != nil {
		Info("创建运行目录 " + path)
		os.MkdirAll(path, 0755)
	}
	return path
}

func ToInt64(str string) int64 {
	r, _ := strconv.ParseInt(str, 10, 64)
	return r
}

func startMonitor(data string) {
	if os.Getppid() != 1 { //判断当其是否是子进程，当父进程return之后，子进程会被 系统1 号进程接管
		Info("启动监控进程..., 最后更新时间为" + string(data))                            //开始执行新进程，不等待新进程退出
		filePath, _ := filepath.Abs(strings.Replace(os.Args[0], ".daemon", "", -1)) //将命令行参数中执行文件路径转换成可用路径
		cmd := exec.Command(filePath, os.Args[1:]...)
		//将其他命令传入生成出的进程
		cmd.Stdin = os.Stdin //给新进程设置文件描述符，可以重定向到文件中
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		cmd.Start()
		return
	}

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


func ToStr64(n int64) string {
	return strconv.FormatInt(n, 10)
}

func checkRunning() {
	runFile := filepath.Join(GetTempDir("cache"), "running")
	_, err := os.Stat(runFile)
	if err == nil {

		data, _ := ioutil.ReadFile(runFile)
		last := ToInt64(string(data))
		// 10秒没有更新就启动进程
		if time.Now().Unix()-last > 20 {
			startMonitor(string(data))
		} else {
			Info("监控进程正常...最后更新时间为" + string(data))
		}
	} else {
		startMonitor("")
		Info("没有找到运行文件" + err.Error())
	}
}

// 检查进程是否存在没有的话就重新启动给一个
// 该进程和监控进程互相监控，2个哪个死了都可以使用另一个启动
func main() {
	PROCESS_PID := strconv.Itoa(os.Getpid())
	PID_FILE := filepath.Join(GetTempDir("cache"), "daemon.pid")
	ioutil.WriteFile(PID_FILE, []byte(PROCESS_PID), 0755)
	runFile := filepath.Join(GetTempDir("cache"), "running.daemon")
	for {
		checkRunning()
		time.Sleep(time.Second * 7)
		ioutil.WriteFile(runFile, []byte( ToStr64(time.Now().Unix())), 0755)
		if PROCESS_PID != ReadFile(PID_FILE) {
			log.Println("获取到新的PID为" + ReadFile(PID_FILE) + " 停止老的守护进程" + PROCESS_PID)
			break
		}
	}
}
