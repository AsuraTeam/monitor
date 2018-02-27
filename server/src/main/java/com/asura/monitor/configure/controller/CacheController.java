package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.*;
import com.asura.monitor.configure.service.*;
import com.asura.monitor.configure.util.ConfigureUtil;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.resource.controller.FloorController;
import com.asura.resource.entity.*;
import com.asura.resource.service.*;
import com.asura.util.CheckUtil;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheConfigureHostsListKey;

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
@RequestMapping("/monitor/configure/")
public class CacheController {

    private final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private MonitorItemService itemService;

    @Autowired
    private MonitorConfigureService configureService;

    @Autowired
    private MonitorGroupsService groupsService;

    @Autowired
    private MonitorScriptsService scriptsService;

    @Autowired
    private MonitorContactGroupService contactGroupService;

    @Autowired
    private MonitorContactsService contactsService;

    @Autowired
    private MonitorTemplateService templateService;

    @Autowired
    private CmdbResourceServerService service;

    @Autowired
    private CmdbResourceGroupsService cmdbResourceGroupsService ;

    @Autowired
    private MonitorMessageChannelService channelService;

    @Autowired
    private IndexController logSave;

    @Autowired
    private CmdbResourceFloorService floorService;

    @Autowired
    private CmdbResourceEntnameService entnameService;

    @Autowired
    private CmdbResourceCabinetService cabinetService;

    @Autowired
    private CmdbResourceUserService userService;

    @Autowired
    private CmdbResourceGroupsService resourceGroupsService;

    @Autowired
    private MonitorAlarmConfigureService alarmConfigureService;

    private final Gson gson = new Gson();
    private final RedisUtil redisUtil = new RedisUtil();

    final PageBounds pageBounds = PageResponse.getPageBounds(10000,1);

