package service

import (
	"time"
	"strings"
	"fmt"
	"os"
)

func Info(info string)  {
	info = strings.Split(time.Now().String(),".")[0] +" INFO " + info +"\n"
	if IS_DEBUG {
		f, _ := os.OpenFile(LOG_PATH, os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
		f.Write([]byte(info))
		f.Close()
	}else{
		fmt.Println(info)
	}
}

func Error(info string)  {
	info = strings.Split(time.Now().String(),".")[0] +" ERROR " + info + "\n"
	f, _ := os.OpenFile(LOG_PATH, os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
	f.Write([]byte(info))
	f.Close()
}

func Debug(info string)  {
	info = strings.Split(time.Now().String(),".")[0] +" DEBUG " + info +"\n"
	if IS_DEBUG {
		f, _ := os.OpenFile(LOG_PATH, os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
		f.Write([]byte(info))
		f.Close()
	}
}