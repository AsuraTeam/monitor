package service

import (
	"net/http"
	"io"
	"time"
)

func httpLog(req *http.Request) {
	Info("请求 " + req.URL.Path + " " + req.RemoteAddr)
}

func initApi(w http.ResponseWriter, req *http.Request) {
	httpLog(req)
	InitMonitor()
	io.WriteString(w, "ok")
}

func HttpServer() {
	http.HandleFunc("/monitor/init", initApi)
	http.HandleFunc("/api/realtime", realtime)
	http.HandleFunc("/update", update)
	var err error
	for i := 0; i < 10; i++ {
		err = http.ListenAndServe(":"+GetServerPort(), nil)
		if err == nil {
			break
		} else {
			Error("启动HTTP服务失败 " + err.Error())
			time.Sleep(time.Second * 5)
		}
	}
	if err != nil {
		Error("ListenAndServe" + err.Error())
	}

}
