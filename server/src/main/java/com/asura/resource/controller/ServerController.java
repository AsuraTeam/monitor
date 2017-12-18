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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import sun.misc.Cache;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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

    private Logger logger = LoggerFactory.getLogger(ServerController.class);

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

    private static boolean isCheck = false;

    private final String colors = "#afc68b,#eccfa4,#8786a2,#a5dcb2,#c4cef8,#a9b380,#d9a3c3,#e5c1c2,#e080c9,#d8c5a7,#c7bdb5,#9992fb,#9f8ac2,#e0e3f5,#dbfcf8,#aae3a9,#e0b28a,#dbd692,#89e0db,#f684bf,#c191b3,#8edb89,#c4e6bb,#d4c1fa,#8b8bb4,#8df3c3,#9c81a3,#facbc6,#9cab83,#85aca9,#b3aad2,#d18bb9,#abb8d8,#d3a4bd,#db8add,#a9aee1,#dd9df9,#d3f0c9,#d2b698,#ce9eec,#c8cfef,#b3b884,#98d7b5,#d0aaf9,#a9f4df,#cfedd9,#ebcaf1,#d79bd1,#b18980,#b09eb2,#e8daf8,#92a6e8,#b09d8e,#ce94f2,#e0e29c,#f1e2f8,#bacde6,#9bc0cc,#cf97b0,#9ec9ef,#9cf5e2,#f4e1b1,#88ceb9,#cff5ae,#ed8fc3,#af9d94,#8fb1fa,#cee797,#81f5a9,#fd93e8,#9380f2,#f1e199,#94a1d5,#a38680,#af9c8b,#a489a4,#e5e689,#96818c,#94b8dc,#deb6cf,#8fcd89,#e98da5,#909d92,#e6dab9,#84a086,#d79eb3,#8a99e3,#ddd6b0,#c7ad9f,#cebe87,#a1e1a2,#8c82e4,#c7c9ea,#facf9f,#fac2c3,#fda697,#c98fb2,#bf85de,#aaefa3,#928cc8,#d99fe7,#96b293,#d38cdb,#d4a29d,#f4cbad,#d4c2cb,#a4e2b0,#e6f094,#8bbcfd,#cfe683,#cde8eb,#87f0b5,#f694c9,#aed9e3,#b5a8c7,#bee2d7,#8f8998,#84ecc0,#adf2ba,#bacdd3,#faa7e8,#bdf2c8,#8ba196,#919fc9,#f5c8cc,#bca0cf,#9b9fa4,#80d7dc,#e7bd9e,#cfc7af,#cfc2fc,#b4ea86,#88a18a,#89a6d0,#cb98cc,#b4caa6,#c7bbe7,#fcbdb4,#e6b9a8,#879cf9,#f4daee,#e3c38e,#c98cdb,#cf9a86,#afde8e,#ceb9fe,#e2d4c4,#9ab2b3,#d18a9f,#f185a2,#f8a2b2,#dae1f1,#c1fdf2,#8da5d5,#dad1a9,#c7cdb8,#cdadfe,#e7938e,#ae8df2,#80dbd9,#ef9984,#8cb3e6,#b3cfac,#97d283,#c883de,#e7f6f2,#cb95bd,#c49980,#d5d8f8,#90eef7,#bcc5ab,#9298d1,#b4e2e2,#9dfcc5,#8b8eb6,#daa7d6,#ec89fb,#88cbd4,#e0e5ad,#9dc8ea,#a9b8ce,#fe80aa,#f4fa88,#bacbaf,#8acc8c,#ba86bd,#94ae8a,#cec795,#92c7ae,#a0b4d3,#83fdec,#fb9afd,#cdef9e,#94fba9,#95f994,#a1dddb,#bcf9f5,#d2e997,#92b1c0,#c385a8,#a2c9a2,#a7eca4,#ecd0cc,#93b487,#9d9ded,#c9c5af,#86ebd3,#b89fb4,#e9dfb2,#b194ef,#b2d7c5,#bbcca3,#ea9c90,#e2abad,#bc9d9c,#d4e5e6,#fc94a8,#b7cdbf,#9890e5,#9db3aa,#88eef6,#d0fba4,#84f6d6,#f7e1bf,#83a5ea,#ae97d3,#b0e296,#fc8bdb,#f0a7df,#c9d8ef,#b6adb3,#e9fec3,#e2fefa,#e194a4,#d989f8,#d2cf90,#91c9d3,#cfdbbf,#bb9bb0,#9bdef0,#e2dad2,#deccf9,#ba9f9e,#d6a5f8,#cdc587,#93cce8,#f8c8bc,#e080f9,#d4e3fb,#a0c3f5,#f6c3bb,#fd8787,#e48ded,#f5e996,#dac48d,#82f4b5,#f79bc1,#a0f7d0,#b1aa8d,#8dd39b,#f4e4b4,#fef2e3,#e6d8ea,#afe9ec,#b0cddc,#93f7bf,#b9c4da,#88c9f8,#e5eff0,#dec6a9,#b6bbf7,#a987ba,#d5a8d0,#fcb6ae,#a3bee8,#b8b4d9,#c99ccb,#aff8cc,#d3fbf4,#aae7fe,#d1dcdd,#93dcc0,#b7edea,#cda8ca,#c2bfcf,#f6e5d4,#b09cac,#e0df96,#d3f98f,#fedc83,#939bcc,#bae2dc,#b2e5d7,#b8b898,#9ac9f1,#f3a2b3,#9bc3c1,#c3b5cb,#de978c,#98f192,#8bc1c5,#f4c5dd,#a9d5c2,#99d2ea,#cd8496,#b0bef6,#afe9f7,#90b5c7,#b59afe,#e59d94,#ece2f5,#ece5d4,#9fdb92,#85acfa,#efbde9,#a5f1be,#f4899f,#ace89a,#b8a4d2,#d499e4,#f1d9ea,#dab4e3,#89a4de,#87989c,#bab5dc,#8a98d7,#8de2d3,#f4b6c0,#9fe092,#ef87f8,#ab9ac5,#a3a992,#95a6bd,#efd399,#83fafc,#f7a0fe,#aaf1d3,#b9c9a8,#fedeba,#f398e3,#a3d5d3,#b4abf8,#9ca187,#e7a3f0,#e9d0de,#fc91f6,#b1b8c6,#cda0d2,#d3a1c0,#9ddf88,#bcc2ca,#fdf691,#95d2aa,#c8a481,#d2b588,#eba6fc,#d5d5ba,#8ba8d6,#aa9cde,#fde78a,#deeae2,#82eba2,#b1f1b9,#eda19c,#baede9,#cfdda1,#dae6e4,#a496c8,#8afef5,#a7ecd9,#e48cfc,#98f793,#9eddd4,#efb1c4,#fccb86,#e3c3d5,#eda4c6,#839ab5,#a0f397,#c3ca8f,#ceda92,#db9db8,#95a7e2,#b2e8b0,#91bf96,#e9ae87,#b2b992,#c980b6,#98ecae,#cbf5aa,#f1c8f3,#85dacb,#acaa9d,#fbb898,#f1ddf4,#d5d2a5,#98f3e4,#e4b3e3,#e5bcce,#dbd582,#a7cf94,#9795a3,#bac1a8,#f0cdb4,#e18cfd,#dfada5,#dcdbe7,#abd5a1,#f6bcd1,#c9a0d9,#ec80d0,#bddef9,#fcf7e0,#c8c586,#fe9eb3,#a9ddaa,#e6e9dd,#9095f5,#a3e7d2,#c7f0fe,#e6e1cc,#cee19c,#99d5eb,#b2eed4,#d8a9f4,#e48681";


    /**
     *
     * @param modelMap
     * @param ip
     * @return
     */
    @RequestMapping("top")
    public String top(ModelMap modelMap, String ip,String groupsName){
        modelMap.put("ip",ip);
        modelMap.put("groupsName", groupsName);
        return "/resource/configure/server/top";
    }

    /**
     *
     * @param entity
     * @return
     */
    TopEntity getData(CmdbResourceServerEntity entity){
        TopEntity topEntity = new TopEntity();
        topEntity.setTypename(entity.getTypeName());
        topEntity.setEntname(entity.getEntName());
        topEntity.setUsername(entity.getUserName());
        topEntity.setCabinet(entity.getCabinetName());
        topEntity.setSwitchIp(entity.getSwitchId());
        topEntity.setDomainName(entity.getDomainName());
        return topEntity;
    }

    /**
     * top图数据
     * @param ip
     * @return
     */
    @RequestMapping(value = "topData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String topData(String ip, String groupsName){
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(ip)){
            searchMap.put("ip", ip);
        }
        if (CheckUtil.checkString(groupsName)){
            searchMap.put("groupsName", groupsName);
        }
        String[] colorData = colors.split(",");
        Map<String,String> switchInfo = new HashMap();
        Map<String, HashSet<String>> switchPortMap = new HashMap();
        Map<String,String> switchPortColor = new HashMap<>();
        Map<Integer, String> existsMap = new HashMap();
        ArrayList list = new ArrayList();
        HashSet tempHashSet ;
        TopEntity topEntity;
        Random random = new Random();
        List<CmdbResourceServerEntity> serverEntities = service.getDataList(searchMap, "selectSwitchTop");
        List<CmdbResourceServerEntity> selectVmTops = service.getDataList(searchMap, "selectVmTop");

        if (searchMap.containsKey("groupsName")){
            ArrayList vmName = new ArrayList();
            for (CmdbResourceServerEntity entity:selectVmTops){
                vmName.add(entity.getIpAddress());
            }
            searchMap.remove("groupsName");
            searchMap.put("vms", vmName);
            List<CmdbResourceServerEntity> serverEntitiesVm = service.getDataList(searchMap, "selectSwitchTop");
            for (CmdbResourceServerEntity entity:serverEntitiesVm){
                serverEntities.add(entity);
            }
            List<CmdbResourceServerEntity> hostIds = service.getDataList(searchMap, "selectTopHostsId");
            ArrayList hosts = new ArrayList();
            for (CmdbResourceServerEntity entity:hostIds){
                hosts.add(entity.getHostId());
            }
            searchMap.remove("vms");
            searchMap.put("hostId", hosts);
            List<CmdbResourceServerEntity> hostServer = service.getDataList(searchMap, "selectSwitchTop");
            for (CmdbResourceServerEntity entity:hostServer){
                serverEntities.add(entity);
            }
        }

        if(searchMap.containsKey("ip")){
            // 查询宿主机或虚拟机的交换机地址
            List<CmdbResourceServerEntity> selectSwitchIp = service.getDataList(searchMap, "selectSwitchIp");
            if (selectSwitchIp.size() > 0){
                searchMap.put("ip", selectSwitchIp.get(0).getSwitchId());
                List<CmdbResourceServerEntity>  serverEntities2 = service.getDataList(searchMap, "selectSwitchTop");
                for (CmdbResourceServerEntity entity:serverEntities2){
                    searchMap.put("ip",entity.getSwitchId());
                    serverEntities.add(entity);
                }
                selectSwitchIp = service.getDataList(searchMap, "selectSwitchIp2");
                if (selectSwitchIp.size() > 0) {
                    searchMap.put("ip", selectSwitchIp.get(0).getSwitchId());
                    List<CmdbResourceServerEntity> serverEntitiesS = service.getDataList(searchMap, "selectSwitchTop");
                    for (CmdbResourceServerEntity entity : serverEntitiesS) {
                        serverEntities.add(entity);
                        searchMap.put("ip", entity.getSwitchId());
                    }
                    serverEntitiesS = service.getDataList(searchMap, "selectSwitchTop");
                    for (CmdbResourceServerEntity entity : serverEntitiesS) {
                        serverEntities.add(entity);
                    }
                }
            }
        }

        for (CmdbResourceServerEntity entity:serverEntities){
            switchInfo.put(entity.getSwitchId(), entity.getK2());
            if (switchPortMap.containsKey(entity.getSwitchId())){
                tempHashSet = switchPortMap.get(entity.getSwitchId());
            }else {
                tempHashSet = new HashSet();
            }
            tempHashSet.add(entity.getSwitchPort());
            switchPortMap.put(entity.getSwitchId(), tempHashSet);
            switchPortColor.put(entity.getSwitchId(), colorData[random.nextInt(colorData.length)]);
            switchPortColor.put(entity.getIpAddress(),colorData[random.nextInt(colorData.length)]);
        }


        // 设置虚拟机对应对物理机信息
        for (CmdbResourceServerEntity entity:selectVmTops){
            topEntity =getData(entity);
            topEntity.setIp(entity.getIpAddress());
            topEntity.setKey(Integer.valueOf(entity.getK1()));
            topEntity.setParent(Integer.valueOf(entity.getK2()));
            topEntity.setColor(switchPortColor.get(entity.getHostIpAddress()));
            if (existsMap.containsKey(topEntity.getKey())){
                continue;
            }
            existsMap.put(topEntity.getKey(),"1");
            list.add(topEntity);
        }

        // 生成服务器的信息
        for (CmdbResourceServerEntity entity:serverEntities){
            topEntity = getData(entity);
            topEntity.setIp(entity.getIpAddress());
            topEntity.setKey(Integer.valueOf(entity.getK1()));
            // 服务器的交换机地址
            topEntity.setParent(Integer.valueOf(entity.getK1()+entity.getK2()));
            if (existsMap.containsKey(topEntity.getKey())){
                continue;
            }
            existsMap.put(topEntity.getKey(),"1");
            list.add(topEntity);
        }


        // 生产交换机端口的
        for (CmdbResourceServerEntity entity:serverEntities){
            topEntity =getData(entity);
            topEntity.setKey(Integer.valueOf(entity.getK1()+entity.getK2()));
            topEntity.setPort(entity.getSwitchPort());
            topEntity.setColor(switchPortColor.get(entity.getSwitchId()));
            // 服务器的交换机地址
            topEntity.setParent(Integer.valueOf(entity.getK2()));
            if (existsMap.containsKey(topEntity.getKey())){
                continue;
            }
            existsMap.put(topEntity.getKey(),"1");
            list.add(topEntity);
        }
        return new Gson().toJson(list);
    }

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


        // 服务类型
        searchMap.put("types", "交换机");
        PagingResult<CmdbResourceServerEntity> switchs  = service.findAll(searchMap, pageBounds, "selectByAll");
        model.addAttribute("switchs", switchs.getRows());
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
    public String list(Model model, String groupsName, String typeName, String time, String isOff, String inventoryId, String t, String domain) {
        model.addAttribute("groupsName", groupsName);
        model.addAttribute("typeName", typeName);
        model.addAttribute("time", time);
        model.addAttribute("isOff", isOff);
        model.addAttribute("inventoryId", inventoryId);
        model.addAttribute("t", t);
        model.addAttribute("domain", domain);
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
     * 域名信息统计
     */
    @RequestMapping("domain")
    public String domain(Model model) {
        getData(model);
        return "/resource/configure/server/domain";
    }

    /**
     * 列表数据
     *
     * @return
     */
    @RequestMapping(value = "selectServerDomain", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectServerDomain(int draw, int start, int length, String  entName, String groupsName, HttpServletRequest request) {
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(entName)){
            searchMap.put("entName", entName);
        }
        if (CheckUtil.checkString(groupsName)){
            searchMap.put("groupsName", groupsName);
        }
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 2) {
            searchMap.put("search", search);
        }
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<CmdbResourceServerEntity> result = service.findAll(searchMap, pageBounds, "selectServerDomain");
        return PageResponse.getMap(result, draw);
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
                           String ipAddress,
                           String domain,
                           String cabinetId,
                           String switchId,
                           String switchPort
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
                case "7" :
                    searchMap.put("dbPhyUsed", "1");
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
        if (CheckUtil.checkString(domain)){
            searchMap.put("domain", domain);
        }
        if (CheckUtil.checkString(typeName)) {
            searchMap.put("typeName", typeName);
        }

        if (CheckUtil.checkString(switchPort)){
            searchMap.put("switchPort", switchPort);
        }
        if (CheckUtil.checkString(groupsName)) {
            searchMap.put("groupsName", groupsName);
        }

        if (CheckUtil.checkString(cabinetId)){
            searchMap.put("cabinetId", cabinetId);
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
        if (CheckUtil.checkString(switchId)){
            searchMap.put("switchId", switchId);
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
        cacheController.cacheServerInfo(entity.getIpAddress());
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
        if (null == cabinet){
            return "[]";
        }

        ArrayList data = new ArrayList();
        for (int i = 1; i <= cabinet.getNumber(); i++) {
            boolean is = false;
            for (CmdbResourceServerEntity c : result) {
                if (c != null && c.getCabinetLevel() != null && c.getCabinetLevel() == i) {
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