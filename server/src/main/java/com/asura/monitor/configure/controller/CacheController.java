package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
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
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.util.RedisUtil;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.resource.service.CmdbResourceServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    private Gson gson = new Gson();
    private RedisUtil redisUtil = new RedisUtil();

    /**
     * 将item的数据写入到cache
     *
     * @return
     */
    @RequestMapping("item/setCache")
    @ResponseBody
    public String setItemCache() {
        SearchMap searchMap = new SearchMap();
        ArrayList<String> items = new ArrayList();
        PagingResult<MonitorItemEntity> result = itemService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
    public String setMessagesCache() {
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorMessageChannelEntity> result = channelService.findAll(searchMap, PageResponse.getPageBounds(10, 1), "selectByAll");
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
        PagingResult<MonitorConfigureEntity> result = configureService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        for (MonitorConfigureEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheConfigureKey + m.getConfigureId(), gson.toJson(m));
        }
        return "ok";
    }
    /**
     * 将脚本的数据写入到cache
     *
     * @return
     */
    @RequestMapping("script/setCache")
    @ResponseBody
    public String setScriptCache() {
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorScriptsEntity> result = scriptsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
    public String setContactGroupCache() {
        SearchMap searchMap = new SearchMap();
        String groups = "";
        PagingResult<MonitorContactGroupEntity> result = contactGroupService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
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
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorContactsEntity> result = contactsService.findAll(searchMap, PageResponse.getPageBounds(10000000, 1), "selectByAll");
        for (MonitorContactsEntity m : result.getRows()) {
            redisUtil.set(MonitorCacheConfig.cacheContactKey + m.getContactsId(), gson.toJson(m));
        }
        return "ok";
    }

    /**
     * 生成主机的id,agent方便从ip地址获取到自己的id
     */
    @RequestMapping("configure/setServerCache")
    @ResponseBody
    public String setServerCache() {
        List<CmdbResourceServerEntity> result = service.getDataList(null, "selectAllIp");
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
                    hostIdArr = (HashSet)hostMap.get(host);
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
            conf = (HashSet)hostMap.get(hostId);
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
    public String makeAllHostKey(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("isValid",1);
        HashSet hostIds = new HashSet();
        HashSet groupIds = new HashSet();

        // 存放每个主机拥有的所有配置文件id
        Map<String, HashSet> hostMap = new HashMap();
        // 存放每个组拥有的所有配置文件的ID
        Map<String, HashSet> groupMap = new HashMap();

        PagingResult<MonitorConfigureEntity> result = configureService.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
        for (MonitorConfigureEntity m : result.getRows()) {

            if(m.getHosts()!=null) {
                String[] hosts = m.getHosts().split(",");
                for (String s : hosts) {
                    if(s.length()<1){continue;}
                    hostIds.add(s);
                    hostMap = setGroupHostConfig(hostMap, m.getConfigureId()+"", s);
                }
            }
            for (Map.Entry<String, HashSet> entry : hostMap.entrySet()) {
                redisUtil.set(MonitorCacheConfig.cacheHostConfigKey + entry.getKey(), gson.toJson(entry.getValue()));
            }

            if(m.getGname()!=null){
                String[] groups = m.getGname().split(",");
                for (String s : groups) {
                    if(s.length()<1){continue;}
                    groupIds.add(s);
                    // 将每个host拥有的配置写入到redis
                    groupMap = setGroupHostConfig(groupMap, m.getConfigureId()+"", s);
                }
            }
            for (Map.Entry<String, HashSet> entry : groupMap.entrySet()) {
                redisUtil.set(MonitorCacheConfig.cacheGroupConfigKey + entry.getKey(), gson.toJson(entry.getValue()));
            }
        }
        String allHost = gson.toJson(hostIds);
        redisUtil.set(MonitorCacheConfig.cacheAllHostIsValid, allHost);
        String allGroup = gson.toJson(groupIds);
        redisUtil.set(MonitorCacheConfig.cacheAllGroupsIsValid, allGroup);
        return "ok";
    }

    /**
     * 生成业务线数据缓存
     * 只存储id,和业务线名称
     */
    @RequestMapping("cache/groups")
    @ResponseBody
    public String cacheGroups(){
        Map map = new HashMap();
        PagingResult<CmdbResourceGroupsEntity> result = cmdbResourceGroupsService.findAll(null,PageResponse.getPageBounds(10000000, 1));
        SearchMap searchMap = new SearchMap();
        for (CmdbResourceGroupsEntity entity:result.getRows()){
            HashSet<String> hosts = new HashSet<>();
            map.put(entity.getGroupsId(), entity.getGroupsName());
            searchMap.put("groupsId", entity.getGroupsId());
            List<CmdbResourceServerEntity> servers = service.getDataList(searchMap, "selectAllIp");
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
    void setDefaultMonitorChange(){
        Jedis jedis = redisUtil.getJedis();
        String dateDir = FileWriter.dataDir;
        String separator = FileWriter.separator;
        File[] files = FileRender.getDirFiles(dateDir+separator+"monitor");
        for (File file:files) {
            String key = RedisUtil.app + "_" + MonitorCacheConfig.cacheDefaultChangeQueue + file.getName();
            System.out.println(key);
            jedis.del(key);
            jedis.lpush(key, "1");
        }
        jedis.close();
    }

    /**
     * 生成所有的cache
     */
    @RequestMapping("cache/all")
    @ResponseBody
    public String allCache(){
        makeAllHostKey();
        setConfigureCache();
        setServerCache();
        setItemCache();
        setScriptCache();
        setContactGroupCache();
        setTemplateCache();
        setGroupsCache();
        cacheGroups();
        setMessagesCache();
        return "ok";
    }


}
