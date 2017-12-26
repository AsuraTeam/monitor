package service

import (
	"util/commonUtil"
	"util/monitorUtil"
	"path/filepath"
	"strings"
	"os"
	"io/ioutil"
	"os/exec"
	"io"
)

/**
 * 更新监控程序，通过到指定的服务器下载新监控程序更新
 */
func AgentUpdate(username string, password string) string {

	// 获取用户名,密码
	configUser := commonUtil.Configure("username")
	configPassword := commonUtil.Configure("password")

	noUserMsg := "用户名或密码错误,更新程序退出"
	if len(configPassword) > 1 && len(configUser) > 1 {
		if username == "" || password == "" {
			Error(noUserMsg)
			return noUserMsg
		}
		if !(username == configUser && password == configPassword) {
			Error(noUserMsg)
			return noUserMsg
		}
	}

	url := commonUtil.Configure("updateUrl")
	if len(url) < 4 {
		Error("下载配置没有,updateUrl=http://xx.xx/file/")
		return ""
	}
	file, _ := exec.LookPath(os.Args[0])
	// 临时文件定义
	var tempFile = filepath.Join(monitorUtil.GetTempDir("cache"), "monitor")
	var tempMd5 = filepath.Join(monitorUtil.GetTempDir("cache") , "monitor.md5")

	Info("下载更新包开始...." + url)
	commonUtil.DownloadNet(url, tempFile)
	Info("下载更新包接收.... " + tempFile)

	_, err := os.Stat(tempFile)
	if err == nil {

		// 获取文件的md5
		var md5 = commonUtil.GetMd5ByFile(strings.TrimSpace(tempFile))
		if !commonUtil.CheckString(md5){
			Error("获取文件错误,文件为空")
			return "获取文件错误,文件为空"
		}
		Info("更新包的文件md5值为: " + md5)

		// 获取提供更新包下载的MD5
		commonUtil.DownloadNet(url+".md5", tempMd5)

		var md5Msg = "没有获取到更新包的MD5值,更新程序结束"
		var fmd5 = commonUtil.GetMd5ByFile(tempMd5)
		var rmd5 string
		if len(fmd5) > 0 {
			rmd51, _ := ioutil.ReadFile(tempMd5)
			rmd5 = string(rmd51)
			Info("提供的更新包md5值为:" + string(rmd5))
		} else {
			Error(md5Msg)
			return md5Msg + " 获取MD5失败 " + url + ".md5"
		}

		// 对比已经下载的更新包的MD5值和远程文件提供的md5
		var exitMsg = "更新包提供的MD5和实际下载的MD5值不一致,更新退出"
		if rmd5 == md5 {
			Info("更新包的md5和提供的md5一致")
		} else {
			Info(exitMsg)
			return exitMsg
		}
		src, err := os.Open(tempFile)
		defer src.Close()
		if err != nil {
			Error("写入文件失败" + tempFile)
			return "写入文件失败" + tempFile
		}
		dst, err := os.Create(file)
		defer dst.Close()
		if err != nil {
			Error("写入文件失败" + file)
			return "写入文件失败" + file
		}
		io.Copy(src, dst)
		Info("更新文件完成")
		// 删除文件
		os.Remove(tempFile)
		os.Remove(tempMd5)
	} else {
		Error("下载文件失败")
		return "faild"
	}

	Info("执行杀死进程" + PROCESS_PID)
	defer run("kill -9 "+PROCESS_PID, 10)
	Info("执行杀死进程完成")
	return "ok"
}
