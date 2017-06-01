package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.graph.controller.CommentController;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.util.MonitorUtil;
import com.asura.resource.configure.server.entity.CmdbResourceServerHistoryEntity;
import com.asura.resource.configure.server.service.CmdbResourceServerHistoryService;
import com.asura.resource.entity.*;
import com.asura.resource.entity.report.ServerReportEntity;
import com.asura.resource.service.*;
import com.asura.util.*;
import com.asura.util.network.ThreadPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 资源配置服务器添加配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/server/")
public class ServerController {
    @Autowired
    private CmdbResourceServerService service;

    @Autowired
    private CmdbResourceCabinetService cabinetService;

    @Autowired
    private CmdbResourceEntnameService entnameService;

    @Autowired
    private CmdbResourceGroupsService groupsService;

    @Autowired
    private CmdbResourceServerTypeService serverTypeService;

    @Autowired
    private CmdbResourceServiceService serviceService;

    @Autowired
    private CmdbResourceUserService userService;

    @Autowired
    private CmdbResourceOsTypeService osTypeService;

    @Autowired
    private CacheController cacheController;

    @Autowired
    private PermissionsCheck permissionsCheck;

    private Gson gson = new Gson();

    private SearchMap searchMapNull = new SearchMap();

    @Autowired
    private CmdbResourceInventoryService inventoryService;

    @Autowired
    IndexController indexController;


    @Autowired
    private CmdbResourceServerHistoryService historyService;

    // 检查标记，如果为true，就不执行检查代码，避免重复执行
    private static boolean isCheck = false;

    public Model getData(Model model) {
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000, 1);
        PagingResult<CmdbResourceGroupsEntity> groups = groupsService.findAll(searchMap, pageBounds);
        model.addAttribute("groups", groups.getRows());

        // 环境名称
        PagingResult<CmdbResourceEntnameEntity> entname = entnameService.findAll(searchMap, pageBounds);
        model.addAttribute("entname", entname.getRows());

        // 用户
        PagingResult<CmdbResourceUserEntity> user = userService.findAll(searchMap, pageBounds);
        model.addAttribute("user", user.getRows());

        // 机柜
        PagingResult<CmdbResourceCabinetEntity> cabinet = cabinetService.findAll(searchMap, pageBounds);
        model.addAttribute("cabinet", cabinet.getRows());

        // 操作系统类型
        PagingResult<CmdbResourceOsTypeEntity> os = osTypeService.findAll(searchMap, pageBounds);
        model.addAttribute("os", os.getRows());

        // 服务器类型
        PagingResult<CmdbResourceServerTypeEntity> types = serverTypeService.findAll(searchMap, pageBounds);
        model.addAttribute("types", types.getRows());

        // 服务类型
        PagingResult<CmdbResourceServiceEntity> services = serviceService.findAll(searchMap, pageBounds);
        model.addAttribute("service", services.getRows());

