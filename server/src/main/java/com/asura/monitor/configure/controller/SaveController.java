package com.asura.monitor.configure.controller;

import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.ResponseVo;
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
import com.asura.util.DateUtil;
import com.asura.util.HttpUtil;
import com.asura.util.LdapAuthenticate;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheConfigureHostsListKey;
import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheGroupsKey;
import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheHostCnfigureKey;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 所有监控保存配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/20 08:00:00
 */
@Controller
@RequestMapping("/monitor/configure/")
public class SaveController {

    @Autowired
    private com.asura.monitor.configure.service.MonitorInformationService informationService;

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
    private IndexController indexController;

    @Autowired
    private PermissionsCheck permissionsCheck;
    private Gson gson = new Gson();
    private RedisUtil redisUtil = new RedisUtil();
    @Autowired
    private LdapAuthenticate ldapAuthenticate;

    @Autowired
    private MonitorMessageChannelService channelService;

    @Autowired
    private CacheController cacheController;

    /**
     * 模板列表
     *
     * @return
     */
    @RequestMapping("template/save")
    @ResponseBody
    public ResponseVo templateSave(MonitorTemplateEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getTemplateId() != null) {
            templateService.update(entity);
        } else {
            templateService.save(entity);
        }
        redisUtil.set(MonitorCacheConfig.cacheTemplateKey + entity.getTemplateId(), gson.toJson(entity));
        updateHostUpdate("template");
        return ResponseVo.responseOk(null);
    }



    /**
     * 监控组配置
     *
     * @return
     */
    @RequestMapping("groups/save")
    @ResponseBody
    public ResponseVo groupsSave(MonitorGroupsEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getGroupsId() != null) {
            groupsService.update(entity);
        } else {
            groupsService.save(entity);
        }
        redisUtil.set(cacheGroupsKey + entity.getGroupsId(), gson.toJson(entity));
        updateHostUpdate("groups");
        return ResponseVo.responseOk(null);
    }





    /**
     * 联系人配置
     *
     * @return
     */
    @RequestMapping("contacts/save")
    @ResponseBody
    public ResponseVo contactsSave(MonitorContactsEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getContactsId() != null) {
            redisUtil.set(MonitorCacheConfig.cacheContactKey + entity.getContactsId(), gson.toJson(entity));
            contactsService.update(entity);
        } else {
            contactsService.save(entity);
        }
        cacheController.setContactCache();
        return ResponseVo.responseOk(null);
    }



    /**
     * 联系组配置
     *
     * @return
     */
    @RequestMapping("contactGroup/save")
    @ResponseBody
    public ResponseVo contactGroupSave(MonitorContactGroupEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getGroupId() != null) {
            contactGroupService.update(entity);
        } else {
            entity.setStatus(1);
            contactGroupService.save(entity);
        }
//        redisUtil.set(MonitorCacheConfig.cacheContactGroupKey + entity.getGroupName(), gson.toJson(entity));
        cacheController.setContactGroupCache();
        return ResponseVo.responseOk(null);
    }


    /**
     * 脚本配置
     *
     * @return
     */
    @RequestMapping("script/save")
    @ResponseBody
    public ResponseVo scriptSave(MonitorScriptsEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getScriptsId() != null) {
            scriptsService.update(entity);
        } else {
            List<MonitorScriptsEntity> r = scriptsService.getDataList(null, "selectMaxId");
            int id ;
            try {
                id = r.get(0).getScriptsId()+1;
            }catch (Exception e){
                id = 1;
            }
            entity.setScriptsId(id);
            scriptsService.save(entity);
        }
        redisUtil.set(MonitorCacheConfig.cacheScriptKey + entity.getScriptsId(), gson.toJson(entity));
        updateHostUpdate("script");
        cacheController.setDefaultMonitorChange();
        return ResponseVo.responseOk(null);
    }

    /**
     * @param type
     */
    void updateHostUpdate(String type){
        String result = redisUtil.get(MonitorCacheConfig.cacheConfigureHostsListKey);
        // 将脚本更新通知到agent
        Jedis jedis = redisUtil.getJedis();
        if(result!=null){
            ArrayList<String> arrayList = gson.fromJson(result, ArrayList.class);
            for (String  h:arrayList) {
                jedis.lpush(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostUpdateQueue + h, type);
            }
        }
        jedis.close();
    }


    /**
     * 项目配置
     *
     * @return
     */
    @RequestMapping("item/save")
    @ResponseBody
    public ResponseVo itemSave(MonitorItemEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getItemId() != null) {
            itemService.update(entity);
        } else {
            List<MonitorItemEntity> r = itemService.getDataList(null, "selectMaxId");
            int id ;
            try {
                id = r.get(0).getItemId()+1;
            }catch (Exception e){
                id = 1;
            }
            entity.setItemId(id);
            itemService.save(entity);
        }
