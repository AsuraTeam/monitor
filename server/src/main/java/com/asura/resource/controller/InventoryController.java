package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.resource.entity.CmdbResourceInventoryEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceInventoryService;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.CheckUtil;
import com.asura.util.LdapAuthenticate;
import com.asura.util.PermissionsCheck;
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
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 资源配置操作系统类型配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/inventory/")
public class InventoryController {


    @Autowired
    private CmdbResourceInventoryService service;

    @Autowired
    IndexController indexController;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private CmdbResourceServerService serverService;

    /**
     * 库存报表
     *
     * @return
     */
    @RequestMapping("report")
    public String report(String inventoryId, Model model, String test, String online) {
        Gson gson = new Gson();
        model.addAttribute("inventoryId", inventoryId);
        String data = listData(1, 1,inventoryId, test, online);
        Map map = gson.fromJson(data, Map.class);
        model.addAttribute("data", ((List) map.get("data")).get(0));
        return "/resource/inventory/report";
    }

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/resource/inventory/list";
    }

    /**
     * 获取库存信息
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
    Map getListData(SearchMap searchMap, PageBounds pageBounds) {
        Map map = new HashMap();
        int count = 0;
        int counts = 0;
        int used = 0;
        String groupsId = "";
        ArrayList<CmdbResourceInventoryEntity> list = new ArrayList<>();
        PagingResult<CmdbResourceInventoryEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
        for (CmdbResourceInventoryEntity entity : result.getRows()) {
            SearchMap searchMaps = new SearchMap();
            String[] group = new String[2];
            group[0] = "0";
            searchMaps.put("groups", group);
            if (entity.getGroupsId() != null) {
                searchMaps.put("groups", entity.getGroupsId().split(","));
            }

            // 购买的物理机数量
            List<CmdbResourceServerEntity> buyData = serverService.getDataList(searchMaps, "selectBuyGroups");
            entity.setBuyNumber(buyData.get(0).getCnt());

            // 虚拟化设备数量selectVmHostNumber
            List<CmdbResourceServerEntity> selectVmHostNumber = serverService.getDataList(searchMaps, "selectVmHostNumber");
            entity.setPhyVmNumber(selectVmHostNumber.get(0).getCnt());

            // 获取借用的数量 selectPhyFromInventory
            List<CmdbResourceServerEntity> selectPhyFromInventory = serverService.getDataList(searchMaps, "selectPhyFromInventory");
            entity.setFromInventory(selectPhyFromInventory.get(0).getCnt());

            List<CmdbResourceServerEntity> selectPhyInventoryUse = serverService.getDataList(searchMaps, "selectPhyInventoryUse");
            // 物理机已使用数量
            entity.setPhyUsed(selectPhyInventoryUse.get(0).getCnt());

            // 获取未使用库存selectPhyUnusedInventory
            List<CmdbResourceServerEntity> selectPhyUnusedInventory = serverService.getDataList(searchMaps, "selectPhyUnusedInventory");
            entity.setUnused(selectPhyUnusedInventory.get(0).getCnt());

            // 非虚拟化库存数量
            entity.setPhyInventoryNumber(entity.getBuyNumber() - entity.getPhyUsed() - entity.getPhyVmNumber());
            groupsId += entity.getGroupsId() + ",";

            // 按单元计算虚拟机数量
            List<CmdbResourceServerEntity> selectVmUsedNumber = serverService.getDataList(searchMaps, "selectVmUsedNumber");
            entity.setVmUnitsUsed(selectVmUsedNumber.get(0).getCnt());

            // 按单元计算虚拟机数量
            try {
                List<CmdbResourceServerEntity> selectVmPrice = serverService.getDataList(searchMaps, "selectVmPrice");
                entity.setVmPrice(selectVmPrice.get(0).getCnt());
            } catch (Exception e) {
                entity.setVmPrice(1000);
            }

            List<CmdbResourceServerEntity> serverData = serverService.getDataList(searchMaps, "countByGroups");
            if (serverData.size() > 0) {
                count = serverData.get(0).getCnt();
                entity.setInventoryUsed(count);
            }
            counts += entity.getInventoryTotle();
            used += entity.getInventoryUsed();
            String currData = String.valueOf(System.currentTimeMillis() / 1000);
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(currData);
                service.update(entity);
            } else {
                searchMaps.put("start", entity.getUpdateTime());
                List<CmdbResourceServerEntity> serverDataByTime = serverService.getDataList(searchMaps, "countByGroups");
                int cnt = serverDataByTime.get(0).getCnt();

                if (cnt > 0) {
                    entity.setUpdateTime(currData);
                    count = entity.getInventoryNumber() - cnt;
                    entity.setInventoryNumber(count);
                    service.update(entity);
                }

            }
            list.add(entity);
        }
        map.put("list", list);
        map.put("used", used);
        map.put("counts", counts);
        map.put("groupsId", groupsId);
        return map;
    }

    /**
     * 按不同环境计算数据
     * @param entity
     * @param testNumber
     * @param onlineNumber
     * @param searchMap1
     * @param used
     * @param groupsId
     * @return
     */
    CmdbResourceInventoryEntity getInventoryDataEnv(CmdbResourceInventoryEntity entity, String testNumber, String onlineNumber, SearchMap searchMap1, int used, String  groupsId){
        int test = 20;
        int online = 13;
        if (CheckUtil.checkString(testNumber)){
            test = Integer.valueOf(testNumber);
        }
        if (CheckUtil.checkString(onlineNumber)){
            online = Integer.valueOf(onlineNumber);
        }
        searchMap1.put("online", "1");
        CmdbResourceInventoryEntity inventoryDataOnline = getInventoryData(searchMap1, used, groupsId, testNumber, onlineNumber );
        searchMap1.remove("online");
        entity.setOnlineBuyNumber(inventoryDataOnline.getBuyNumber() + "");
        entity.setOnlinePhyInventoryNumber(inventoryDataOnline.getPhyInventoryNumber()+"");
        entity.setOnlineFromNumber(inventoryDataOnline.getFromInventory() + "");
        entity.setOnlineVmInventoryNumber(((inventoryDataOnline.getPhyVmNumber() * online) - inventoryDataOnline.getVmUnitsUsed())+"");
        entity.setOnlineVmUsedNumber(inventoryDataOnline.getVmUnitsUsed() + "");
        entity.setOnlineUnused(inventoryDataOnline.getUnused()+"");
        entity.setOnlineVmPhyNumber(inventoryDataOnline.getPhyVmNumber()+"");
        entity.setOnlinePhyUsedNumber(inventoryDataOnline.getPhyUsed());

        searchMap1.put("noOnline", "1");
        CmdbResourceInventoryEntity inventoryDataNoOnline = getInventoryData(searchMap1, used, groupsId, testNumber, onlineNumber);
        entity.setTestBuyNumber(inventoryDataNoOnline.getBuyNumber() + "");
        entity.setTestFromNumber(inventoryDataNoOnline.getFromInventory() + "");
        entity.setTestPhyInventoryNumber(inventoryDataNoOnline.getPhyInventoryNumber()+"");
        entity.setTestVmUsedNumber(inventoryDataNoOnline.getInventoryNumber() + "");
        entity.setTestVmInventoryNumber(((inventoryDataNoOnline.getPhyVmNumber() * test) - inventoryDataNoOnline.getVmUnitsUsed())+"");
        entity.setTestVmUsedNumber(inventoryDataNoOnline.getVmUnitsUsed()+"");
        entity.setTestUnused(inventoryDataNoOnline.getUnused()+"");
        entity.setTestVmPhyNumber(inventoryDataNoOnline.getPhyVmNumber()+"");
        entity.setTestPhyUsedNumber(inventoryDataNoOnline.getPhyUsed());
        entity.setVmInventoryNumber(Integer.valueOf(entity.getTestVmInventoryNumber()) + Integer.valueOf(entity.getOnlineVmInventoryNumber()));
        return entity;
    }

    /**
     * @param searchMap1
     * @param used
     * @param groupsId
     */
    CmdbResourceInventoryEntity getInventoryData(SearchMap searchMap1, int used, String groupsId, String testNumber, String onlineNumber) {
        CmdbResourceInventoryEntity entity = new CmdbResourceInventoryEntity();
        // 购买的物理机数量
        List<CmdbResourceServerEntity> buyData = serverService.getDataList(searchMap1, "selectBuyGroups");
        entity.setBuyNumber(buyData.get(0).getCnt());

        // 虚拟化设备数量selectVmHostNumber selectVmUsedNumber
        List<CmdbResourceServerEntity> selectVmHostNumber = serverService.getDataList(searchMap1, "selectVmHostNumber");
        entity.setPhyVmNumber(selectVmHostNumber.get(0).getCnt());

        List<CmdbResourceServerEntity> selectVmUsedNumber = serverService.getDataList(searchMap1, "selectVmUsedNumber");
        entity.setVmUnitsUsed(selectVmUsedNumber.get(0).getCnt());

        // 按单元计算虚拟机数量
        List<CmdbResourceServerEntity> selectVmPrice = serverService.getDataList(searchMap1, "selectVmPrice");
        entity.setInventoryId(0);
        try {
            entity.setVmPrice(selectVmPrice.get(0).getCnt());
        } catch (Exception e) {
            entity.setVmPrice(1000);
        }

        // 总资产数量  购买数量 + 虚拟机使用数量
        entity.setInventoryTotle(entity.getBuyNumber() + entity.getInventoryUsed());

        // 获取未使用库存selectPhyUnusedInventory
        List<CmdbResourceServerEntity> selectPhyUnusedInventory = serverService.getDataList(searchMap1, "selectPhyUnusedInventory");
        entity.setUnused(selectPhyUnusedInventory.get(0).getCnt());

        // 物理机已使用数量
        entity.setPhyUsed(entity.getBuyNumber() - entity.getPhyVmNumber());
        entity.setInventoryUsed(used);
        entity.setTitle("库存总数");
        entity.setGroupsId(groupsId);

        return entity;
    }

    /***
     *
     * @param draw
     * @param start
     * @param inventoryId
     * @param testNumber
     * @param onlineNumber
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, String inventoryId, String testNumber, String onlineNumber) {

        PageBounds pageBounds = PageResponse.getPageBounds(10000, start);
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(inventoryId)) {
            if (!inventoryId.equals("0")) {
                searchMap.put("inventoryId", Integer.valueOf(inventoryId));
            }
        }
        ArrayList sortList = new ArrayList();
        Map map = getListData(searchMap, pageBounds);
        ArrayList<CmdbResourceInventoryEntity> list = (ArrayList<CmdbResourceInventoryEntity>) map.get("list");
        String groupsId = (String) map.get("groupsId");
        groupsId = groupsId.replace(",,", ",");
        int used = (int) map.get("used");
        SearchMap searchMap1 = new SearchMap();
        searchMap1.put("groups", groupsId.split(","));
        CmdbResourceInventoryEntity entity = new CmdbResourceInventoryEntity();

        if (!CheckUtil.checkString(inventoryId) || inventoryId.equals("0")) {
            entity = getInventoryData(searchMap1, used, groupsId, testNumber, onlineNumber);
            entity = getInventoryDataEnv(entity, testNumber , onlineNumber, searchMap1, used, groupsId);
        }


        if (CheckUtil.checkString(inventoryId) && inventoryId.equals("0")) {
            sortList.add(entity);
        } else {
            if (!CheckUtil.checkString(inventoryId)) {
                sortList.add(entity);
            }
            for (CmdbResourceInventoryEntity entity1 : list) {
                entity1 = getInventoryDataEnv(entity1, testNumber , onlineNumber, searchMap1, used, groupsId);
                sortList.add(entity1);
            }
        }
        return PageResponse.getList(sortList, draw);
    }


    /**
     *
     * @param draw
     * @param inventoryId
     * @param testNumber
     * @param onlineNumber
     * @return
     */
    @RequestMapping(value = "listDataAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listDataAll(int draw, String inventoryId, String testNumber, String onlineNumber) {
        Gson gson = new Gson();
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(100000,1 );
        PagingResult<CmdbResourceInventoryEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
        List list = new ArrayList();
        Map map1 = gson.fromJson(listData(1, 1, "0", testNumber, onlineNumber), Map.class);
        list.add(((List) map1.get("data")).get(0));
        for (CmdbResourceInventoryEntity entity: result.getRows()) {
            String data = listData(1, 1, entity.getInventoryId()+"", testNumber, onlineNumber);
            Map map = gson.fromJson(data, Map.class);
            list.add(((List) map.get("data")).get(0));
        }
        return PageResponse.getList(list, draw);
    }

        /**
         * 添加页面
         *
         * @return
         */
    @RequestMapping("add")
    public String add(int id, Model model) {
        if (id > 0) {
            CmdbResourceInventoryEntity result = service.findById(id, CmdbResourceInventoryEntity.class);
            model.addAttribute("configs", result);
        }
        return "/resource/inventory/add";
    }


    /**
     * 保存库存信息
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceInventoryEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        if (entity.getInventoryId() != null) {
            service.update(entity);
        } else {
            service.save(entity);
        }
        indexController.logSave(request, "添加更新库存信息 " + entity.getTitle() + "  " + entity.getInventoryTotle());
        return ResponseVo.responseOk(null);
    }


    /**
     * 删除信息
     *
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo delete(CmdbResourceInventoryEntity entity, HttpServletRequest request) {
        indexController.logSave(request, "删除库存信息 " + entity.getInventoryId());
        service.delete(entity);
        return ResponseVo.responseOk(null);
    }
}
