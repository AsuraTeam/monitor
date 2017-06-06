package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MonitorConfigureEntity;
import com.asura.monitor.configure.entity.MonitorContactGroupEntity;
import com.asura.monitor.configure.entity.MonitorContactsEntity;
import com.asura.monitor.configure.entity.MonitorGroupsEntity;
import com.asura.monitor.configure.entity.MonitorItemEntity;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
import com.asura.monitor.configure.entity.MonitorTemplateEntity;
import com.asura.monitor.configure.service.MonitorConfigureService;
import com.asura.monitor.configure.service.MonitorContactGroupService;
import com.asura.monitor.configure.service.MonitorContactsService;
import com.asura.monitor.configure.service.MonitorGroupsService;
import com.asura.monitor.configure.service.MonitorItemService;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.configure.service.MonitorTemplateService;
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

    private Gson gson = new Gson();
    private RedisUtil redisUtil = new RedisUtil();
    final SearchMap searchMap = new SearchMap();
    final PageBounds pageBounds = PageResponse.getPageBounds(10000,1);

    /**
     * 将item的数据写入到cache
     *
     * @return
     */
    @RequestMapping("item/setCache")
    @ResponseBody
    public String setItemCache(MonitorItemService monitorItemService) {
        SearchMap searchMap = new SearchMap();
        ArrayList<String> items = new ArrayList();
        PagingResult<MonitorItemEntity> result;
        try {
           result = monitorItemService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        }catch (Exception e){
            result = itemService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
    public String setConfigureCache() {
        SearchMap searchMap = new SearchMap();
        HashSet hostSet = new HashSet();
        PagingResult<MonitorConfigureEntity> result = configureService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        for (MonitorConfigureEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheConfigureKey + m.getConfigureId(), gson.toJson(m));
            try {
                ConfigureUtil configureUtil = new ConfigureUtil();
                hostSet = configureUtil.makeHostMonitorTag(m, hostSet);
                configureUtil.setUpdateMonitor(m);
            }catch (Exception e){

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
    public String setScriptCache(MonitorScriptsService monitorScriptsService) {
        PagingResult<MonitorScriptsEntity> result;
        SearchMap searchMap = new SearchMap();
        try {
              result = monitorScriptsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        }catch (Exception e){
            result = scriptsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
    public String setContactGroupCache(MonitorContactGroupService monitorContactGroupService) {
        SearchMap searchMap = new SearchMap();
        String groups = "";
        PagingResult<MonitorContactGroupEntity> result;
        try {
            result = monitorContactGroupService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        }catch (Exception e){
            result = contactGroupService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
    public String setContactCache() {
        try {
            SearchMap searchMap = new SearchMap();
            PagingResult<MonitorContactsEntity> result = contactsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
            for (MonitorContactsEntity m : result.getRows()) {
                redisUtil.set(MonitorCacheConfig.cacheContactKey + m.getContactsId(), gson.toJson(m));
            }
        }catch (Exception e){
        }
        return "ok";
    }

    /**
     * 生成主机的id,agent方便从ip地址获取到自己的id
     */
    @RequestMapping("configure/setServerCache")
    @ResponseBody
    public String setServerCache(CmdbResourceServerService resourceServerService) {
        List<CmdbResourceServerEntity> result;
        try {
            result = resourceServerService.getDataList(null, "selectAllIp");
        }catch (Exception e){
            result = service.getDataList(null, "selectAllIp");
        }
        redisUtil.setHostId(result);
        return "ok";
    }

    /**
     * 将模板的数据写入到cache
     *
     * @return
     */
    @RequestMapping("template/setCache")
    @ResponseBody
    public String setTemplateCache() {
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorTemplateEntity> result = templateService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        for (MonitorTemplateEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheTemplateKey + m.getTemplateId(), gson.toJson(m));
        }
        return "ok";
    }

    /**
     * 获取组机组的IP地址
     *
     * @param m
     *
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
     * 将模板的数据写入到cache
     *
     * @return
     */
    @RequestMapping("groups/setCache")
    @ResponseBody
    public String setGroupsCache() {
        HashSet hostIdArr ;
        Map<String,HashSet> hostMap = new HashMap<>();
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorGroupsEntity> result = groupsService.findAll(searchMap, PageResponse.getPageBounds(100000, 1), "selectByAll");
        for (MonitorGroupsEntity m : result.getRows()) {
            String hosts = getHosts(m);
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
    public Map<String, HashSet> setGroupHostConfig(Map<String, HashSet> hostMap, String configId, String hostId){
        HashSet conf ;
        // 将每个host拥有的配置写入到redis
        if(!hostMap.containsKey(hostId)){
            conf = new HashSet();
        }else {
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
    public String makeAllHostKey(MonitorConfigureService monitorConfigureService){
        Jedis jedis = redisUtil.getJedis();
        SearchMap searchMap = new SearchMap();
        searchMap.put("isValid",1);
        HashSet hostIds = new HashSet();

        // 存放每个主机拥有的所有配置文件id
        Map<String, HashSet> hostMap = new HashMap();
        // 存放每个组拥有的所有配置文件的ID

        PagingResult<MonitorConfigureEntity> result;
        try {
            result = monitorConfigureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
        }catch (Exception e){
            result = configureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
        }
        for (MonitorConfigureEntity m : result.getRows()) {
            String[] hosts = null;
            if(CheckUtil.checkString(m.getHosts()) && m.getMonitorHostsTp().equals("host")) {

                hosts = m.getHosts().split(",");
            }
            // 设置组的信息
            if(CheckUtil.checkString(m.getGname()) && m.getMonitorHostsTp().equals("groups")){
                hosts = redisUtil.get(MonitorCacheConfig.cacheGroupsKey.concat(m.getGname())).split(",");
                logger.info("获取到组配置信息 " + gson.toJson(hostIds));
            }

            for (String s : hosts) {
                if(s.length() < 1){continue;}
                hostIds.add(s);
                hostMap = setGroupHostConfig(hostMap, m.getConfigureId()+"", s);
            }

            for (Map.Entry<String, HashSet> entry : hostMap.entrySet()) {
                try {
                    jedis.set(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostConfigKey + entry.getKey(), gson.toJson(entry.getValue()));
                }catch (Exception e){
                    jedis = redisUtil.getJedis();
                    jedis.set(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostConfigKey + entry.getKey(), gson.toJson(entry.getValue()));
                }
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
     * 缓冲每个服务器的相信新
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
       PagingResult<CmdbResourceFloorEntity> list = floorService.findAll(searchMap, pageBounds);
       for (CmdbResourceFloorEntity floorEntity: list.getRows()){
           redisUtil.set(MonitorCacheConfig.cacheFloorInfo+floorEntity.getFloorId()+"", floorEntity.getFloorAddress());
       }
    }

    /**
     * 缓冲环境
     */
    public void cacheEntname(){
        PagingResult<CmdbResourceEntnameEntity> list = entnameService.findAll(searchMap, pageBounds);
        for (CmdbResourceEntnameEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheEntnameInfo+entity.getEntId()+"", entity.getEntName());
        }
    }

    /**
     * 缓冲负责人信息
     */
    public void cacheUsers(){
        PagingResult<CmdbResourceUserEntity> list = userService.findAll(searchMap, pageBounds);
        for (CmdbResourceUserEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheUserInfo+entity.getUserId()+"", entity.getUserName());
        }
    }

    /**
     * 缓冲业务线数据
     */
    public void cacheGroups(){
        PagingResult<CmdbResourceGroupsEntity> list = resourceGroupsService.findAll(searchMap, pageBounds);
        for (CmdbResourceGroupsEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheGroupsInfo+entity.getGroupsId()+"", entity.getGroupsName());
        }
    }

    /**
     * 缓冲机柜数据
     */
    public void cacheCabinet(){
        PagingResult<CmdbResourceCabinetEntity> list = cabinetService.findAll(searchMap, pageBounds);
        for (CmdbResourceCabinetEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheCabinetInfo+entity.getCabinetId()+"", entity.getCabinetName());
        }
    }

    /**
     * 脚本信息缓冲
     */
    public void cacheScript(){
        PagingResult<MonitorScriptsEntity> list = scriptsService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorScriptsEntity entity: list.getRows()){
            redisUtil.set(MonitorCacheConfig.cacheCabinetInfo+entity.getScriptsId()+"", entity.getFileName());
        }
    }

    /**
     * 生成所有的cache
     */
    @RequestMapping("cache/all")
    @ResponseBody
    public String allCache(){
        makeAllHostKey(null);
        setConfigureCache();
        setServerCache(service);
        setServerInfoCache(service, null, null);
        setItemCache(itemService);
        setScriptCache(scriptsService);
        setContactGroupCache(contactGroupService);
        setTemplateCache();
        setGroupsCache();
        cacheGroups(cmdbResourceGroupsService, service);
        setMessagesCache(channelService);
        try {
            setContactCache();
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
