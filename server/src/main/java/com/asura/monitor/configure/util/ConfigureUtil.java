package com.asura.monitor.configure.util;

import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.configure.entity.MonitorConfigureEntity;
import com.asura.monitor.configure.entity.MonitorGroupsEntity;
import com.asura.monitor.configure.entity.MonitorItemEntity;
import com.asura.monitor.configure.service.MonitorConfigureService;
import com.asura.monitor.configure.service.MonitorGroupsService;
import com.asura.monitor.configure.service.MonitorItemService;
import com.asura.util.CheckUtil;
import com.asura.util.HttpUtil;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheConfigureHostsListKey;
import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheHostCnfigureKey;

/**
 * <p></p>
 *
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
@Controller
@ComponentScan
public class ConfigureUtil {

    private final Logger logger = LoggerFactory.getLogger(ConfigureUtil.class);
    private static final RedisUtil REDIS_UTIL = new RedisUtil();
    private static final Gson GSON = new Gson();

    /**
     * @param hosts
     * @param hostSet
     *
     * @return
     */
    public HashSet<String> getHosts(String[] hosts, HashSet hostSet) {
        for (String h : hosts) {
            hostSet.add(h);
        }
        return hostSet;
    }

    /**
     * @param configureId
     * @param hosts
     */
    public void deleteOldConfigure(String configureId, String[] hosts) {
        for (String host : hosts) {
            REDIS_UTIL.del(cacheHostCnfigureKey + host + "_" + configureId);
        }
    }

    /**
     * 初始化监控信息
     *
     * @param hostId
     */
    public void initMonitor(String hostId) {
        String server = "";
        String url = "";
        String portData = REDIS_UTIL.get(MonitorCacheConfig.cacheAgentServerInfo + hostId);
        if (portData.length() > 5) {
            Map serverMap = GSON.fromJson(portData, HashMap.class);
            server = REDIS_UTIL.get(MonitorCacheConfig.cacheHostIdToIp + hostId);
            url = "http://" + server + ":" + serverMap.get("port") + "/monitor/init";
            HttpUtil.sendGet(url);
        }
    }

    /**
     * 设置组和host的缓存
     *
     * @param hashSet
     * @param newHashSet
     * @param entity
     * @param arrayList
     *
     * @return
     */
    public HashSet setHostGroupData(HashSet<String> hashSet, HashSet<String> newHashSet, MonitorConfigureEntity entity, ArrayList arrayList) {
        if (hashSet != null) {
            for (String m : hashSet) {
                if (entity.getIsValid() == 1) {
                    newHashSet.add(m);
                } else {
                    if (!arrayList.contains(m)) {
                        newHashSet.add(m);
                    }
                }
            }
        }
        return newHashSet;
    }

    /**
     * @param type
     */
    public void updateHostUpdate(String type) {
        String result = REDIS_UTIL.get(MonitorCacheConfig.cacheConfigureHostsListKey);
        // 将脚本更新通知到agent
        Jedis jedis = REDIS_UTIL.getJedis();
        if (result != null) {
            ArrayList<String> arrayList = GSON.fromJson(result, ArrayList.class);
            for (String h : arrayList) {
                jedis.lpush(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostUpdateQueue + h, type);
            }
        }
        jedis.close();
    }

    /**
     * 获取相关配置的更新
     * 获取主机
     * 然后每个主机更新自己的数据
     *
     * @param entity
     */
    public void setUpdateMonitor(MonitorConfigureEntity entity) {
        Jedis jedis = REDIS_UTIL.getJedis();
        HashSet<String> hostSet = new HashSet<>();
        String host = entity.getHosts();
        String group = entity.getGname();
        if (host != null) {
            String[] hosts = host.split(",");
            if (entity.getIsValid() == 1) {
                hostSet = getHosts(hosts, hostSet);
            }
        }
        // 把组里的主机都找到
        if (CheckUtil.checkString(group)) {
                String result = REDIS_UTIL.get(MonitorCacheConfig.cacheGroupsKey + group);
                if (CheckUtil.checkString(result)) {
                    MonitorGroupsEntity groupsEntity = GSON.fromJson(result, MonitorGroupsEntity.class);
                    String[] hosts = groupsEntity.getHosts().split(",");
                    hostSet = getHosts(hosts, hostSet);
                }
        }
        logger.info("获取到配置文件"+  entity.getConfigureId() + GSON.toJson(hostSet));
        for (String h : hostSet) {
            jedis.lpush(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostUpdateQueue + h, "configure");
        }
        jedis.close();
    }

    /**
     * 生成每个主机和组的监控配置
     *
     * @param entity
     */
    public HashSet makeHostMonitorTag(MonitorConfigureEntity entity, HashSet hostSet) {
        String cacheData = entity.getConfigureId() + "";
        if (entity.getHostId() != null || entity.getGname() != null) {
            // 监控的组
            String groups = entity.getGname();
            String[] hosts;
            if (CheckUtil.checkString(groups)){
                try {
                    hosts = REDIS_UTIL.get(MonitorCacheConfig.cacheGroupsKey.concat(groups)).split(",");
                    logger.info("获取到组的成员信息" + GSON.toJson(hosts));
                }catch (Exception e){
                    hosts = "".split(",");
                }
            }else {
                // 监控的主机
                hosts = entity.getHosts().split(",");
                logger.info("获取到监控成员信息" + GSON.toJson(hosts));
            }
            // 从redis获取监控的组和host
//            HashSet<String> hostSet = new HashSet<>();

            ArrayList<String> arrayList = new ArrayList<>();
            if (entity.getIsValid() == 1) {
                for (String c : hosts) {
                    arrayList.add(c);
                }
            }

            for (int i = 0; i < hosts.length; i++) {
                String key = cacheHostCnfigureKey + hosts[i] + "_" + entity.getConfigureId();
                if (entity.getIsValid() == 1) {
                    // 缓存host的配置信息
                    REDIS_UTIL.set(key, GSON.toJson(cacheData));
                    hostSet.add(hosts[i].replace("\\n", ""));
                } else {
                    REDIS_UTIL.del(key);
                }
            }
//            HashSet<String> cacheHosts = GSON.fromJson(REDIS_UTIL.get(cacheConfigureHostsListKey), HashSet.class);
//            hostSet = setHostGroupData(cacheHosts, hostSet, entity, arrayList);
//            REDIS_UTIL.set(cacheConfigureHostsListKey, GSON.toJson(hostSet));
        }
        return hostSet;
    }

    /**
     * 获取使用某个item的配置的hosts
     * @param configureService
     * @param itemId
     */
     ArrayList getItemUsedHosts(MonitorConfigureService configureService, int itemId,   ArrayList<String> hostList){
        SearchMap searchMap = new SearchMap();
        searchMap.put("itemId", itemId);
        String hosts;
        String gname;
        String[] ghosts = null;
        List<MonitorConfigureEntity> result = configureService.getDataList(searchMap, "selectItemHosts");
        for (MonitorConfigureEntity entity: result){
            hosts = entity.getHosts();
            if (hosts != null){
                ghosts =  hosts.split(",");
            }
            if(CheckUtil.checkString(entity.getGname())){
                gname = entity.getGname();
                ghosts = REDIS_UTIL.get(MonitorCacheConfig.cacheGroupsKey.concat(gname)).split(",");
            }
            if (ghosts == null){
                continue;
            }
            for (String host: ghosts){
                if (host.length() > 0 && ! hostList.contains(host)) {
                    hostList.add(host);
                }
            }
        }
        return hostList;
    }

    /**
     *
     * @param itemService
     * @param scriptsId
     * @return
     */
     ArrayList getScriptsItem(MonitorItemService itemService,  int scriptsId, CacheController cacheController){
        SearchMap searchMap = new SearchMap();
        searchMap.put("scriptsId", scriptsId);
        ArrayList<Integer> itemList = new ArrayList();
        List<MonitorItemEntity> result = itemService.getDataList(searchMap, "selectItemScripts");
        for (MonitorItemEntity entity: result) {
             if (entity.getScriptId() != null && ! itemList.contains(entity.getItemId())){
                 itemList.add(entity.getItemId());
             }
            // 默认监控处理
            if (entity.getIsDefault() != null && entity.getIsDefault().equals("1")){
                cacheController.setDefaultMonitorChange();
                return new ArrayList();
            }
        }
        return itemList;
    }

    /**
     * 设置所有监控配置的机器
     * @param itemService
     * @param configureService
     * @param scriptsId
     */
    public void setHostMonitorQueue(MonitorItemService itemService, MonitorConfigureService configureService, int scriptsId,  CacheController cacheController){
        ArrayList<Integer> items = getScriptsItem(itemService, scriptsId, cacheController);
        ArrayList<String> hostsList = new ArrayList();
        for (Integer itemId: items){
            getItemUsedHosts(configureService, itemId, hostsList);
        }
        Jedis jedis = REDIS_UTIL.getJedis();
        StringBuilder keySb = new StringBuilder();
        keySb.append(RedisUtil.app.concat("_"))
                .append(MonitorCacheConfig.cacheDefaultChangeQueue);
        String key = keySb.toString();
        String redisKey;
        for (String host: hostsList){
            redisKey = key.concat(host);
            jedis.del(redisKey);
            jedis.lpush(redisKey, "1");
        }
        jedis.close();
    }

    /**
     *
     * @param hosts
     * @return
     */
   public ArrayList stringToArrayList(String hosts){
        ArrayList arrayList = new ArrayList();
        for (String host: hosts.split(",")){
            arrayList.add(host);
        }
        return arrayList;
    }

    /**
     * 获取某个组拥有的配置信息
     * 2017-06-05
     * @param groupsId
     * @return
     */
    public List<MonitorConfigureEntity>  getConfigure(int groupsId, MonitorConfigureService configureService){
        SearchMap searchMap = new SearchMap();
        searchMap.put("gname", groupsId);
        List<MonitorConfigureEntity> conf = configureService.getDataList(searchMap, "selectByAll");
        return conf;
    }

    /**
     *
     * @param hosts
     * @param groupsId
     */
    public  void deleteGroupMonitor(String hosts, int groupsId, boolean isDel, MonitorGroupsEntity oldEntity, MonitorConfigureService configureService){
        // 将删除的host的监控删除掉
        ArrayList<String > old = stringToArrayList(oldEntity.getHosts());
        ArrayList<String > newHosts = stringToArrayList(hosts);
        ArrayList<String> delList = new ArrayList();

        for (String oldHost: old){
            if (!newHosts.contains(old)){
                delList.add(oldHost);
            }
        }

        if (isDel){
            for (String oldHost: old){
                delList.add(oldHost);
            }
        }

        // 如果有删除的机器，就删除监控
        List<MonitorConfigureEntity> conf = getConfigure(groupsId, configureService);
        for (MonitorConfigureEntity entity: conf){
            for (String del: delList){
                HashSet newHash = new HashSet();
                String configs = REDIS_UTIL.get(MonitorCacheConfig.cacheHostConfigKey.concat(del));
                if (CheckUtil.checkString(configs)){
                    HashSet<String> configsSet = GSON.fromJson(configs, HashSet.class);
                    for (String cs: configsSet){
                        if (!cs.equals(entity.getConfigureId())){
                            newHash.add(entity.getConfigureId());
                        }
                    }
                }
                // 将某个监控项从主机的监控数据删除掉
                REDIS_UTIL.set(MonitorCacheConfig.cacheHostConfigKey.concat(del), GSON.toJson(newHash));
            }
        }

        // 发布删除通知
        if (isDel) {
            for (String oldHost: old) {
                REDIS_UTIL.lpush(MonitorCacheConfig.cacheHostUpdateQueue.concat(oldHost), "configure");
            }
        }
    }

}
