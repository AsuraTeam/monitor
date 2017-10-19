package com.asura.agent.util;

import com.google.gson.Gson;
import com.asura.agent.conf.MonitorCacheConfig;
import com.asura.agent.configure.Configure;
import com.asura.agent.entity.MonitorConfigureEntity;
import com.asura.agent.entity.MonitorItemEntity;
import com.asura.agent.entity.MonitorMessagesEntity;
import com.asura.agent.entity.MonitorScriptsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class MonitorUtil {

    private static final Logger logger = LoggerFactory.getLogger(MonitorUtil.class);
    public static final RedisUtil REDIS_UTIL = new RedisUtil();
    public static final Gson GSON = new Gson();
    public static final String SEPARATOR = System.getProperty("file.separator");
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + SEPARATOR + "monitor" + SEPARATOR;

    /**
     * 打印日志
     *
     * @param messages
     */
    public static void info(String messages) {
        if (null == messages) {
            return;
        }
        String string = "true";
        if (string.equals(Configure.get("DEBUG"))) {
            logger.info(messages);
        }
    }

    /**
     * 检查缓存文件，并获取内容
     *
     * @param name
     * @param interval
     * @return
     */
    public static boolean checkCacheFileTime(String name, long interval) {
        String fileName = TEMP_DIR + SEPARATOR + name;
        File file = new File(fileName);
        long t = 1000;
        if (file.exists()) {
            if (System.currentTimeMillis() / t - file.lastModified() / t > interval) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


    /**
     * @param groupsId
     * @return
     */
    public static HashSet<String> getGroupsHosts(String groupsId) {
        String fileName = TEMP_DIR + SEPARATOR + "cache_groups_hosts_" + groupsId;
        if (checkCacheFileTime("cache_groups_hosts_" + groupsId, 1800)) {
            String result = REDIS_UTIL.get(MonitorCacheConfig.cacheGroupsHosts + groupsId);
            FileIoUtil.writeFile(fileName, result, false);
        }
        String result = FileIoUtil.readFile(fileName);
        if (result != null && result.length() > 0) {
            return GSON.fromJson(result, HashSet.class);
        }
        return new HashSet<>();
    }


    /**
     * @param hosts
     * @return
     */
    public static List<String> getHostStatus(List<String> hosts, boolean isAlarm) {
        String[] strings = new String[hosts.size()];
        int count = 0;
        String key = "";
        for (String host : hosts) {
            if (isAlarm) {
                key = RedisUtil.app + "_" + MonitorCacheConfig.cacheAgentAlarmNumber + host;
            } else {
                key = RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIsUpdate + host;
            }
            strings[count] = key;
            count += 1;
        }
        if (strings.length > 0) {
            Jedis jedis = REDIS_UTIL.getJedis();
            List<String> result = jedis.mget(strings);
            return result;
        }
        return new ArrayList<>();
    }

    /**
     * @param groupsId
     * @return
     */
    public static List<String> getHosts(String groupsId) {
        HashSet<String> hosts = getGroupsHosts(groupsId);
        List<String> hostList = new ArrayList<String>();
        for (String host : hosts) {
            hostList.add(host);
        }
        return hostList;
    }

    /**
     * 获取组信息
     *
     * @return
     */
    public static Map<String, String> getGroups() {
        String groups = "";
        String fileName = TEMP_DIR + SEPARATOR + "cache_groups_name";
        if (checkCacheFileTime("cache_groups_name", 1800)) {
            groups = REDIS_UTIL.get(MonitorCacheConfig.cacheGroupName);
            FileIoUtil.writeFile(fileName, groups, false);
        }
        groups = FileIoUtil.readFile(fileName);
        logger.info("获取到grous " + groups);
        Map<String, String> groupsMap = new HashMap<>();
        if (groups != null) {
            groupsMap = GSON.fromJson(groups, HashMap.class);
        }
        return groupsMap;
    }

    /**
     * @return
     */
    public static String getAdminGroup() {
        String adminGroup = REDIS_UTIL.get(MonitorCacheConfig.cacheIsAdmin);
        if (adminGroup == null) {
            adminGroup = "";
        }
        return adminGroup;
    }

    /**
     * 获取默认的管理员组
     *
     * @param itemEntity
     * @return
     */
    public static String getAdminGroup(MonitorItemEntity itemEntity) {
        // 在项目中配置发送给管理员的项目全部发报警给管理员
        String adminGroup;
        if (itemEntity.getIsAdmin() != null && itemEntity.getIsAdmin().length() > 0 && Integer.valueOf(itemEntity.getIsAdmin()) == 1) {
            adminGroup = getAdminGroup();
        } else {
            adminGroup = "";
        }
        return adminGroup;
    }

    /**
     * 获取脚本内容
     * 2017-02-03
     */
    public static MonitorScriptsEntity getScripts(String scriptsId) {
        for (int i = 0; i < 5; i++) {
            String script = HttpSendUtil.sendPost("monitor/scripts/api/scripts", "scriptsId=" + scriptsId);
            if (script != null && script.length() > 1) {
                MonitorScriptsEntity scriptsEntity = GSON.fromJson(script, MonitorScriptsEntity.class);
                return scriptsEntity;
            }
            info("获取脚本重试" + i);
        }
        return new MonitorScriptsEntity();
    }

    /**
     * 设置默认配置
     *
     * @return
     */
    public static MonitorConfigureEntity getMonitorConfig() {
        info("获取默认配置");
        MonitorConfigureEntity configureEntity = new MonitorConfigureEntity();
        configureEntity.setConfigureId(0);
        configureEntity.setMonitorConfigureTp("template");
        configureEntity.setTemplateId("0");
        configureEntity.setIsValid(1);
        return configureEntity;
    }


    /**
     * @param faildData
     * @param okData
     * @param warningData
     * @param unknownData
     * @param type
     * @param ids
     */
    public static Map getGroupData(Map<String, String> faildData, Map<String, String> okData, Map<String, String> warningData, Map<String, String> unknownData, String type, ArrayList<String> ids) {
        Map map = new HashMap();
        String currTime = DateUtil.getCurrTime() + "";
        for (String id : ids) {
            switch (type) {
                case "ok":
                    okData.put(id, currTime);
                    faildData.remove(id);
                    unknownData.remove(id);
                    warningData.remove(id);
                    break;
                case "faild":
                    faildData.put(id, currTime);
                    unknownData.remove(id);
                    warningData.remove(id);
                    okData.remove(id);
                    break;
                case "warning":
                    warningData.put(id, currTime);
                    faildData.remove(id);
                    unknownData.remove(id);
                    okData.remove(id);
                    break;
                case "unknown":
                    unknownData.put(id, currTime);
                    faildData.remove(id);
                    warningData.remove(id);
                    okData.remove(id);
                    break;
                default:
                        break;
            }
        }
        map.put("ok", okData);
        map.put("faild", faildData);
        map.put("warning", warningData);
        map.put("unknown", unknownData);
        return map;
    }

    /**
     * 直接发送报警消息
     *
     * @param messages
     */
    public static void sendPostMessages(String messages, String url) {
        MonitorMessagesEntity entity = GSON.fromJson(messages, MonitorMessagesEntity.class);
        String mess = "alarmCount=" + entity.getAlarmCount() +
                "&serverId=" + entity.getServerId() +
                "&groupsId=" + entity.getGroupsId() +
                "&scriptsId=" + entity.getScriptsId() +
                "&severtityId=" + entity.getSevertityId() + "" +
                "&messages=" + entity.getMessages() +
                "&messagesTime=" + entity.getMessagesTime();
        if (entity.getMobile() != null) {
            mess += "&mobile=" + entity.getMobile();
        }
        if (entity.getEmail() != null) {
            mess += "&email=" + entity.getEmail();
        }
        if (entity.getDing() != null) {
            mess += "&ding=" + entity.getDing();
        }
        if (entity.getWeixin() != null) {
            mess += "&weixin=" + entity.getWeixin();
        }
        logger.info(mess);
        logger.info(HttpSendUtil.sendPost(url, mess));
    }

    /**
     * @param server
     * @return
     */
    public static String getIpHostName(String server) {
        String serverId;
        serverId = REDIS_UTIL.get(MonitorCacheConfig.hostsIdKey + server);
        if (serverId == null) {
            return "";
        }
        String portData = REDIS_UTIL.get(MonitorCacheConfig.cacheAgentServerInfo + serverId);
        if (portData != null && portData.length() > 10) {
            Map<String, String> map = GSON.fromJson(portData, HashMap.class);
            if (map != null) {
                try {
                    return map.get("hostname");
                } catch (Exception e) {
                    logger.info("获取主机名失败: " + server);
                }
            }
        }
        return "";
    }


    /**
     * 获取有效的监控主机
     */
    public static HashSet getIsValidHosts() {
        // 获取自己是否有监控项目
        String allHosts = REDIS_UTIL.get(MonitorCacheConfig.cacheAllHostIsValid);
        info("get is configure host " + allHosts);
        HashSet hosts = GSON.fromJson(allHosts, HashSet.class);
        return hosts;
    }

    /**
     * 设置文件可执行权限
     *
     * @param filePath
     */
    public static void setFileExec(String filePath) {
        if (SEPARATOR.equals("/")) {
            File file = new File(filePath);
            if (file.exists()) {
                file.setExecutable(true);
            }
        }
    }

    /**
     * 检查字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean checkString(String str) {
        if (null != str && str.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @param list
     * @param key
     */
    static void setAlarmList(ArrayList list, String key, boolean defKey) {
        if (defKey) {
            key = MonitorCacheConfig.cacheAlarmItem + key;
        }
        list.add(key);
    }

    /**
     * @param itemId
     * @param host
     */
    public static String getAlarmGroups(String itemId, String host) {
        String groupsId = REDIS_UTIL.get(MonitorCacheConfig.getCacheHostGroupsKey + host);
        String serviceId = REDIS_UTIL.get(MonitorCacheConfig.cacheHostServiceId+ host);
        ArrayList<String> list = new ArrayList();
        setAlarmList(list, itemId + "_" + groupsId + "_" + serviceId + "_" + host, true);
        setAlarmList(list, itemId + "_" + groupsId + "_" + serviceId, true);
        setAlarmList(list, itemId + "_" + groupsId + "_" + host, true);
        setAlarmList(list, itemId + "_" + serviceId + "_" + host, true);
        setAlarmList(list, "groups_" + groupsId + "_" + serviceId + "_" + host, true);
        setAlarmList(list, itemId + "_" + groupsId, true);
        setAlarmList(list, itemId + "_" + host, true);
        setAlarmList(list, itemId + "_" + serviceId, true);
        setAlarmList(list, "groups_" + groupsId + "_" + serviceId, true);
        setAlarmList(list, "groups_" + groupsId + "_" + host, true);
        setAlarmList(list, "service_" + serviceId + "_" + host, true);
        setAlarmList(list, MonitorCacheConfig.cacheAlarmItem + itemId, false);
        setAlarmList(list, MonitorCacheConfig.cacheAlarmGroups + groupsId, false);
        setAlarmList(list, MonitorCacheConfig.cacheAlarmService + serviceId, false);
        setAlarmList(list, MonitorCacheConfig.cacheAlarmServer + host, false);
        String r = "";
        info("获取到信息" + GSON.toJson(list));
        for (String k:list){
            r = REDIS_UTIL.get(k);
            if (r != null && r.length() > 0) {
                break;
            }
        }
        try {
            String result = "";
            if (r != null && r.length() > 0) {
                ArrayList<String> groupList = GSON.fromJson(r, ArrayList.class);
                for (String g : groupList) {
                    result += g + ",";
                }
            }
            return "," + result;
        }catch (Exception e){
            return "";
        }
    }

}
