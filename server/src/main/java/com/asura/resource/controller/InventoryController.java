package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.resource.entity.CmdbResourceInventoryEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceInventoryService;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.LdapAuthenticate;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    private LdapAuthenticate ldapAuthenticate;

    @Autowired
    private CmdbResourceInventoryService service;

    @Autowired
    IndexController indexController;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private CmdbResourceServerService serverService;

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
     * 列表数据
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        int counts = 0;
        int used = 0;
        int count;
        String groupsId = "";
        ArrayList sortList = new ArrayList();
        ArrayList<CmdbResourceInventoryEntity> list = new ArrayList();
        PagingResult<CmdbResourceInventoryEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
        for (CmdbResourceInventoryEntity entity : result.getRows()) {
            SearchMap searchMaps = new SearchMap();
            if (entity.getGroupsId() != null) {
                searchMaps.put("groups", entity.getGroupsId().split(","));
            } else {
                String[] group = new String[2];
                group[0] = "0";
                searchMaps.put("groups", group);
            }
            groupsId += entity.getGroupsId() + ",";
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
        groupsId = groupsId.replace(",,", ",");
        CmdbResourceInventoryEntity entity = new CmdbResourceInventoryEntity();
        entity.setInventoryNumber(counts - used);
        entity.setInventoryTotle(counts);
        entity.setInventoryUsed(used);
        entity.setTitle("库存总数");
        entity.setGroupsId(groupsId);
        sortList.add(entity);
        for (CmdbResourceInventoryEntity entity1: list){
            sortList.add(entity1);
        }
        return PageResponse.getList(sortList, draw);
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
        String dept = ldapAuthenticate.getSignUserInfo("department",  "sAMAccountName=" + user);
        if (!dept.contains("运维")) {
            return ResponseVo.responseError("必须由运维修改");
        }
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