        return model;
    }


    /**
     * @param model
     * @param groupsName
     * @param typeName
     * @param time
     * @param isOff
     * @param inventoryId
     * @param t
     * @return
     */
    @RequestMapping("list")
    public String list(Model model, String groupsName, String typeName, String time, String isOff, String inventoryId, String t) {
        model.addAttribute("groupsName", groupsName);
        model.addAttribute("typeName", typeName);
        model.addAttribute("time", time);
        model.addAttribute("isOff", isOff);
        model.addAttribute("inventoryId", inventoryId);
        model.addAttribute("t", t);
        model = CommentController.getGroups(model, groupsService);
        model.addAttribute("username", userService.findAll(searchMapNull, PageResponse.getPageBounds(1000, 1)).getRows());
        model.addAttribute("entname", entnameService.findAll(searchMapNull, PageResponse.getPageBounds(1000, 1)).getRows());
        return "/resource/configure/server/list";
    }

    /**
     * 未添加到资源表的设备
     */
    @RequestMapping("noRecord")
    public String noRecord() {
        return "/resource/configure/server/noRecord";
    }

    /**
     * 列表数据
     *
     * @return
     */
    @RequestMapping(value = "noRecordData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String noRecordData(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<CmdbResourceServerEntity> result = service.findAll(searchMapNull, pageBounds, "selectNoRecord");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @param inventoryId
     * @param searchMap
     */
    void  getGrsoupsId(String inventoryId, SearchMap searchMap){
        String groupsId;
        // 通过库存获取组信息
        if (CheckUtil.checkString(inventoryId)) {
            if (inventoryId == null || inventoryId.equals("0")){
                groupsId = "";
                SearchMap inventoryMap = new SearchMap();
                PagingResult<CmdbResourceInventoryEntity> inventoryEntitys = inventoryService.findAll(inventoryMap, PageResponse.getPageBounds(10000,1), "selectByAll");
                for (CmdbResourceInventoryEntity entity: inventoryEntitys.getRows()){
                    groupsId += entity.getGroupsId()+",";
                }
                groupsId = groupsId.replace(",,",",");
            }else {
                CmdbResourceInventoryEntity inventoryEntity = inventoryService.findById(Integer.valueOf(inventoryId), CmdbResourceInventoryEntity.class);
                groupsId = inventoryEntity.getGroupsId();
            }
            searchMap.put("groups", groupsId.split(","));
        }
    }


    /**
     * 列表数据
     *
     * @param draw
     * @param start
     * @param length
     * @param request
     * @param hostIp
     * @param entname
     * @param groupsName
     * @param typeName
     * @param userName
     * @param time
     * @param isOff
     * @param inventoryId
     * @param t
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length, HttpServletRequest request,
                           String hostIp, String entname, String groupsName,
                           String typeName, String userName,
                           String time, String isOff, String hosts,
                           String inventoryId, String t,
                           String ipAddress
    ) {
        PageBounds pageBounds;
        if (hostIp == null) {
            pageBounds = PageResponse.getPageBounds(length, start);
        } else {
            pageBounds = new PageBounds(1, 1000);
        }

        SearchMap searchMap = new SearchMap();

        // 通过库存获取组信息
        getGrsoupsId(inventoryId, searchMap);
        if (CheckUtil.checkString(t)){
            searchMap.put("noGroups","1");
            switch (t){
                case "1":
                    searchMap.put("buyNumber", "1");
                    break;
                case "2":
                    searchMap.put("phyInventoryNumber", "1");
                    break;
                case "3":
                    searchMap.put("phyUsed", "1");
                    break;
                case "4" :
                    searchMap.put("phyVmNumber", "1");
                    break;
                case "5" :
                    searchMap.put("vmNumber", "1");
                    searchMap.remove("noGroups");
                    break;
                case "6" :
                    searchMap.put("fromInventory", "1");
                    break;
            }
        }

        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 2) {
            searchMap.put("search", search);
        }
        if (hostIp != null && hostIp.length() > 2) {
            searchMap.put("hostIp", hostIp);

        }
        if (CheckUtil.checkString(typeName)) {
            searchMap.put("typeName", typeName);
        }

        if (CheckUtil.checkString(groupsName)) {
            searchMap.put("groupsName", groupsName);
        }

        if (CheckUtil.checkString(userName)) {
            searchMap.put("userName", userName);
        }
        if (CheckUtil.checkString(time)) {
            searchMap.put("time", time);
        }
        if (CheckUtil.checkString(entname)) {
            searchMap.put("entName", entname);
        }
        if (CheckUtil.checkString(hosts)) {
            String[] host = hosts.split(",");
            searchMap.put("hosts", host);
        }
        if (CheckUtil.checkString(ipAddress)){
            searchMap.put("ipAddress", ipAddress);
        }

        if (isOff != null) {
            searchMap.put("isOff", isOff);
        }
        PagingResult<CmdbResourceServerEntity> result;
        result = service.findAll(searchMap, pageBounds, "selectByAll");
        if (hostIp != null && result.getTotal() < 1) {
            searchMap.remove("hostIp");
            searchMap.put("ipAddress", hostIp);
            result = service.findAll(searchMap, pageBounds, "selectByAll");
            if (result != null && result.getTotal() > 0) {
                int hostId = result.getRows().get(0).getHostId();
                searchMap.remove("ipAddress");
                searchMap.put("vmHostId", hostId);
                result = service.findAll(searchMap, pageBounds, "selectByAll");
            }

        }
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     *
     * @return
     */
    @RequestMapping("add")
    public String add(Model model) {
        getData(model);
        return "/resource/configure/server/add";
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("saveServer")
    @ResponseBody
    public ResponseVo saveServer(CmdbResourceServerEntity entity, HttpServletRequest request) {
        return save(entity, request);
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceServerEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        int serverId = 0;


        // 只有宿主机选择为空的时候修改宿主机为自己,还有判断不是修改的时候
        if (entity.getHostId() == 0 && entity.getServerId() == null) {
            // 获取最大ID

            CmdbResourceServerEntity max = service.selectMaxId();
            if (max == null) {
                serverId = 1;
            } else {
                serverId = max.getServerId() + 1;

            }
            entity.setServerId(serverId);
            entity.setHostId(serverId);
        }

        // 其他组织传的宿主机ip地址
        if (entity.getHostIpAddress() != null) {
            SearchMap searchMap = new SearchMap();
            searchMap.put("ipAddress", entity.getHostIpAddress());
            List<CmdbResourceServerEntity> re = service.getDataList(searchMap, "selectByAll");
            if (re != null) {
                entity.setHostId(re.get(0).getServerId());
            }
        }

        entity.setIpAddress(entity.getIpAddress().trim());

        if (entity.getServerId() != null && serverId == 0) {
            if (entity.getHostId() == 0) {
                entity.setHostId(entity.getServerId());
            }
            service.update(entity);
        } else {

            entity.setCreateUser(user);
            if (entity.getCreateUser() != null) {
                entity.setCreateUser(entity.getCreateUser());
            }
            entity.setCreateTime(System.currentTimeMillis() / 1000);
            service.save(entity);
        }

        String data = gson.toJson(entity);
        CmdbResourceServerHistoryEntity historyEntity = gson.fromJson(data, CmdbResourceServerHistoryEntity.class);
        historyService.save(historyEntity);
        // 主机的id
        RedisUtil redisUtil = new RedisUtil();
        cacheController.setServerInfoCache(service, serverId+"", null);
        redisUtil.set(MonitorCacheConfig.getCacheHostGroupsKey + entity.getIpAddress(), entity.getGroupsId() + "");
        redisUtil.set(MonitorCacheConfig.cacheHostIdToIp + entity.getServerId(), entity.getIpAddress());
        redisUtil.set(MonitorCacheConfig.hostsIdKey + entity.getIpAddress(), entity.getServerId() + "");
        indexController.logSave(request, "保存资产数据 " + entity.getIpAddress() + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }


    /**
     * 停止监控
     * @param serverId
     * @param stopTime
     */
    @RequestMapping("stopMonitorSave")
    @ResponseBody
    public String stopMonitor(String serverId, String stopTime, HttpServletRequest request){
        RedisUtil redisUtil = new RedisUtil();
        String key = MonitorCacheConfig.cacheStopServer.concat(serverId);
        redisUtil.del(key);
        redisUtil.setex(key,Integer.valueOf(stopTime), "1");
        indexController.logSave(request , "停止监控报警"+ serverId);
        return "ok";
    }

    /**
     * 获取监控停止还有多长时间过期
     * @param serverId
     */
    @RequestMapping(value = "getStopMonitorTime", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseVo getStopMonitorTime(String serverId){
        RedisUtil redisUtil = new RedisUtil();
        String key = MonitorCacheConfig.cacheStopServer.concat(serverId);
        String result =  redisUtil.get(key);
        if (CheckUtil.checkString(result)){
            Jedis jedis = redisUtil.getJedis();
            long value = jedis.ttl(RedisUtil.app+"_"+key);
            String data =  MonitorUtil.getStopMonitorTime(value);
            return ResponseVo.responseOk(data);
        }
            return ResponseVo.responseOk("监控正常报警中");
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model, String detail) {
        CmdbResourceServerEntity result = service.findById(id, CmdbResourceServerEntity.class);
        model = getData(model);
        model.addAttribute("configs", result);
        String buyUserId = result.getBuyUser();
        if (CheckUtil.checkString(buyUserId) && !buyUserId.trim().equals("0")) {
            CmdbResourceGroupsEntity groupsEntity = groupsService.findById(Integer.valueOf(buyUserId), CmdbResourceGroupsEntity.class);
            model.addAttribute("buyUserId", groupsEntity.getGroupsId());
            result.setBuyUser(groupsEntity.getGroupsName());
        }
        String useUserId = result.getUseUser();
        if (CheckUtil.checkString(useUserId) && !useUserId.trim().equals("0")) {
            CmdbResourceGroupsEntity groupsEntity = groupsService.findById(Integer.valueOf(useUserId), CmdbResourceGroupsEntity.class);
            model.addAttribute("useUserId", groupsEntity.getGroupsId());
            result.setUseUser(groupsEntity.getGroupsName());
        }

        SearchMap searchMap = new SearchMap();
        searchMap.put("serverId", result.getServerId());
        // 宿主机的信息
        java.util.List<CmdbResourceServerEntity> hosts = service.findHosts(searchMap);
        model.addAttribute("hosts", hosts);

        if (detail != null) {
            return "/resource/configure/server/detail";
        } else {
            return "/resource/configure/server/add";

        }
    }


    /**
     * 列表数据
     *
     * @return
     */
    @RequestMapping(value = "getGroupsUsedNumber", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupsUsedNumber(int draw, int start, int length, String groupsIds) {
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(groupsIds)) {
            String[] groups = groupsIds.split(",");
            searchMap.put("groups", groups);
        }
        List<CmdbResourceServerEntity> result = service.getDataList(searchMap, "getGroupsUsedNumber");
        return PageResponse.getList(result, draw);
    }

    /**
     * 删除资产数据
     *
     * @return
     */
    @RequestMapping(value = "deleteSave", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseVo delete(int id, HttpServletRequest request) {
        CmdbResourceServerEntity resourceServerEntity = service.findById(id, CmdbResourceServerEntity.class);
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.del(MonitorCacheConfig.cacheHostIsUpdate + id);
        redisUtil.del(MonitorCacheConfig.hostsIdKey + id);
        redisUtil.del(MonitorCacheConfig.cacheHostIdToIp + id);
        service.delete(resourceServerEntity);
        indexController.logSave(request, "删除资产数据 " + gson.toJson(resourceServerEntity));
        cacheController.cacheGroups(groupsService, service);
        return ResponseVo.responseOk(null);
    }

    /**
     * 获取可以用的机柜位置
     */
    @RequestMapping(value = "selectUseCabinetLevel", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String selectUseCabinetLevel(int cabinetId) {
        SearchMap map = new SearchMap();
        map.put("cabinetId", cabinetId);

        // 已经使用了的ID
        List<CmdbResourceServerEntity> result = service.getDataList(map, "selectUseCabinetLevel");

        if (result == null) {
            return "";
        }

        // 获取可用的数量
        CmdbResourceCabinetEntity cabinet = cabinetService.findById(cabinetId, CmdbResourceCabinetEntity.class);

        ArrayList data = new ArrayList();
        for (int i = 1; i <= cabinet.getNumber(); i++) {
            boolean is = false;
            for (CmdbResourceServerEntity c : result) {
                if (c.getCabinetLevel() == i) {
                    is = true;
                }
            }
            if (!is) {
                data.add(i);
            }
        }

        return gson.toJson(data);
    }

    /**
     * 按
     */
    public ArrayList setServerEntity(List<CmdbResourceServerEntity> result) {
        ArrayList list = new ArrayList();
        ServerReportEntity entity;
        for (CmdbResourceServerEntity c : result) {
            entity = new ServerReportEntity();
            entity.setName(c.getTypeName());
            entity.setY(c.getCnt());
            list.add(entity);
        }
        return list;
    }


    /**
     * 各种类型统计公用
     * countEntName
     * countGroupsName
     * countServerType
     * countDiskSize
     * counterMemory
     * countService
     * 按环境
     *
     * @returncount
     */
    @RequestMapping(value = "countDataReport", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String countDataReport(String type, String entName, String inventoryId) {
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(entName)) {
            searchMap.put("entName", entName);
        }
        getGrsoupsId(inventoryId, searchMap);
        List<CmdbResourceServerEntity> result = service.getDataList(searchMap, type);
        ArrayList list = setServerEntity(result);
        return gson.toJson(list);
    }

    /**
     * 探测服务器是否活着
     *
     * @return
     */
    @RequestMapping("checkActive")
    @ResponseBody
    public String checkActive() throws Exception {
        if (!isCheck) {
            List<CmdbResourceServerEntity> result = service.getDataList(searchMapNull, "selectByAll");
            ArrayList ipList = new ArrayList();
            ArrayList upList = new ArrayList();
            ArrayList downList = new ArrayList();
            for (CmdbResourceServerEntity entity : result) {
                ipList.add(entity.getIpAddress());
            }
            ThreadPing ping = new ThreadPing(ipList, upList, downList, 255);
            ping.startPing();
            SearchMap map = new SearchMap();
            map.put("status", 1);
            map.put("hosts", upList);
            if (upList.size() > 0) {
                service.updatePing(map);
            }
            SearchMap downMap = new SearchMap();
            downMap.put("status", 0);
            downMap.put("hosts", downList);
            if (downList.size() > 0) {
                service.updatePing(downMap);
            }
        }
        return "ok";
    }

    /**
     * 自动生成组5个组,每组1000个机器
     *
     * @return
     */
    int autoMakeGroups() {
        String groupsName = "自动生成组1";
        SearchMap searchMap = new SearchMap();
        searchMap.put("groupsName", groupsName);
        PageBounds pageBounds = PageResponse.getPageBounds(3, 1);
        PagingResult<CmdbResourceGroupsEntity> result = groupsService.findAll(searchMap, pageBounds);
        if (result.getTotal() < 1) {
            for (int i = 1; i < 3; i++) {
                CmdbResourceGroupsEntity entity = new CmdbResourceGroupsEntity();
                entity.setCreateTime(DateUtil.getDateStampInteter());
                entity.setGroupsName("自动生成组" + i);
                groupsService.save(entity);
            }
        }
        Random random = new Random();
        PagingResult<CmdbResourceGroupsEntity> data = groupsService.findAll(searchMap, pageBounds);
        int rand = random.nextInt((int) data.getTotal());
        return data.getRows().get(rand).getGroupsId();
    }

    /**
     * 生成系统类型
     *
     * @return
     */
    int autoMakeOs(String os) {
        SearchMap searchMap = new SearchMap();
        searchMap.put("os", os);
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        PagingResult<CmdbResourceOsTypeEntity> result = osTypeService.findAll(searchMap, pageBounds);
        if (result.getTotal() > 0) {
            return result.getRows().get(0).getOsId();
        } else {
            CmdbResourceOsTypeEntity entity = new CmdbResourceOsTypeEntity();
            entity.setOsName(os);
            osTypeService.save(entity);
        }
        PagingResult<CmdbResourceOsTypeEntity> data = osTypeService.findAll(searchMap, pageBounds);
        return data.getRows().get(0).getOsId();
    }

    /**
     * 自动生成用户
     *
     * @return
     */
    int autoMakeUser() {
        SearchMap searchMap = new SearchMap();
        searchMap.put("username", "admin");
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        PagingResult<CmdbResourceUserEntity> result = userService.findAll(searchMap, pageBounds);
        if (result.getTotal() > 0) {
            return result.getRows().get(0).getUserId();
        } else {
            CmdbResourceUserEntity entity = new CmdbResourceUserEntity();
            entity.setUserName("admin");
            entity.setGroupsId(autoMakeGroups());
            userService.save(entity);
        }
        PagingResult<CmdbResourceUserEntity> data = userService.findAll(searchMap, pageBounds);
        return data.getRows().get(0).getUserId();
    }

    /**
     * 自动生产环境
     *
     * @return
     */
    int autoMakeEnt() {
        SearchMap searchMap = new SearchMap();
        searchMap.put("entname", "自动环境");
        PagingResult<CmdbResourceEntnameEntity> result = entnameService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0) {
            return result.getRows().get(0).getEntId();
        } else {
            CmdbResourceEntnameEntity entnameEntity = new CmdbResourceEntnameEntity();
            entnameEntity.setEntName("自动环境");
            entnameService.save(entnameEntity);
        }
        PagingResult<CmdbResourceEntnameEntity> data = entnameService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getEntId();
    }

    /**
     * 自动生产服务类型
     *
     * @return
     */
    int autoMakeServerType() {
        SearchMap searchMap = new SearchMap();
        searchMap.put("typeName", "未知");
        PagingResult<CmdbResourceServerTypeEntity> result = serverTypeService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0) {
            return result.getRows().get(0).getTypeId();
        } else {
            CmdbResourceServerTypeEntity typeEntity = new CmdbResourceServerTypeEntity();
            typeEntity.setTypeName("未知");
            serverTypeService.save(typeEntity);
        }
        PagingResult<CmdbResourceServerTypeEntity> data = serverTypeService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getTypeId();
    }

    /**
     * 自动生产服务
     *
     * @return
     */
    int autoMakeService() {
        SearchMap searchMap = new SearchMap();
        searchMap.put("serviceName", "未知");
        PagingResult<CmdbResourceServiceEntity> result = serviceService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0) {
            return result.getRows().get(0).getServiceId();
        } else {
            CmdbResourceServiceEntity serviceEntity = new CmdbResourceServiceEntity();
            serviceEntity.setServiceName("未知");
            serviceService.save(serviceEntity);
        }
        PagingResult<CmdbResourceServiceEntity> data = serviceService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getServiceId();
    }

    /**
     * 自动生产机房
     *
     * @return
     */
    int autoMakeCabinet() {
        SearchMap searchMap = new SearchMap();
        searchMap.put("cabinetName", "未知");
        List<CmdbResourceCabinetEntity> result = cabinetService.selectCabinetName(searchMap, "selectCabinetName");
        if (result.size() > 0) {
            return result.get(0).getCabinetId();
        } else {
            CmdbResourceCabinetEntity cabinetEntity = new CmdbResourceCabinetEntity();
            cabinetEntity.setCabinetName("未知");
            cabinetService.save(cabinetEntity);
        }
        List<CmdbResourceCabinetEntity> data = cabinetService.selectCabinetName(searchMap, "selectCabinetName");
        return data.get(0).getCabinetId();
    }


    /**
     * 自动上传cmdb数据
     *
     * @param entity
     * @return
     */
    @RequestMapping("auto")
    @ResponseBody
    public ResponseVo autoCmdb(PushEntity entity, HttpServletRequest request) {
        String ip = HttpClientIpAddress.getIpAddr(request);
        SearchMap searchMap = new SearchMap();
        searchMap.put("ipAddress", ip);
        List<CmdbResourceServerEntity> serverEntity = service.getDataList(searchMap, "selectServerid");
        if (serverEntity.size() > 0) {
            return ResponseVo.responseOk("ok");
        }
        CmdbResourceServerEntity resourceServerEntity = new CmdbResourceServerEntity();
        resourceServerEntity.setGroupsId(autoMakeGroups());
        resourceServerEntity.setCpu(entity.getCpu());
        resourceServerEntity.setDiskSize(entity.getDisk());
        resourceServerEntity.setStatus(1);
        resourceServerEntity.setIpAddress(ip);
        resourceServerEntity.setOsId(autoMakeOs(entity.getOs()));
        resourceServerEntity.setEntId(autoMakeEnt());
        resourceServerEntity.setUserId(autoMakeUser() + "");
        resourceServerEntity.setServiceId(autoMakeService() + "");
        resourceServerEntity.setTypeId(autoMakeServerType());
        resourceServerEntity.setDescription("自动上报");
        resourceServerEntity.setHostId(0);
        resourceServerEntity.setCabinetId(autoMakeCabinet());
        save(resourceServerEntity, request);
        return ResponseVo.responseOk("ok");
    }
}