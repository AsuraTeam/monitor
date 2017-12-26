package service

import (
	"util/commonUtil"
	"path/filepath"
)


func execPing(server string)string  {
	server = commonUtil.Replace(server)
	separator := string(filepath.Separator)
	var command string
	if separator == "/" {
		// linux
		command = "ping -c 10 -i 1 "
	}else{
		// windows
		command = "ping -n 2 -w 1 "
	}
	return runScript(command, 20)
}
