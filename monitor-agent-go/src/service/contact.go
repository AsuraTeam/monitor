package service

import (
	"util/monitorUtil"
	"util/redisUtil"
	"configure"
	"util/commonUtil"
	"encoding/json"
	"strings"
)

/**
 * 获取默认的管理员组
 *
 * @param itemEntity
 * @return
 */
func getAdminGroup(itemEntity MonitorItemEntity) string {
	// 在项目中配置发送给管理员的项目全部发报警给管理员
	adminGroup := ""
	if monitorUtil.CheckString(itemEntity.IsAdmin) && itemEntity.IsAdmin == "1" {
		adminGroup = getAdminGroupData()
	}
	return adminGroup
}

/**
 * @return
 */
func getAdminGroupData() string {
	adminGroup := redisUtil.Get_del("get", configure.CacheIsAdmin)
	if ! monitorUtil.CheckString(adminGroup) {
		adminGroup = ""
	}
	return adminGroup
}

/**
 * @param itemId
 * @param host
 */
func getAlarmGroups(itemId string, host string) string {
	groupsId := redisUtil.Get_del("get", configure.GetCacheHostGroupsKey+host)
	serviceId := redisUtil.Get_del("get", configure.CacheHostServiceId+host)
	list := make([]string, 0)
	list = setAlarmList(list, itemId+"_"+groupsId+"_"+serviceId+"_"+host, true)
	list = setAlarmList(list, itemId+"_"+groupsId+"_"+serviceId, true)
	list = setAlarmList(list, itemId+"_"+groupsId+"_"+host, true)
	list = setAlarmList(list, itemId+"_"+serviceId+"_"+host, true)
	list = setAlarmList(list, "groups_"+groupsId+"_"+serviceId+"_"+host, true)
	list = setAlarmList(list, itemId+"_"+groupsId, true)
	list = setAlarmList(list, itemId+"_"+host, true)
	list = setAlarmList(list, itemId+"_"+serviceId, true)
	list = setAlarmList(list, "groups_"+groupsId+"_"+serviceId, true)
	list = setAlarmList(list, "groups_"+groupsId+"_"+host, true)
	list = setAlarmList(list, "service_"+serviceId+"_"+host, true)
	list = setAlarmList(list, configure.CacheAlarmItem+itemId, false)
	list = setAlarmList(list, configure.CacheAlarmGroups+groupsId, false)
	list = setAlarmList(list, configure.CacheAlarmService+serviceId, false)
	list = setAlarmList(list, configure.CacheAlarmServer+host, false)
	Info("获取到信息" + commonUtil.ToString(list))
	r := ""
	for _, k := range list {
		r = redisUtil.Get_del("get", string(k))
		if monitorUtil.CheckString(r) {
			break
		}
	}

	result := ""
	if monitorUtil.CheckString(r) {
		groupsList := make([]string, 0)
		json.Unmarshal([]byte(r), &groupsList)
		result = strings.Join(groupsList, ",")
	}
	return "," + result

}

/**
 * @param list
 * @param key
 */
func setAlarmList(list []string, key string, defKey bool) []string {
	if defKey {
		key = configure.CacheAlarmItem + key
	}
	list = append(list, key)
	return list
}

/**
 * 获取联系人
 * @return
 */
func getContact(groups string, tp string) string {
	if !monitorUtil.CheckString(groups) {
		return ""
	}
	// 获取正常的联系组
	oldGroups := strings.Split(groups, "\\|")
	g := strings.Split(oldGroups[0], ",")
	contact := ""
	for _, group := range g {
		if len(group) < 1 {
			continue
		}
		result := redisUtil.Get_del("get", configure.CacheContactGroupKey+group)
		if monitorUtil.CheckString(result) {
			entity := MonitorContactGroupEntity{}
			json.Unmarshal([]byte(result), &entity)
			contact += entity.Member + ","
		}
	}

	Info("获取到联系组 " + contact)
	contactSet := make([]string, 0)
	contacts := strings.Split(contact, ",")
	Info(commonUtil.ToString(contacts))
	entity := MonitorContactsEntity{}
	for _, c := range contacts {
		if len(c) < 1 {
			continue
		}
		result := redisUtil.Get_del("get", configure.CacheContactKey+c)
		if monitorUtil.CheckString(result) {
			json.Unmarshal([]byte(result), &entity)
		}
		if entity.ContactsId > 0 {
			contactSet = getContactData(entity, tp, contactSet)
		}
	}
	result := strings.Join(contactSet, ",")
	Info("获取到联系人 " + result)
	if len(result) > 2 {
		return result
	} else {
		return ""
	}
}

/**
  * @param contactsEntity
  * @param type
  * @return
  */
func getContactData(contactsEntity MonitorContactsEntity, tp string, contactSet []string) []string {
	switch tp {
	case "mobile":
		if monitorUtil.CheckString(contactsEntity.Mobile) {
			contactSet = append(contactSet, contactsEntity.Mobile)
		}
		break
	case "email":
		if monitorUtil.CheckString(contactsEntity.Mail) {
			contactSet = append(contactSet, contactsEntity.Mail)
		}
		break
	case "weixin":
		if monitorUtil.CheckString(contactsEntity.Mobile) {
			contactSet = append(contactSet, contactsEntity.Mobile)
		}
		break
	case "ding":
		if monitorUtil.CheckString(contactsEntity.No) {
			contactSet = append(contactSet, contactsEntity.No)
		}
		break
	default:
		break
	}
	return contactSet
}

/**
 * 报警升级
 *
 * @param groups
 * @param alarmCount
 * @param type
 * @return
 */
func getAlarmUpGroups(groups string, alarmCount int64, tp string, oldData string, upList []int) string {
	oldGroups := strings.Split(groups, "\\|")
	groupsData := ""
	if len(oldGroups) > 1 {
		for i := 1; i < len(oldGroups); i++ {
			datas := strings.Split(oldGroups[i], ";")
			Info("获取到报警次数为 " + commonUtil.ToStr64(alarmCount-1) + " 报警升级次数为: " + datas[0])
			datav := commonUtil.ToInt64(datas[0])
			if alarmCount-1 >= datav {
				groupsData = getContact(datas[1], tp)
				upList = append(upList, i)
			}
		}
	}
	set := make([]string, 0)
	data := strings.Split(oldData+","+groupsData, ",")
	for _, g := range data {
		set = append(set, g)
	}
	r := ""
	for _, g := range set {
		r += g + ","
	}
	if len(r) > 1 {
		if r[0:len(r)-1] == "," {
			r = r[0:len(r)-1]
		}
	}
	Info("获取到报警联系人" + r)
	return r
}

// 联系人去重
func getUniqContact(c string)string  {
	cs := strings.Split(c,",")
	data := make([]string,0)
	for _,d := range cs {
		if !commonUtil.CheckString(d){
			continue
		}
		if ! commonUtil.ListExistsString(data, d){
			data = append(data, d)
		}
	}
	return strings.Join(data, ",")
}

/**
  * @return
  */
func getAdminGroupAll() string {
	adminGroup := redisUtil.Get_del("get", configure.CacheIsAdmin)
	if len(adminGroup) < 1 {
		adminGroup = ""
	}
	return adminGroup
}
