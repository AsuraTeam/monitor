package com.asura.monitor.gateway.controller;

import com.asura.framework.base.paging.PagingResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.configure.entity.*;
import com.asura.monitor.configure.entity.MonitorGroupsEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 跨机房网关接口
 * Created by zhaoyun on 2017/6/17.
 */
@Controller
@RequestMapping("/monitor/gateway/")
public class GatewayController {

    @Autowired
    private CacheController cacheController;

    private final Gson gson = new Gson();
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GatewayController.class);

    /**
     * @param type
     * @param data
     * @return
     */
    @RequestMapping(value = "api/{type}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String api(@PathVariable String type, String data) {
        switch (type) {
            case "setServerToRedis":
                return setServerToRedis(data);
            case "setMonitorItemToRedis":
                return setMonitorItemToRedis(data);
            case "setMonitorScriptsToRedis":
                return setMonitorScriptsToRedis(data);
            case "setContactGroupCacheToRedis":
                return setContactGroupCacheToRedis(data);
            case "makeAllHostKey":
                return makeAllHostKey(data);
            case "setConfigureCache":
                return setConfigureCache(data);
            case "setTemplateCache":
                return setTemplateCache(data);
            case "setGroupsCache":
                return setGroupsCache(data);
            case "setContacCacheToRedis":
                return setContacCacheToRedis(data);
            default:
                return "无方法";
        }
    }


    /**
     * 设置异地机房服务器信息
     *
     * @return
     */
    @RequestMapping("setServerToRedis")
    @ResponseBody
    public String setServerToRedis(String data) {
        Type type = new TypeToken<ArrayList<CmdbResourceServerEntity>>() {
        }.getType();
        ArrayList<CmdbResourceServerEntity> entities = gson.fromJson(data, type);
        cacheController.setServerCache(null, entities);
        return "ok";
    }

    /**
     * 设置异地机房监控项目信息
     *
     * @return
     */
    @RequestMapping("setMonitorItemToRedis")
    @ResponseBody
    public String setMonitorItemToRedis(String data) {
        Gson gson = new Gson();
        Type type = new TypeToken<PagingResult<MonitorItemEntity>>() {
        }.getType();
        PagingResult<MonitorItemEntity> entities = gson.fromJson(data, type);
        cacheController.setItemCache(null, entities);
        return "ok";
    }

    /**
     * 设置异地机房监控脚本信息
     *
     * @return
     */
    @RequestMapping("setMonitorScriptsToRedis")
    @ResponseBody
    public String setMonitorScriptsToRedis(String data) {
        Type type = new TypeToken<PagingResult<MonitorScriptsEntity>>() {
        }.getType();
        PagingResult<MonitorScriptsEntity> entities = gson.fromJson(data, type);
        cacheController.setScriptCache(null, entities);
        return "ok";
    }

    /**
     * 设置异地机房监控联系组
     *
     * @return
     */
    @RequestMapping("setContactGroupCacheToRedis")
    @ResponseBody
    public String setContactGroupCacheToRedis(String data) {
        Type type = new TypeToken<PagingResult<MonitorContactGroupEntity>>() {
        }.getType();
        PagingResult<MonitorContactGroupEntity> entities = gson.fromJson(data, type);
        cacheController.setContactGroupCache(null, entities);
        return "ok";
    }

    /**
     * 设置异地机房报警接收人
     * @return
     */
    @RequestMapping("setContactCacheToRedis")
    @ResponseBody
    public String setContacCacheToRedis(String data) {
        Type type = new TypeToken<PagingResult<MonitorContactsEntity>>() {
        }.getType();
        PagingResult<MonitorContactsEntity> entities = gson.fromJson(data, type);
        cacheController.setContactCache(entities);
        return "ok";
    }

    /**
     * 设置异地机房host信息
     *
     * @return
     */
    @RequestMapping("makeAllHostKey")
    @ResponseBody
    public String makeAllHostKey(String data) {
        Type type = new TypeToken<PagingResult<MonitorConfigureEntity>>() {
        }.getType();
        PagingResult<MonitorConfigureEntity> entities = gson.fromJson(data, type);
        cacheController.makeAllHostKey(null, entities);
        return "ok";
    }


    /**
     * 设置异地机房配置文件信息
     *
     * @return
     */
    @RequestMapping("setConfigureCache")
    @ResponseBody
    public String setConfigureCache(String data) {
        Type type = new TypeToken<PagingResult<MonitorConfigureEntity>>() {
        }.getType();
        PagingResult<MonitorConfigureEntity> entities = gson.fromJson(data, type);
        cacheController.setConfigureCache(entities);
        return "ok";
    }

    /**
     * 设置异地机监控组信息
     *
     * @return
     */
    @RequestMapping("setGroupsCache")
    @ResponseBody
    public String setGroupsCache(String datas) {
        List list = gson.fromJson(datas, ArrayList.class);
        Type type = new TypeToken<PagingResult<MonitorGroupsEntity>>() {
        }.getType();
        PagingResult<MonitorGroupsEntity> entities = gson.fromJson(gson.toJson(list.get(0)), type);
        Map<String, String> map = gson.fromJson(gson.toJson(list.get(1)), HashMap.class);
        cacheController.setGroupsCache(entities, map);
        logger.info("获取到数据gson2" + new Gson().toJson(entities));

        return "ok";
    }

    /**
     * @param data
     * @return
     */
    @RequestMapping("setTemplateCache")
    @ResponseBody
    public String setTemplateCache(String data) {
        Type type = new TypeToken<PagingResult<MonitorTemplateEntity>>() {
        }.getType();
        PagingResult<MonitorTemplateEntity> entities = gson.fromJson(data, type);
        cacheController.setTemplateCache(entities);
        return "ok";
    }

}
