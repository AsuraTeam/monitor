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
        ArrayList list = new ArrayList();
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
            List<CmdbResourceServerEntity> serverData = serverService.getDataList(searchMaps, "countByGroups");
            if (serverData.size() > 0) {
                entity.setInventoryUsed(serverData.get(0).getCnt());
            }
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
                    entity.setInventoryNumber(entity.getInventoryNumber() - cnt);
                    service.update(entity);
                }
            }
            list.add(entity);
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
    @RequestMapping("delete")
    public ResponseVo delete(CmdbResourceInventoryEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        if (user == null || user.length() < 1) {
            return ResponseVo.responseError("请登陆后操作");
        }
        indexController.logSave(request, "删除库存信息 " + entity.getInventoryId());
        service.delete(entity);
        return ResponseVo.responseOk(null);
    }
}
