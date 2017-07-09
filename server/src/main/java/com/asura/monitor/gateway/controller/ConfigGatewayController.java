package com.asura.monitor.gateway.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.configure.entity.*;
import com.asura.monitor.configure.service.*;
import com.asura.monitor.gateway.entity.MonitorGatewayEntity;
import com.asura.monitor.gateway.service.MonitorGatewayService;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.HttpUtil;
import com.asura.util.PermissionsCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跨机房网关配置
 * Created by zhaoyun on 2017/6/17.
 */
@Controller
@RequestMapping("/monitor/configure/gateway")
public class ConfigGatewayController {

    @Autowired
    private MonitorGatewayService gatewayService;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private IndexController logSave;

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
    private CacheController cacheController;

    private Logger logger = LoggerFactory.getLogger(ConfigGatewayController.class);

    /**
     *
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/monitor/configure/gateway/list";
    }

    /**
     *
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model){
        if (id > 0){
            MonitorGatewayEntity gatewayEntity = gatewayService.findById(id, MonitorGatewayEntity.class);
            model.addAttribute("configs", gatewayEntity);
        }
        return "/monitor/configure/gateway/add";
    }

    /**
     * @param draw
     * @param start
     * @param length
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length, HttpServletRequest request) {
        String search = request.getParameter("search[value]");
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(search)){
            searchMap.put("key", search);
        }
        PagingResult<MonitorGatewayEntity> result = gatewayService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @param entity
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(MonitorGatewayEntity entity, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        entity.setLastModifyUser(user);
        if (entity.getGatewayId() > 0){
            gatewayService.update(entity);
        }else{
            gatewayService.save(entity);
        }
        logSave.logSave(request,"保存跨机房服务器信息"  + new Gson().toJson(entity));
        return ResponseVo.responseOk("ok");
    }

    /**
     *
     * @param type
     * @param floorId
     * @param map
     * @return
     */
    void getSendData(String type, int floorId, Map<String, String> map){
        SearchMap searchMap = new SearchMap();
        searchMap.put("floorId", floorId);
        searchMap.put("cache", 1);
        Gson gson = new Gson();
        String data = "";
        switch (type) {
            case "setServerToRedis": //ok
                List<CmdbResourceServerEntity> cmdbResourceServerEntities = service.getDataList(searchMap, "selectByAll");
                data = gson.toJson(cmdbResourceServerEntities);
                break;
            case "setMonitorItemToRedis": // ok
                List<MonitorItemEntity> itemEntities = itemService.getDataList(searchMap, "selectByAll");
                data = gson.toJson(itemEntities);
                break;
            case "setMonitorScriptsToRedis": // ok
                List<MonitorScriptsEntity> scriptsEntities = scriptsService.getDataList(searchMap, "selectByAll");
                data = gson.toJson(scriptsEntities);
                break;
            case "setContactGroupCacheToRedis": //ok
                PagingResult<MonitorContactGroupEntity> contactGroupEntityPagingResult = contactGroupService.findAll(searchMap,PageResponse.getPageBounds(1000000,1), "selectByAll");
                data = gson.toJson(contactGroupEntityPagingResult);
                break;
            case "makeAllHostKey":
                PagingResult<CmdbResourceServerEntity> serverEntityPagingResult = service.findAll(searchMap, PageResponse.getPageBounds(1000000, 1), "selectByAll");
                data =  gson.toJson(serverEntityPagingResult);
                break;
            case "setConfigureCache":
                PagingResult<MonitorConfigureEntity> configureEntityPagingResult = configureService.findAll(searchMap,PageResponse.getPageBounds(1000000,1), "selectByAll");
                data = gson.toJson(configureEntityPagingResult);
                break;
            case "setTemplateCache": //ok
                PagingResult<MonitorTemplateEntity> templateEntityPagingResult = templateService.findAll(searchMap,PageResponse.getPageBounds(1000000,1), "selectByAll");
                data = gson.toJson(templateEntityPagingResult);
                break;
            case "setGroupsCache": //ok
                PagingResult<MonitorGroupsEntity> groupsEntityPagingResult = groupsService.findAll(searchMap,PageResponse.getPageBounds(1000000,1), "selectByAll");
                String hosts;
                List list = new ArrayList();
                Map groupHostMap = new HashMap();
                for (MonitorGroupsEntity entity:groupsEntityPagingResult.getRows()) {
                    hosts = cacheController.getHosts(entity);
                    groupHostMap.put(entity.getGroupsId(), hosts);
                }
                list.add(groupsEntityPagingResult);
                list.add(groupHostMap);
                data = gson.toJson(list);
                break;
            case "setContacCacheToRedis": // ok
                PagingResult<MonitorContactsEntity> contactsEntityPagingResult = contactsService.findAll(searchMap,PageResponse.getPageBounds(1000000,1), "selectByAll");
                data = gson.toJson(contactsEntityPagingResult);
                break;
        }
        map.put(type, data);
    }

    /**
     * 给某个机房生产缓冲
     * @param floorId
     * @return
     */
    @RequestMapping("cache")
    @ResponseBody
    public ResponseVo cache(int floorId, String type){
        SearchMap searchMap = new SearchMap();
        searchMap.put("floorId", floorId);
        List<MonitorGatewayEntity> data = gatewayService.getListData(searchMap, "selectByAll");
        String url;
        Map<String,String> dataMap = new HashMap();
        getSendData(type, floorId, dataMap);
        for (MonitorGatewayEntity entity:data){
            url = "http://{0}:{1}/monitor/gateway/api/".concat(type);
            url = url.replace("{0}", entity.getIpAddress());
            url = url.replace("{1}", entity.getPort());
            logger.info(url);
            logger.info("data=" + dataMap.get(type));
            HttpUtil.sendPost(url, "data="+ dataMap.get(type));
        }
        return ResponseVo.responseOk("ok");
    }
}