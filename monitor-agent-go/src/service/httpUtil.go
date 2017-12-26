package service

import (
	"time"
	"net/http"
	"strings"
	"io/ioutil"
	"net"
	"util/commonUtil"
	"fmt"
)

var (
	PushServerTime map[string]int64
)


func HttpPost(url string, param string) string {
    Info(url + param)
	if len(Url) < 1 {
		fmt.Println(Url)
		Init()
		SetServer()
		SetHttpServer("")
		PushServerTime = make(map[string]int64)
		PushServerTime["time"] = time.Now().Unix()
		commonUtil.Info("初始化PushServer....")
	}
	client := &http.Client{
		Transport: &http.Transport{
			Dial: func(netw, addr string) (net.Conn, error) {
				conn, err := net.DialTimeout(netw, addr, time.Second * 6) //设置建立连接超时
				if err != nil {
					return nil, err
				}
				conn.SetDeadline(time.Now().Add(time.Second * 12)) //设置发送接受数据超时
				return conn, nil
			},
			ResponseHeaderTimeout: time.Second * 12,
		},
	}
	if len(Url) < 10 {
		Error("获取不到master服务器信息,请检查master服务器配置和日志")
		return ""
	}
	url = Url + url
	request, err := http.NewRequest("POST", url, strings.NewReader(param)) //提交请求;用指定的方法，网址，可选的主体放回一个新的*Request
	request.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	if err != nil {
		Error("http 请求出错 " + url + err.Error())
		return ""
	}
	response, err := client.Do(request) //前面预处理一些参数，状态，Do执行发送；处理返回结果;Do:发送请求,
	if err != nil {
		Error("获取数据失败 " + url + " " + err.Error())
		return ""
	}
	defer response.Body.Close()
	body, err := ioutil.ReadAll(response.Body)
	if err != nil {
		Error("解析数据失败 " + err.Error())
		return ""
	}
	return string(body)
}
