package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MonitorPauseEntity;
import com.asura.monitor.configure.service.MonitorItemService;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.util.MonitorUtil;
import com.asura.resource.controller.ServerController;
import com.asura.util.CheckUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 监控暂停配置
 */
@Controller
@RequestMapping("/monitor/pause/")
public class MonitorPauseController {

    private final Logger logger = LoggerFactory.getLogger(MonitorPauseController.class);

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    IndexController indexController;

    @Autowired
    private ServerController serverController;

    @Autowired
    private MonitorScriptsService scriptsService;

    @Autowired
    private MonitorItemService itemService;

    /**
     * 添加
     * @return
     */
    @RequestMapping("add")
    public String add(Model model) {
        serverController.getData(model);
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(100000,1);
        model.addAttribute("scripts", scriptsService.findAll(searchMap, pageBounds, "selectByAll").getRows());
        model.addAttribute("items", itemService.findAll(searchMap, pageBounds, "selectByAll").getRows());
        return "/monitor/pause/add";
    }


    /**
     * 获取停止的配置，存在redis里
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/monitor/pause/list";
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData() {
        Gson gson = new Gson();
        ArrayList<MonitorPauseEntity> list = new ArrayList();
        RedisUtil redisUtil = new RedisUtil();
        String data = redisUtil.get(MonitorCacheConfig.cacheStopMonitorData);
        if (CheckUtil.checkString(data)) {
            Type type = new TypeToken<ArrayList<MonitorPauseEntity>>() {
            }.getType();
            list = gson.fromJson(data, type);
        }
        ArrayList newList = new ArrayList();
        long closeTime;
        for (MonitorPauseEntity entity: list){
            if (entity.getCreateTime() > System.currentTimeMillis() / 1000){
                closeTime = 0;
            }else {
                closeTime = ((System.currentTimeMillis() / 1000) - entity.getCreateTime());
            }
            if (closeTime < Long.valueOf(entity.getPauseTime())) {
                logger.info("获取到距离关闭时间" + closeTime);
                logger.info("redis数据为" + gson.toJson(entity));
                if (closeTime < Long.valueOf(entity.getPauseTime()) || closeTime > 0) {
                    String sleep = (closeTime - Long.valueOf(entity.getPauseTime())) + "";
                    entity.setCloseTime(MonitorUtil.getStopMonitorTime(Long.valueOf(sleep.replace("-", ""))));
                    newList.add(entity);
                }
            }
            if (closeTime == 0){
                entity.setCloseTime("未到维护时间");
                newList.add(entity);
            }
        }
        redisUtil.set(MonitorCacheConfig.cacheStopMonitorData, gson.toJson(newList));
        return PageResponse.getList(list, 1);
    }

    /**
     * 公共停止函数
     * @param name
     * @param key
     * @param description
     * @param entity
     * @param request
     */
    void stopMonitor(String name, String key,String description, MonitorPauseEntity entity, HttpServletRequest request){
        try {
            if (name != null && Integer.valueOf(name) > 0) {
                RedisUtil redisUtil = new RedisUtil();
                key = key.concat(name);
                redisUtil.del(key);
                redisUtil.setex(key, Integer.valueOf(entity.getPauseTime()), "1");
                indexController.logSave(request, description.concat(name));
            }
        }catch (Exception e){

        }
    }

    /**
     *
     * @param id
     * @return
     */
    boolean checkId(String id){
        if (CheckUtil.checkString(id) && Integer.valueOf(id) > 0) return true;
        return false;
    }