//        redisUtil.set(MonitorCacheConfig.cacheItemKey + entity.getItemId(), gson.toJson(entity));
        cacheController.setItemCache();
        cacheController.setDefaultMonitorChange();
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @return
     */
    @RequestMapping("messages/save")
    @ResponseBody
    public ResponseVo messagesSave(MonitorMessageChannelEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getChannelId() != null) {
            channelService.update(entity);
        } else {
            channelService.save(entity);
        }
        redisUtil.set(MonitorCacheConfig.cacheChannelKey + entity.getChannelTp(), gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     * 项目配置
     *
     * @return
     */
    @RequestMapping("configure/save")
    @ResponseBody
    public ResponseVo configureSave(MonitorConfigureEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getConfigureId() != null) {
            configureService.update(entity);
        } else {
            List<MonitorConfigureEntity> r = configureService.getDataList(null, "selectMaxId");
            int id ;
            try {
                id = r.get(0).getConfigureId()+1;
            }catch (Exception e){
                id = 1;
            }
            entity.setConfigureId(id);
            configureService.save(entity);
        }
        redisUtil.set(MonitorCacheConfig.cacheConfigureKey+entity.getConfigureId(),gson.toJson(entity));
        makeHostMonitorTag(entity);
        setUpdateMonitor(entity);
        cacheController.makeAllHostKey();
        cacheController.setDefaultMonitorChange();
        return ResponseVo.responseOk(null);
    }

    /**
     * 设置组和host的缓存
     * @param hashSet
     * @param newHashSet
     * @param entity
     * @param arrayList
     * @return
     */
    public HashSet setHostGroupData(HashSet<String> hashSet, HashSet<String> newHashSet, MonitorConfigureEntity entity, ArrayList arrayList){
       if(hashSet!=null) {
           for (String m : hashSet) {
               if (entity.getIsValid()==1) {
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
     * 生成每个主机和组的监控配置
     */
    public void makeHostMonitorTag(MonitorConfigureEntity entity) {
        String cacheData = entity.getConfigureId() + "";
        if (entity.getHostId() != null) {
            // 监控的组
            String[] groups = entity.getGroupsId().split(",");
            ArrayList<String> arrayList = new ArrayList<>();
            for(String c:groups){
                if (entity.getIsValid() == 1) {
                    arrayList.add(c);
                }
            }

            HashSet<String> groupSet = new HashSet<>();

            for (int i = 0; i < groups.length; i++) {
                String key = MonitorCacheConfig.cacheGroupsConfigureKey + groups[i] + "_" +
                        entity.getConfigureId();
                if (entity.getIsValid() == 1) {
                    // 缓存组的数据
                    redisUtil.set(key, cacheData);
                    groupSet.add(groups[i]);
                } else {
                    redisUtil.del(key);
                }
            }
            HashSet<String> cacheGroups = gson.fromJson(redisUtil.get(MonitorCacheConfig.cacheConfigureGroupsListKey), HashSet.class);
            groupSet = setHostGroupData(cacheGroups,groupSet,entity,arrayList);
            redisUtil.set(MonitorCacheConfig.cacheConfigureGroupsListKey, gson.toJson(groupSet));
        }

        if (entity.getHosts() != null) {
            // 监控的主机
            String[] hosts = entity.getHosts().split(",");
            // 从redis获取监控的组和host
            HashSet<String> hostSet = new HashSet<>();

            ArrayList<String> arrayList = new ArrayList<>();
            for(String c:hosts){
                if (entity.getIsValid() == 1) {
                    arrayList.add(c);
                }
            }

            for (int i = 0; i < hosts.length; i++) {
                String  key = cacheHostCnfigureKey + hosts[i] + "_"
                        + entity.getConfigureId();
                if (entity.getIsValid() == 1) {
                    // 缓存host的配置信息
                    redisUtil.set(key, gson.toJson(cacheData));
                    hostSet.add(hosts[i].replace("\\n",""));
                }else{
                    redisUtil.del(key);
                }
            }
            HashSet<String> cacheHosts = gson.fromJson(redisUtil.get(cacheConfigureHostsListKey), HashSet.class);
            hostSet = setHostGroupData(cacheHosts,hostSet,entity,arrayList);
            redisUtil.set(cacheConfigureHostsListKey, gson.toJson(hostSet));
        }
    }

    /**
     *
     * @param hosts
     * @param hostSet
     * @return
     */
    HashSet<String> getHosts(String[] hosts, HashSet hostSet){
        for (String h:hosts){
            hostSet.add(h);
        }
        return hostSet;
    }

    /**
     * 获取相关配置的更新
     * 获取主机
     * 然后每个主机更新自己的数据
     * @param entity
     */
    void setUpdateMonitor(MonitorConfigureEntity entity){
           Jedis jedis = redisUtil.getJedis();
           HashSet<String> hostSet = new HashSet<>();
           String host = entity.getHosts();
           String group = entity.getGroupsId();
           if(host!=null){
               String[] hosts = host.split(",");
               if (entity.getIsValid() == 1) {
                   hostSet = getHosts(hosts, hostSet);
               }
           }
           if(group!=null){
               String[] groups = group.split(",");
               for (String g:groups){
                   String result = redisUtil.get(MonitorCacheConfig.cacheGroupsKey+g);
                   if(result!=null && result.length()>1){
                       MonitorGroupsEntity groupsEntity = gson.fromJson(result, MonitorGroupsEntity.class);
                       String[] hosts = groupsEntity.getHosts().split(",");
                           hostSet = getHosts(hosts, hostSet);
                   }
               }
           }
           for (String h:hostSet){
              jedis.lpush(RedisUtil.app+"_"+MonitorCacheConfig.cacheHostUpdateQueue+h, "configure");
           }
        jedis.close();
    }

    /**
     * 修改报警开关
     * @param value
     * @return
     */
    @RequestMapping("messages/recordSaveSetAlarm")
    @ResponseBody
    public String recordSaveSetAlarm(String value, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(request.getSession());
        String dept = ldapAuthenticate.getSignUserInfo("department", "sAMAccountName=" + user);
        if (dept != null && !dept.contains("运维") && !user.equals("admin")) {
            return "error";
        }
        indexController.logSave(request, "修改报警开关 " + value);
        redisUtil.set(MonitorCacheConfig.cacheAlarmSwitch, value);
        return "ok";
    }

    /**
     * 删除监控
     * @return
    */
    @RequestMapping("configure/delete")
    @ResponseBody
    public String deleteConfigure(int id, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(request.getSession());
        if (user.length()<2){
            return "请登陆后操作";
        }
        String dept = ldapAuthenticate.getSignUserInfo("department", "sAMAccountName=" + user);
        MonitorConfigureEntity configureEntity = configureService.findById(id,MonitorConfigureEntity.class);
        String lastModifyUser = configureEntity.getLastModifyUser();
        if (!user.equals(lastModifyUser) && ! user.equals("admin") && ! dept.contains("运维")){
            return "no permissions";
        }
        String hosts = configureEntity.getHosts();
        String[] hostsList = hosts.split(",");
        String url;
        String server;
        for (String hostId: hostsList){
            if (hostId.equals("")||hostId.length()<1){
                continue;
            }
            String portData = redisUtil.get(MonitorCacheConfig.cacheAgentServerInfo + hostId);
            if (portData.length() > 5) {
                Map serverMap = gson.fromJson(portData, HashMap.class);
                server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp+hostId);
                url = "http://" + server + ":" + serverMap.get("port") + "/monitor/init";
                HttpUtil.sendGet(url);
            }
        }
        configureService.delete(configureEntity);
        redisUtil.del(MonitorCacheConfig.cacheConfigureKey);
        indexController.logSave(request, "删除监控配置" + gson.toJson(configureEntity));
        return "ok";
  }

}
