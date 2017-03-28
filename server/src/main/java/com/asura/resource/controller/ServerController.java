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
import com.asura.resource.entity.CmdbResourceCabinetEntity;
import com.asura.resource.entity.CmdbResourceEntnameEntity;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.entity.CmdbResourceOsTypeEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.entity.CmdbResourceServerTypeEntity;
import com.asura.resource.entity.CmdbResourceServiceEntity;
import com.asura.resource.entity.CmdbResourceUserEntity;
import com.asura.resource.entity.report.ServerReportEntity;
import com.asura.resource.service.CmdbResourceCabinetService;
import com.asura.resource.service.CmdbResourceEntnameService;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.resource.service.CmdbResourceOsTypeService;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.resource.service.CmdbResourceServerTypeService;
import com.asura.resource.service.CmdbResourceServiceService;
import com.asura.resource.service.CmdbResourceUserService;
import com.asura.util.DateUtil;
import com.asura.util.HttpClientIpAddress;
import com.asura.util.LdapAuthenticate;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import com.asura.util.network.ThreadPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.asura.util.RedisUtil.app;


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
    private LdapAuthenticate ldapAuthenticate;

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
    IndexController indexController;

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
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list(Model model,String groupsName, String typeName, String time, String isOff) {
        model.addAttribute("groupsName",groupsName);
        model.addAttribute("typeName",typeName);
        model.addAttribute("time",time);
        model.addAttribute("isOff",isOff);
        model = CommentController.getGroups(model,groupsService);
        model.addAttribute("username",userService.findAll(searchMapNull,PageResponse.getPageBounds(1000,1)).getRows());
        model.addAttribute("entname",entnameService.findAll(searchMapNull,PageResponse.getPageBounds(1000,1)).getRows());
        return "/resource/configure/server/list";
    }

    /**
     * 未添加到资源表的设备
     */
    @RequestMapping("noRecord")
    public String noRecord(){
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
        PageBounds pageBounds = PageResponse.getPageBounds(length,start);
        PagingResult<CmdbResourceServerEntity> result = service.findAll(searchMapNull,pageBounds,"selectNoRecord");
        return PageResponse.getMap(result, draw);
    }


    /**
     * 列表数据
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
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length, HttpServletRequest request,String hostIp,String entname, String groupsName, String typeName, String userName,String time, String isOff,String hosts) {
        PageBounds pageBounds ;
        if(hostIp==null){
            pageBounds = PageResponse.getPageBounds(length, start);
        }else{
            pageBounds = new PageBounds(1,1000);
        }
        SearchMap searchMap = new SearchMap();

        String search = request.getParameter("search[value]");
        if (search!=null&&search.length() > 2) {
            searchMap.put("search", search);
        }
        if(hostIp!=null&&hostIp.length()>2){
            searchMap.put("hostIp", hostIp);

        }
        if(typeName!=null&&typeName.length()>1){
            searchMap.put("typeName",typeName);
        }

        if(groupsName!=null&&groupsName.length()>1){
              searchMap.put("groupsName",groupsName);
        }

        if(userName!=null&&userName.length()>1){
            searchMap.put("userName",userName);
        }
        if(time!=null&&time.length()>1){
            searchMap.put("time",time);
        }
        if(entname!=null&&entname.length()>1){
            searchMap.put("entName",entname);
        }
        if(hosts!=null&&hosts.length()>1){
            String[] host = hosts.split(",");
            searchMap.put("hosts",host);
        }

        if(isOff!=null){
            searchMap.put("isOff",isOff);
        }
        PagingResult<CmdbResourceServerEntity> result;
        result = service.findAll(searchMap, pageBounds, "selectByAll");
        if(hostIp !=null && result.getTotal()<1){
            searchMap.remove("hostIp");
            searchMap.put("ipAddress",hostIp);
            result = service.findAll(searchMap, pageBounds, "selectByAll");
            if(result!=null && result.getTotal()>0){
                int hostId = result.getRows().get(0).getHostId();
                searchMap.remove("ipAddress");
                searchMap.put("vmHostId",hostId);
                result = service.findAll(searchMap,pageBounds, "selectByAll");
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
        model = getData(model);
        return "/resource/configure/server/add";
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
        if(entity.getHostIpAddress()!=null){
            SearchMap searchMap = new SearchMap();
            searchMap.put("ipAddress",entity.getHostIpAddress());
            List<CmdbResourceServerEntity> re = service.getDataList(searchMap,"selectByAll");
            if(re!=null){
                entity.setHostId(re.get(0).getServerId());
            }
        }

        if (entity.getServerId() != null && serverId == 0) {
            if(entity.getHostId()==0){
                entity.setHostId(entity.getServerId());
            }
            service.update(entity);
        } else {

            entity.setCreateUser(user);
            if(entity.getCreateUser()!=null){
                entity.setCreateUser(entity.getCreateUser());
            }
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        CmdbResourceServerEntity c = entity;
        Jedis jedis = new RedisUtil().getJedis();
        // 主机的id
        jedis.set(app + "_cache_hosts_id_"+c.getIpAddress() , c.getServerId()+"");
        // 主机的业务线
        jedis.set(app +"_" + MonitorCacheConfig.getCacheHostGroupsKey+c.getIpAddress() , c.getGroupsId()+"");
        // 获取每个id对应的ip地址
        jedis.set(app+"_"+MonitorCacheConfig.cacheHostIdToIp+ c.getServerId(), c.getIpAddress());
        jedis.close();
        indexController.logSave(request,"保存资产数据 " + entity.getIpAddress());
        return ResponseVo.responseOk(null);
    }

    /**
     * @param id
     *
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model, String detail) {
        CmdbResourceServerEntity result = service.findById(id, CmdbResourceServerEntity.class);
        model = getData(model);
        model.addAttribute("configs", result);

        SearchMap searchMap = new SearchMap();
        searchMap.put("serverId",result.getServerId());
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
     * @return
     */
    @RequestMapping(value = "getGroupsUsedNumber", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupsUsedNumber(int draw, int start, int length, String groupsIds) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (groupsIds!=null&&groupsIds.length()>0){
            String[] groups = groupsIds.split(",");
            searchMap.put("groups", groups);
        }
        List<CmdbResourceServerEntity> result = service.getDataList(searchMap,"getGroupsUsedNumber");
        return PageResponse.getList(result, draw);
    }

    /**
     * 删除资产数据
     * @return
     */
    @RequestMapping(value = "delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseVo delete(int id, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        if (user.length()<2){
            return ResponseVo.responseError("请登陆后操作");
        }
        String dept = ldapAuthenticate.getSignUserInfo("department", "sAMAccountName=" + user);
        CmdbResourceServerEntity resourceServerEntity = service.findById(id, CmdbResourceServerEntity.class);
        if (! user.equals("admin") && ! dept.contains("运维")){
            return ResponseVo.responseError("no permissions");
        }
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.del(MonitorCacheConfig.cacheHostIsUpdate+id);
        redisUtil.del(MonitorCacheConfig.hostsIdKey+id);
        redisUtil.del(MonitorCacheConfig.cacheHostIdToIp+id);
        service.delete(resourceServerEntity);
        indexController.logSave(request,"删除资产数据 " + gson.toJson(resourceServerEntity));
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
    public ArrayList setServerEntity(List<CmdbResourceServerEntity> result){
        ArrayList list = new ArrayList();
        ServerReportEntity entity;
        for(CmdbResourceServerEntity c:result){
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
     * @returncount
     */
    @RequestMapping(value = "countDataReport",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String countDataReport(String type, String entName){
        SearchMap searchMap = new SearchMap();
        if(entName!=null&&entName.length()>1) {
            searchMap.put("entName", entName);
        }
        List<CmdbResourceServerEntity> result = service.getDataList(searchMap,type);
        ArrayList list = setServerEntity(result);
        return gson.toJson(list);
    }

    /**
     * 探测服务器是否活着
     * @return
     */
    @RequestMapping("checkActive")
    @ResponseBody
    public String checkActive() throws Exception{
        if(!isCheck) {
            List<CmdbResourceServerEntity> result = service.getDataList(searchMapNull, "selectByAll");
            ArrayList ipList = new ArrayList();
            ArrayList upList = new ArrayList();
            ArrayList downList = new ArrayList();
            for (CmdbResourceServerEntity entity:result){
                ipList.add(entity.getIpAddress());
            }
            ThreadPing ping = new ThreadPing(ipList, upList, downList,255);
            ping.startPing();
            SearchMap map = new SearchMap();
            map.put("status", 1);
            map.put("hosts", upList);
            if (upList.size() > 0 ) {
                service.updatePing(map);
            }
            SearchMap downMap = new SearchMap();
            downMap.put("status", 0);
            downMap.put("hosts", downList);
            if (downList.size()>0) {
                service.updatePing(downMap);
            }
        }
        return "ok";
    }

    /**
     * 自动生成组5个组,每组1000个机器
     * @return
     */
    int autoMakeGroups(){
        String groupsName = "自动生成组1";
        SearchMap searchMap = new SearchMap();
        searchMap.put("groupsName", groupsName);
        PageBounds pageBounds = PageResponse.getPageBounds(3, 1);
        PagingResult<CmdbResourceGroupsEntity> result = groupsService.findAll(searchMap, pageBounds);
        if (result.getTotal() < 1) {
             for (int i = 1; i < 3 ; i++ ) {
                CmdbResourceGroupsEntity entity = new CmdbResourceGroupsEntity();
                entity.setCreateTime(DateUtil.getDateStampInteter());
                entity.setGroupsName("自动生成组"+i);
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
     * @return
     */
    int autoMakeOs(String os){
        SearchMap searchMap = new SearchMap();
        searchMap.put("os", os);
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        PagingResult<CmdbResourceOsTypeEntity> result = osTypeService.findAll(searchMap, pageBounds);
        if (result.getTotal() > 0 ){
            return result.getRows().get(0).getOsId();
        }else{
            CmdbResourceOsTypeEntity entity = new CmdbResourceOsTypeEntity();
            entity.setOsName(os);
            osTypeService.save(entity);
        }
        PagingResult<CmdbResourceOsTypeEntity> data = osTypeService.findAll(searchMap, pageBounds);
        return data.getRows().get(0).getOsId();
    }

    /**
     * 自动生成用户
     * @return
     */
    int autoMakeUser(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("username", "admin");
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        PagingResult<CmdbResourceUserEntity> result = userService.findAll(searchMap, pageBounds);
        if (result.getTotal() > 0 ){
            return result.getRows().get(0).getUserId();
        }else{
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
     * @return
     */
    int autoMakeEnt(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("entname", "自动环境");
        PagingResult<CmdbResourceEntnameEntity> result = entnameService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0 ){
            return result.getRows().get(0).getEntId();
        }else{
            CmdbResourceEntnameEntity entnameEntity = new CmdbResourceEntnameEntity();
            entnameEntity.setEntName("自动环境");
            entnameService.save(entnameEntity);
        }
        PagingResult<CmdbResourceEntnameEntity> data = entnameService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getEntId();
    }

    /**
     * 自动生产服务类型
     * @return
     */
    int autoMakeServerType(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("typeName", "未知");
        PagingResult<CmdbResourceServerTypeEntity> result = serverTypeService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0 ){
            return result.getRows().get(0).getTypeId();
        }else{
            CmdbResourceServerTypeEntity typeEntity = new CmdbResourceServerTypeEntity();
            typeEntity.setTypeName("未知");
            serverTypeService.save(typeEntity);
        }
        PagingResult<CmdbResourceServerTypeEntity> data = serverTypeService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getTypeId();
    }

    /**
     * 自动生产服务
     * @return
     */
    int autoMakeService(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("serviceName", "未知");
        PagingResult<CmdbResourceServiceEntity> result = serviceService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        if (result.getTotal() > 0 ){
            return result.getRows().get(0).getServiceId();
        }else{
            CmdbResourceServiceEntity serviceEntity = new CmdbResourceServiceEntity();
            serviceEntity.setServiceName("未知");
            serviceService.save(serviceEntity);
        }
        PagingResult<CmdbResourceServiceEntity> data = serviceService.findAll(searchMap, PageResponse.getPageBounds(2, 1));
        return data.getRows().get(0).getServiceId();
    }

    /**
     * 自动生产机房
     * @return
     */
    int autoMakeCabinet(){
        SearchMap searchMap = new SearchMap();
        searchMap.put("cabinetName", "未知");
        List<CmdbResourceCabinetEntity> result = cabinetService.selectCabinetName(searchMap, "selectCabinetName");
        if (result.size() > 0 ){
            return result.get(0).getCabinetId();
        }else{
            CmdbResourceCabinetEntity cabinetEntity = new CmdbResourceCabinetEntity();
            cabinetEntity.setCabinetName("未知");
            cabinetService.save(cabinetEntity);
        }
        List<CmdbResourceCabinetEntity> data = cabinetService.selectCabinetName(searchMap, "selectCabinetName");
        return data.get(0).getCabinetId();
    }


    /**
     * 自动上传cmdb数据
     * @param entity
     * @return
     */
    @RequestMapping("auto")
    @ResponseBody
    public ResponseVo autoCmdb(PushEntity entity, HttpServletRequest request){
        String ip = HttpClientIpAddress.getIpAddr(request);
        SearchMap searchMap = new SearchMap();
        searchMap.put("ipAddress", ip);
        List<CmdbResourceServerEntity> serverEntity = service.getDataList(searchMap, "selectServerid");
        if (serverEntity.size() > 0 ){
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
        resourceServerEntity.setUserId(autoMakeUser()+"");
        resourceServerEntity.setServiceId(autoMakeService()+"");
        resourceServerEntity.setTypeId(autoMakeServerType());
        resourceServerEntity.setDescription("自动上报");
        resourceServerEntity.setHostId(0);
        resourceServerEntity.setCabinetId(autoMakeCabinet());
        save(resourceServerEntity, request);
        return ResponseVo.responseOk("ok");
    }
}