    /**
     *
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(MonitorPauseEntity entity, HttpServletRequest request) {
        Gson gson = new Gson();
        RedisUtil redisUtil = new RedisUtil();
        if (entity.getCreateTime()==0) {
            entity.setCreateTime(System.currentTimeMillis() / 1000);
        }
        entity.setCreateUser(permissionsCheck.getLoginUser(request.getSession()));

        // 对单个server进行暂停
        if (checkId(entity.getServerId())) {
            entity.setIpAddress(redisUtil.get(MonitorCacheConfig.cacheHostIdToIp.concat(entity.getServerId()+"")));
            stopMonitor(entity.getServerId()+"", MonitorCacheConfig.cacheStopServer, "停止某个server监控报警", entity, request);
        }
        // 停止某个环境的报警
        if (checkId(entity.getEntId())) {
            entity.setEntName(redisUtil.get(MonitorCacheConfig.cacheEntnameInfo.concat(entity.getEntId()+"")));
            stopMonitor(entity.getEntId()+"", MonitorCacheConfig.cacheStopEntname, "停止某个环境的报警", entity, request);
        }
        // 停止某个机柜报警
        if (checkId(entity.getCabinetId())) {
            entity.setCabinetName(redisUtil.get(MonitorCacheConfig.cacheCabinetInfo.concat(entity.getCabinetId()+"")));
            stopMonitor(entity.getCabinetId()+"", MonitorCacheConfig.cacheStopCabinet, "停止某个机柜报警", entity, request);
        }
        // 停止某个负责人报警
        if (checkId(entity.getUserId())) {
            entity.setUserName(redisUtil.get(MonitorCacheConfig.cacheUserInfo.concat(entity.getUserId()+"")));
            stopMonitor(entity.getUserId(), MonitorCacheConfig.cacheStopUser, "停止某个负责人报警", entity, request);
        }
        // 停止某个业务线
        if (checkId(entity.getGroupsId())) {
            entity.setGroupsName(redisUtil.get(MonitorCacheConfig.cacheGroupsInfo.concat(entity.getGroupsId()+"")));
            stopMonitor(entity.getGroupsId()+"", MonitorCacheConfig.cacheStopGroups, "停止某个业务线报警", entity, request);
        }
        // 停止某个机房报警
        if (checkId(entity.getFloorId())) {
            entity.setFloorName(redisUtil.get(MonitorCacheConfig.cacheFloorInfo.concat(entity.getFloorId()+"")));
            stopMonitor(entity.getFloorId()+"", MonitorCacheConfig.cacheStopFloor, "停止某个机房报警", entity, request);
        }
        // 停止某个宿主机
        if (checkId(entity.getHostId())) {
            entity.setHostIpAddress(redisUtil.get(MonitorCacheConfig.cacheHostIdToIp.concat(entity.getHostId()+"")));
            stopMonitor(entity.getHostId()+"", MonitorCacheConfig.cacheStopHosts, "停止某个宿主机报警", entity, request);
        }
        // 停止某个脚本
        if (checkId(entity.getScriptsId())) {
            entity.setScriptName(redisUtil.get(MonitorCacheConfig.cacheScriptInfo.concat(entity.getScriptsId()+"")));
            stopMonitor(entity.getScriptsId(), MonitorCacheConfig.cacheStopScripts, "停止某个脚本报警", entity, request);
        }
        logger.info("获取到停止信息" + gson.toJson(entity));
        ArrayList<MonitorPauseEntity> list = new ArrayList();
        ArrayList newList = new ArrayList();
        boolean isUpdate = false;
        String data = redisUtil.get(MonitorCacheConfig.cacheStopMonitorData);
        if (CheckUtil.checkString(data) && data.length() > 10 ){
            Type type = new TypeToken<ArrayList<MonitorPauseEntity>>() {
            }.getType();

            list = gson.fromJson(data, type);

            for (MonitorPauseEntity entity1: list){
                if (    checkUpdate(entity.getCabinetId(), entity1.getCabinetId()) ||
                        checkUpdate(entity.getEntId(), entity1.getEntId()) ||
                        checkUpdate(entity.getScriptsId(), entity1.getScriptsId()) ||
                        checkUpdate(entity.getUserId(), entity1.getUserId()) ||
                        checkUpdate(entity.getFloorId(), entity1.getFloorId()) ||
                        checkUpdate(entity.getGroupsId(), entity1.getGroupsId()) ||
                        checkUpdate(entity.getServerId(), entity1.getServerId()))
                    isUpdate = true;
                if (isUpdate){
                    entity1.setPauseTime(entity.getPauseTime());
                    logger.info("更新暂停数据 " + gson.toJson(entity1));
                    newList.add(entity1);
                }else{
                    logger.info("添加暂停数据 " + gson.toJson(entity));
                    newList.add(entity1);
                }
            }
        }else{
            list.add(entity);
            redisUtil.set(MonitorCacheConfig.cacheStopMonitorData, gson.toJson(list));
        }
        if (!isUpdate){
            logger.info("添加新的暂停数据 " + gson.toJson(entity));
            newList.add(entity);
        }
        if (newList.size() >  0 ) {
            redisUtil.set(MonitorCacheConfig.cacheStopMonitorData, gson.toJson(newList));
        }
        return "ok";
    }

    /**
     *
     * @param id1
     * @param id2
     * @return
     */
    boolean checkUpdate(String id1, String id2){
        if (id1 != null && id1.length() > 0 && id2 != null && id2.length()  > 0   && id2.equals(id1)){
            return true;
        }
        return false;
    }
}