    /**
     * 将item的数据写入到cache
     *
     * @return
     */
    @RequestMapping("item/setCache")
    @ResponseBody
    public String setItemCache(MonitorItemService monitorItemService, PagingResult<MonitorItemEntity> result) {
        SearchMap searchMap = new SearchMap();
        ArrayList<String> items = new ArrayList();
        if (null == result) {
            try {
                result = monitorItemService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            } catch (Exception e) {
                result = itemService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            }
        }
        for (MonitorItemEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheItemKey + m.getItemId(), gson.toJson(m));
            if (m.getIsDefault() != null && m.getIsDefault().equals("1")){
                items.add(m.getItemId()+"");
            }
        }
        redisUtil.set(MonitorCacheConfig.cacheIsDefault, gson.toJson(items));
        return "ok";
    }

    /**
     *
     * 保存消息通道数据
     * @return
     */
    @RequestMapping("messages/setCache")
    @ResponseBody
    public String setMessagesCache(MonitorMessageChannelService monitorMessageChannelService) {
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorMessageChannelEntity> result;
        try {
           result = monitorMessageChannelService.findAll(searchMap, PageResponse.getPageBounds(10, 1), "selectByAll");
        }catch (Exception e){
            result = channelService.findAll(searchMap, PageResponse.getPageBounds(10, 1), "selectByAll");
        }
        for (MonitorMessageChannelEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheChannelKey + m.getChannelTp(), gson.toJson(m));
        }
        return "ok";
    }

    /**
     * 监控配置生成缓存
     * @return
     */
    @RequestMapping("configure/setCache")
    @ResponseBody
    public String setConfigureCache(PagingResult<MonitorConfigureEntity> result) {
        SearchMap searchMap = new SearchMap();
        HashSet hostSet = new HashSet();
        if (null == result) {
            result = configureService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        }
        if (null == result || null == result.getRows() ){
            return "";
        }
        for (MonitorConfigureEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheConfigureKey + m.getConfigureId(), gson.toJson(m));
            try {
                ConfigureUtil configureUtil = new ConfigureUtil();
                hostSet = configureUtil.makeHostMonitorTag(m, hostSet);
                configureUtil.setUpdateMonitor(m);
            }catch (Exception e){
                logger.error("setConfigureCache ERROR", e);
            }
        }
        ArrayList arrayList = new ArrayList();
        ConfigureUtil configureUtil = new ConfigureUtil();
        MonitorConfigureEntity entity = new MonitorConfigureEntity();
        entity.setIsValid(1);
        HashSet<String> cacheHosts = gson.fromJson(redisUtil.get(cacheConfigureHostsListKey), HashSet.class);
        hostSet = configureUtil.setHostGroupData(cacheHosts, hostSet, entity, arrayList);
        redisUtil.set(cacheConfigureHostsListKey, gson.toJson(hostSet));
        return "ok";
    }

    /**
     * 将脚本的数据写入到cache
     *
     * @return
     */
    @RequestMapping("script/setCache")
    @ResponseBody
    public String setScriptCache(MonitorScriptsService monitorScriptsService, PagingResult<MonitorScriptsEntity> result) {
        if (null == result) {
            SearchMap searchMap = new SearchMap();
            try {
                result = monitorScriptsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            } catch (Exception e) {
                result = scriptsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            }
        }
        for (MonitorScriptsEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheScriptKey + m.getScriptsId(), gson.toJson(m));
        }
        return "ok";
    }


    /**
     * 将联系组的数据写入到cache
     *
     * @return
     */
    @RequestMapping("contactGroup/setCache")
    @ResponseBody
    public String setContactGroupCache(MonitorContactGroupService monitorContactGroupService,    PagingResult<MonitorContactGroupEntity> result) {
        SearchMap searchMap = new SearchMap();
        String groups = "";
        if (null == result) {
            try {
                result = monitorContactGroupService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            } catch (Exception e) {
                result = contactGroupService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            }
        }
        for (MonitorContactGroupEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheContactGroupKey + m.getGroupId(), gson.toJson(m));
            if(m.getIsAdmin()==1){
                groups += m.getGroupId()+",";
            }
        }
        redisUtil.set(MonitorCacheConfig.cacheIsAdmin, groups);
        return "ok";
    }

    /**
     * 将联系组的数据写入到cache
     *
     * @return
     */
    @RequestMapping("contacts/setCache")
    @ResponseBody
    public String setContactCache(PagingResult<MonitorContactsEntity> result ) {
        try {
            SearchMap searchMap = new SearchMap();
            if (null == result){
                result = contactsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            }
            for (MonitorContactsEntity m : result.getRows()) {
                redisUtil.set(MonitorCacheConfig.cacheContactKey + m.getContactsId(), gson.toJson(m));
            }
        }catch (Exception e){
        }
        return "ok";
    }

    /**
     * 生成主机的id,agent方便从ip地址获取到自己的id
     * 20170617 添加直接数据支持
     */
    @RequestMapping("configure/setServerCache")
    @ResponseBody
    public String setServerCache(CmdbResourceServerService resourceServerService, List<CmdbResourceServerEntity> result) {
        if ( null == result) {
            try {
                result = resourceServerService.getDataList(null, "selectAllIp");
            } catch (Exception e) {
                result = service.getDataList(null, "selectAllIp");
            }
        }
        redisUtil.setHostId(result);
        return "ok";
    }


    /**
     * 将模板的数据写入到cache
     * @return
     */
    @RequestMapping("server/cacheServerInfo")
    @ResponseBody
    public String cacheServerInfo(String ip) {
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(ip)){
            searchMap.put("ipAddress", ip);
        }
        PagingResult<CmdbResourceServerEntity> result = service.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        for (CmdbResourceServerEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheIpHostInfo + m.getIpAddress(), gson.toJson(m));
        }
        return "ok";
    }

    /**
     * 将模板的数据写入到cache
     *
     * @return
     */
    @RequestMapping("template/setCache")
    @ResponseBody
    public String setTemplateCache(PagingResult<MonitorTemplateEntity> result) {
        if (null == result) {
            SearchMap searchMap = new SearchMap();
            result = templateService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        }
        for (MonitorTemplateEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheTemplateKey + m.getTemplateId(), gson.toJson(m));
        }
        return "ok";
    }

    /**
     * 获取组机组的IP地址
     * @param m
     * @return
     */
    public String getHosts(MonitorGroupsEntity m) {
        String hosts = "";
        SearchMap searchMap = new SearchMap();
        searchMap.put("hosts", m.getHosts().split(","));
        searchMap.put("groupsName", m.getGname());
        PagingResult<CmdbResourceServerEntity> data = service.findAll(searchMap, PageResponse.getPageBounds(1000, 1), "selectByAll");
        for (CmdbResourceServerEntity c : data.getRows()) {
            hosts += c.getIpAddress() + ",";
        }
        return hosts;
    }

    /**
     *
     * 监控组数据
     * @return
     */
    @RequestMapping("groups/setCache")
    @ResponseBody
    public String setGroupsCache(PagingResult<MonitorGroupsEntity> result, Map<String,String> map) {
        HashSet hostIdArr ;
        Map<String,HashSet> hostMap = new HashMap<>();
        SearchMap searchMap = new SearchMap();
        logger.info("result == null" + new Gson().toJson(result));

        if (null == result) {
            logger.info("result == null");
            result = groupsService.findAll(searchMap, PageResponse.getPageBounds(100000, 1), "selectByAll");
        }
        String hosts;
        for (MonitorGroupsEntity m : result.getRows()) {
            if (null == map) {
                 hosts = getHosts(m);
            }
            else {
                hosts = map.get(m.getGroupsId());
            }
            m.setIpList(hosts);
            String[] hostIds = m.getHosts().split(",");
            for(String host:hostIds){
                if(!hostMap.containsKey(host)){
                    hostIdArr = new HashSet();
                }else {
                    hostIdArr = hostMap.get(host);
                }
                hostIdArr.add(m.getGroupsId());
                hostMap.put(host,hostIdArr);
            }
            redisUtil.set(MonitorCacheConfig.cacheGroupsKey + m.getGroupsId(), gson.toJson(m));
        }

        // 将单个主机的所有组放入到redis
        for (Map.Entry<String, HashSet> entry : hostMap.entrySet()) {
            redisUtil.set(MonitorCacheConfig.cacheHostGroupsKey + entry.getKey(), gson.toJson(entry.getValue()));
        }
        return "ok";
    }

    /**
     * 处理每个组和host拥有的配置文件
     * @param hostMap
     * @param configId
     * @param hostId
     * @return
     */
    Map<String, HashSet>  setGroupHostConfig(Map<String, HashSet> hostMap, String configId, String hostId){
        HashSet conf = new HashSet();
        // 将每个host拥有的配置写入到redis
        if(hostMap.containsKey(hostId)){
            conf = hostMap.get(hostId);
        }
        conf.add(configId);
        hostMap.put(hostId , conf);
        return hostMap;
    }

    /**
     * 生成一个存放所有host id的key, agent 可以从这里获取是否有自己对于的id，没有的话停止监控
     */
    @RequestMapping("configure/makeAllHostKey")
    @ResponseBody
    public String makeAllHostKey(MonitorConfigureService monitorConfigureService, PagingResult<MonitorConfigureEntity> result){
        Jedis jedis = redisUtil.getJedis();
        SearchMap searchMap = new SearchMap();
        searchMap.put("isValid",1);
        HashSet<String> hostIds = new HashSet();
        HashSet<String> conf;
        // 存放每个主机拥有的所有配置文件id
        Map<String, HashSet> hostMap = new HashMap();
        // 存放每个组拥有的所有配置文件的ID

        if (null == result && monitorConfigureService == null) {
            if (monitorConfigureService != null){
                result = monitorConfigureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
            } else{
                result = configureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
            }
        }else{
            result = configureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
        }


        for (MonitorConfigureEntity m : result.getRows()) {
            String[] hosts = null;
            if(CheckUtil.checkString(m.getHosts()) && m.getMonitorHostsTp().equals("host")) {
                hosts = m.getHosts().split(",");
            }
            // 设置组的信息
            if(CheckUtil.checkString(m.getGname()) && m.getMonitorHostsTp().equals("groups")){
                String hostData = redisUtil.get(MonitorCacheConfig.cacheGroupsKey.concat(m.getGname()));
                if (CheckUtil.checkString(hostData)) {
                    MonitorGroupsEntity entity = gson.fromJson(hostData, MonitorGroupsEntity.class);
                    hosts = entity.getHosts().split(",");
                    logger.info("获取到组配置信息 " + gson.toJson(hostIds));
                }
            }
            if (null == hosts){
                continue;
            }

            for (String s : hosts) {
                if(s.length() < 1){continue;}
                hostIds.add(s);
                conf = new HashSet();
                // 将每个host拥有的配置写入到redis
                if(hostMap.containsKey(s)){
                    conf = hostMap.get(s);
                }
                conf.add(String.valueOf(m.getConfigureId()));
                hostMap.put(s , conf);
            }
        }
        for (Map.Entry<String, HashSet> entry : hostMap.entrySet()) {
            try {
                String data =  gson.toJson(entry.getValue());
                logger.info("保存主机信息"+entry.getKey() + data);
                jedis.set(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostConfigKey + entry.getKey(),data);
            }catch (Exception e){
                jedis = redisUtil.getJedis();
                jedis.set(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostConfigKey + entry.getKey(), gson.toJson(entry.getValue()));
            }
        }
        String allHost = gson.toJson(hostIds);
        redisUtil.set(MonitorCacheConfig.cacheAllHostIsValid, allHost);
        return "ok";
    }

    /**
     * 生成业务线数据缓存
     * 只存储id,和业务线名称
     */
    @RequestMapping("cache/groups")
    @ResponseBody
    public String cacheGroups(CmdbResourceGroupsService resourceGroupsService, CmdbResourceServerService resourceServerService){
        Map map = new HashMap();
        PagingResult<CmdbResourceGroupsEntity> result;
        try {
            result = resourceGroupsService.findAll(null, PageResponse.getPageBounds(10000000, 1));
        }catch (Exception e){
            result = cmdbResourceGroupsService.findAll(null, PageResponse.getPageBounds(10000000, 1));
        }
        SearchMap searchMap;

        for (CmdbResourceGroupsEntity entity:result.getRows()){
            HashSet<String> hosts = new HashSet<>();
            searchMap = new SearchMap();
            map.put(entity.getGroupsId(), entity.getGroupsName());
            searchMap.put("groupsId", entity.getGroupsId());
            List<CmdbResourceServerEntity> servers;
            try {
                servers = resourceServerService.getDataList(searchMap, "selectAllIp");
            }catch (Exception e){
                servers = service.getDataList(searchMap, "selectAllIp");
            }
            for (CmdbResourceServerEntity serverEntity:servers){
                hosts.add(serverEntity.getServerId()+"");
            }
            redisUtil.set(MonitorCacheConfig.cacheGroupsHosts+entity.getGroupsId(), gson.toJson(hosts));
        }
        // 所有ID缓存下
        redisUtil.set(MonitorCacheConfig.cacheGroupName, gson.toJson(map));
        return "ok";
    }


    /**
     * 生产监控修改配置
     */
    public void setDefaultMonitorChange(){
        Jedis jedis = redisUtil.getJedis();
        String dateDir = FileWriter.dataDir;
        String separator = FileWriter.separator;
        File[] files = FileRender.getDirFiles(dateDir+separator+"monitor");
        for (File file:files) {
            String key = RedisUtil.app + "_" + MonitorCacheConfig.cacheDefaultChangeQueue + file.getName();
            jedis.del(key);
            jedis.lpush(key, "1");
        }
        jedis.close();
    }

    /**
     * 缓冲每个服务器的信息
     */
    @RequestMapping("/cache/serverInfoSave")
    @ResponseBody
    public String setServerInfoCache(CmdbResourceServerService resourceServerService, String serverId, HttpServletRequest request){
        List<CmdbResourceServerEntity> list;
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(serverId)){
            searchMap.put("serverId", serverId);
        }
        try {
            list = resourceServerService.getDataList(searchMap, "selectServerInfo");
        }catch (Exception e){
            list = service.getDataList(searchMap, "selectServerInfo");
        }
        Jedis jedis = redisUtil.getJedis();

        String key;
        for (CmdbResourceServerEntity entity: list){
            key = RedisUtil.app.concat("_").concat(MonitorCacheConfig.cacheServerInfo).concat(entity.getServerId()+"");
            logger.info(key);
            jedis.set(key, gson.toJson(entity));
        }
        jedis.close();
        if (request != null){
            logSave.logSave(request, "生成服务器缓冲信息");
        }
        return "ok";
    }

    /**
     * 缓冲机房信息
     */
    public void cacheFloor(){
        SearchMap searchMap = new SearchMap();
       PagingResult<CmdbResourceFloorEntity> list = floorService.findAll(searchMap, pageBounds);
       for (CmdbResourceFloorEntity floorEntity: list.getRows()){
           redisUtil.set(MonitorCacheConfig.cacheFloorInfo+floorEntity.getFloorId()+"", floorEntity.getFloorAddress());
       }
    }

    /**
     * 缓冲环境
     */
    public void cacheEntname(){
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceEntnameEntity> list = entnameService.findAll(searchMap, pageBounds);
        for (CmdbResourceEntnameEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheEntnameInfo+entity.getEntId()+"", entity.getEntName());
        }
    }

    /**
     * 缓冲负责人信息
     */
    public void cacheUsers(){
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceUserEntity> list = userService.findAll(searchMap, pageBounds);
        for (CmdbResourceUserEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheUserInfo+entity.getUserId()+"", entity.getUserName());
        }
    }

    /**
     * 缓冲业务线数据
     */
    public void cacheGroups(){
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceGroupsEntity> list = resourceGroupsService.findAll(searchMap, pageBounds);
        for (CmdbResourceGroupsEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheGroupsInfo+entity.getGroupsId()+"", entity.getGroupsName());
        }
    }

    /**
     * 缓冲机柜数据
     */
    public void cacheCabinet(){
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceCabinetEntity> list = cabinetService.findAll(searchMap, pageBounds);
        for (CmdbResourceCabinetEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheCabinetInfo+entity.getCabinetId()+"", entity.getCabinetName());
        }
    }

    /**
     * 脚本信息缓冲
     */
    public void cacheScript(){
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorScriptsEntity> list = scriptsService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorScriptsEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheCabinetInfo+entity.getScriptsId()+"", entity.getFileName());
        }
    }

    /**
     *
     * @param itemMap
     * @param key
     * @param redisUtil
     */
    void makeRedisCache( Map<String, ArrayList> itemMap, String key, RedisUtil redisUtil){
        for (Map.Entry<String, ArrayList> entry:itemMap.entrySet()){
            logger.info("获取到循环key" + key + " " + gson.toJson(entry));
            redisUtil.set(key+entry.getKey().trim(), gson.toJson(entry.getValue()));
        }
    }

    /**
     *
     * @param map
     * @param itemMap
     * @param key
     */
    void setAlarmMap(Map<String,String> map,  Map<String, ArrayList> itemMap, String key){
        ArrayList itemList = new ArrayList();
        String itemId = map.get(key);
        String allGroups = map.get("allGroups");
        if (CheckUtil.checkString(itemId)) {
            if (itemMap.containsKey(itemId)){
                itemList = itemMap.get(itemId);
            }
            if (CheckUtil.checkString(allGroups)) {
                for (String host:allGroups.split(",")) {
                    if (!itemList.contains(host)) {
                        itemList.add(host);
                    }
                }
            }
            itemMap.put(itemId, itemList);
        }
    }

    /**
     * 检查map里面的数据是否有
     * @param map
     * @param keys
     */
    boolean checkMdata(Map<String,String> map, String keys){
        for (String key:keys.split(",")){
            logger.info("检查key" + key + " " + map.get(key)) ;
            if (!CheckUtil.checkString(map.get(key))){
                return false;
            }
        }
        return true;
    }

    /**
     * 任何一个存在就为false
     * @param map
     * @param keys
     * @param isReverse
     * @return
     */
    boolean checkMdata(Map<String,String> map, String keys, boolean isReverse){
        for (String key:keys.split(",")){
            logger.info("检查key" + key + " " + map.get(key)) ;
            if (CheckUtil.checkString(map.get(key))){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param allGroups
     * @param itemList
     */
    void setGroups(String allGroups , ArrayList itemList){
        for (String group:allGroups.split("\n")) {
            if (!itemList.contains(group)) {
                itemList.add(group);
            }
        }
    }

    /**
     *
     * @param map
     * @param key
     */
    void deleteAlarm(Map<String, String> map,  String key, boolean isHosts){
        if (CheckUtil.checkString(map.get("hosts")) && !isHosts) {
            for (String host : map.get("hosts").split(",")) {
                redisUtil.del(key.concat(host.trim()));
            }
        }else {
            redisUtil.del(key);
        }
        cacheAlarmConfigure();
    }

    /**
     *
     * @param map
     * @param item
     * @param itemMap
     */
    void setHostsAlarm(Map<String,String> map, String item, Map<String, ArrayList> itemMap) {
        ArrayList itemList = new ArrayList();
        String allGroups = map.get("allGroups");
        if (!CheckUtil.checkString(allGroups)){
            return;
        }
        if (CheckUtil.checkString(map.get("hosts"))) {
            String[] hosts = map.get("hosts").split("\n");
            for (String host : hosts) {
                if (!CheckUtil.checkString(host)){continue;}
                    String itemId = item + host;
                    if (itemMap.containsKey(itemId)) {
                        itemList = itemMap.get(itemId);
                    }
                    setGroups(allGroups,itemList);
                    itemMap.put(itemId, itemList);
                }
            return;
        }
        if (itemMap.containsKey(item)) {
            itemList = itemMap.get(item);
        }
        setGroups(allGroups,itemList);
        itemMap.put(item, itemList);
    }

    /**
     *
     * @param entity
     */
    @RequestMapping("alarm/setCacheSave")
    @ResponseBody
    public void deleteAlarmConfigure(MonitorAlarmConfigureEntity entity, HttpServletRequest request){
       Map<String,String> map = gson.fromJson(entity.getGsonData(), HashMap.class);
       String key;
        // 四选一
        if (checkMdata(map, "itemId,groupsId,serviceId,hosts")){
            key = map.get("itemId") +"_" + map.get("groupsId") + "_" + map.get("serviceId") + "_" ;
            logger.info("key1 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem + key, false);
            return;
        }
        if (checkMdata(map, "itemId,groupsId,serviceId")){
            key = map.get("itemId") +"_" + map.get("groupsId") + "_" + map.get("serviceId") ;
            logger.info("key2 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);
            return;
        }
        if (checkMdata(map, "itemId,groupsId,hosts")){
            key = map.get("itemId") +"_" + map.get("groupsId") + "_"  ;
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);

            return;
        }
        if (checkMdata(map, "itemId,serviceId,hosts")){
            key = map.get("itemId") +"_" + map.get("serviceId") + "_"  ;
            logger.info("key4 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);

            return;
        }
        if (checkMdata(map, "groupsId,serviceId,hosts")){
            key = map.get("itemId")  + map.get("serviceId")+"_" ;
            logger.info("key8 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +"groups_"+key, false);
            return;

        }
        if (checkMdata(map, "itemId,groupsId")){
            key = map.get("itemId") +"_" + map.get("groupsId");
            logger.info("key5 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);
            return;

        }
        if (checkMdata(map, "itemId,hosts")){
            key = map.get("itemId") +"_";
            logger.info("key6 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);
            return;
        }
        if (checkMdata(map, "itemId,serviceId")){
            key = map.get("itemId")  + map.get("serviceId") ;
            logger.info("key7 " + key);
            deleteAlarm(map, MonitorCacheConfig.cacheAlarmItem +key, false);
            return;
        }

        if (checkMdata(map, "groupsId,serviceId")){
            key = MonitorCacheConfig.cacheAlarmItem + "groups_"+ map.get("groupsId") + "_" + map.get("serviceId") ;
            deleteAlarm(map, key, false);
            return;
        }
        if (checkMdata(map, "groupsId,hosts")){
            key = MonitorCacheConfig.cacheAlarmItem+"groups_"+ map.get("groupsId")+"_";
            logger.info("key10 " + key);
            deleteAlarm(map, key, false);
            return;
        }
        if (checkMdata(map, "serviceId,hosts")){
            key = MonitorCacheConfig.cacheAlarmItem+"service_" + map.get("serviceId")+"_";
            deleteAlarm(map, key, false);
            return;
        }
        if (CheckUtil.checkString(map.get("itemId"))) {
            key = MonitorCacheConfig.cacheAlarmItem+map.get("itemId");
            deleteAlarm(map, key, false);
            return;
        }
        if (CheckUtil.checkString(map.get("groupsId"))) {
            key = MonitorCacheConfig.cacheAlarmGroups + map.get("groupsId");
            deleteAlarm(map, key, false);
            return;
        }
        if (CheckUtil.checkString(map.get("serviceId"))) {
            key = MonitorCacheConfig.cacheAlarmService + map.get("serviceId");
            deleteAlarm(map, key, false);
            return;
        }
        if (CheckUtil.checkString(map.get("hosts"))) {
            String[] hosts = map.get("hosts").split(",");
            for (String host:hosts){
                key = MonitorCacheConfig.cacheAlarmServer+host.trim();
                deleteAlarm(map, key, true);
                return;
            }
        }
        logSave.logSave(request, "生成监控缓存");
    }

    /**
     * 缓存额外配的的监控报警方式
     */
    public void cacheAlarmConfigure(){
        List<MonitorAlarmConfigureEntity> entitys = alarmConfigureService.getListData(new SearchMap(), "selectByAll");
        Map<String, ArrayList> itemMap = new HashMap();
        Map<String, ArrayList> groupsMap = new HashMap();
        Map<String, ArrayList> serverMap = new HashMap();
        Map<String, ArrayList> serviceMap = new HashMap();
        Gson gson = new Gson();
        Map<String,String> map;
        String key;
        for (MonitorAlarmConfigureEntity entity: entitys){
            map = gson.fromJson(entity.getGsonData(), HashMap.class);
            // 四选一
            if (checkMdata(map, "itemId,groupsId,serviceId,hosts")){
                key = map.get("itemId") +"_" + map.get("groupsId") + "_" + map.get("serviceId") + "_" ;
                logger.info("key1 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,groupsId,serviceId")){
                key = map.get("itemId") +"_" + map.get("groupsId") + "_" + map.get("serviceId") ;
                logger.info("key2 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,groupsId,hosts")){
                key = map.get("itemId") +"_" + map.get("groupsId") + "_"  ;
                logger.info("key3 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,serviceId,hosts")){
                key = map.get("itemId") +"_" + map.get("serviceId") + "_"  ;
                logger.info("key4 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "groupsId,serviceId,hosts")){
                key = "groups_" + map.get("itemId")  + map.get("serviceId")+"_" ;
                logger.info("key8 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,groupsId")){
                key = map.get("itemId") +"_" + map.get("groupsId");
                logger.info("key5 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,hosts")){
                key = map.get("itemId") +"_";
                logger.info("key6 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "itemId,serviceId")){
                key = map.get("itemId")  + map.get("serviceId") ;
                logger.info("key7 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }

            if (checkMdata(map, "groupsId,serviceId")){
                key = "groups_" + map.get("groupsId") + "_" + map.get("serviceId") ;
                logger.info("key9 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "groupsId,hosts")){
                key = "groups_" + map.get("groupsId")+"_";
                logger.info("key10 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (checkMdata(map, "serviceId,hosts")){
                key = "service_" + map.get("serviceId")+"_";
                logger.info("key11 " + key);
                setHostsAlarm(map, key, itemMap);
                continue;
            }
            if (CheckUtil.checkString(map.get("itemId")) && checkMdata(map, "groupsId,serviceId,hosts", true)) {
                setAlarmMap(map, itemMap, "itemId");
            }
            if (CheckUtil.checkString(map.get("groupsId")) && checkMdata(map, "itemId,serviceId,hosts", true) ) {
                setAlarmMap(map, groupsMap, "groupsId");
            }
            if (CheckUtil.checkString(map.get("serviceId")) && checkMdata(map, "groupsId,itemId,hosts", true) ) {
                setAlarmMap(map, serviceMap, "serviceId");
            }
            if (CheckUtil.checkString(map.get("hosts")) && checkMdata(map, "groupsId,serviceId,itemId", true) ) {
                String[] hosts = map.get("hosts").split(",");
                for (String host:hosts){
                    if (!CheckUtil.checkString(host)){
                        continue;
                    }
                    logger.info("获取到服务器" + host);
                    map.put("host", host);
                    setAlarmMap(map, serverMap, "host");
                    logger.info("获取到服务器map"+ gson.toJson(serverMap));
                }
            }
        }
        RedisUtil redisUtil = new RedisUtil();
        if (itemMap.size() > 0 ) {
            makeRedisCache(itemMap, MonitorCacheConfig.cacheAlarmItem, redisUtil);
        }
        if (groupsMap.size() > 0) {
            makeRedisCache(groupsMap, MonitorCacheConfig.cacheAlarmGroups, redisUtil);
        }
        if (serverMap.size() > 0 ) {
            makeRedisCache(serverMap, MonitorCacheConfig.cacheAlarmServer, redisUtil);
        }
        if (serviceMap.size() > 0 ) {
            makeRedisCache(serviceMap, MonitorCacheConfig.cacheAlarmService, redisUtil);
        }
    }

    /**
     * 生成所有的cache
     */
    @RequestMapping("cache/all")
    @ResponseBody
    public String allCache(){
        makeAllHostKey(null, null);
        setConfigureCache(null);
        cacheServerInfo(null);
        setServerCache(service, null);
        setServerInfoCache(service, null, null);
        setItemCache(itemService, null);
        setScriptCache(scriptsService, null);
        setContactGroupCache(contactGroupService, null);
        setTemplateCache(null);
        setGroupsCache(null, null);
        cacheGroups(cmdbResourceGroupsService, service);
        setMessagesCache(channelService);
        try {
            setContactCache(null);
            cacheUsers();
            cacheCabinet();
            cacheGroups();
            cacheEntname();
            cacheFloor();
            cacheScript();
        }catch (Exception e){

        }
        return "ok";
    }


}
