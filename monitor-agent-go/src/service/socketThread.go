package service

import (
	"net"
	"util/monitorUtil"
	"math/rand"
	"time"
	"encoding/json"
	"util/commonUtil"
)

func SendData(datas string, server string, port string) bool {
	udpAddr, err := net.ResolveUDPAddr("udp4", server+":"+port)
	if err != nil {
		return false
	}

	conn, err := net.DialUDP("udp", nil, udpAddr)
	if err != nil {
		return false
	}

	_, err = conn.Write([]byte(datas))
	if err != nil {
		return false
	}
	conn.Close()
	return true
}

/**
   * @param data
   *
   * @return
   */
func sendDataSocket(data string) bool {
	server := GetServer("")
	if !monitorUtil.CheckString(server) {
		Error("获取push服务失败..")
		return false
	}
	port := ports[rand.Intn(299)]
	c1 := make(chan bool, 1)
	go func() {
		c1 <- SendData(data, server, port)
	}()

	select {
	case res := <-c1:
		close(c1)
		return res
	case <- time.After(time.Second * 3):
		Error("udp线程超时")
		close(c1)
	}
	return false
}
func SendSplitData(data string) {

	if len(data) > 65534 {
		split := len(data) / 50000 + 2
		newList := make([]PushEntity, 0)
		list := getPushEntity(data)
		counter := 0
		for i := 0; i < len(list); i++ {
		if i % (len(list) / split) == 0 {
			sendDataSocket(commonUtil.ToString(newList))
			counter = i
		newList = make([]PushEntity, 0)
		}else{
			newList = append(newList, list[i])
		}
		}
		if counter < len(list) {
			newList = make([]PushEntity, 0)
			for i:=counter ;i <len(list);i++ {
				newList = append(newList, list[i])
			}
			sendDataSocket(commonUtil.ToString(newList))
		}
	} else {
		sendDataSocket(data)
	}
}

/**
  *
  * @param lentity
  * @return
  */
func getPushEntity(lentity string)[]PushEntity{
	entitys := make([]PushEntity, 0)
	json.Unmarshal([]byte(lentity), &entitys)
    return entitys
}