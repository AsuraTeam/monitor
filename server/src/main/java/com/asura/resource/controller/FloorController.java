package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.resource.entity.CmdbResourceCabinetEntity;
import com.asura.resource.service.CmdbResourceCabinetService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.resource.entity.CmdbResourceFloorEntity;
import com.asura.resource.service.CmdbResourceFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 资源配置楼层机房
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/floor/")
public class FloorController {

    @Autowired
    private CmdbResourceFloorService service;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private IndexController logSave;

    @Autowired
    private CabinetController cabinetController;

    @Autowired
    private CacheController cacheController;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/resource/configure/floor/list";
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
        PagingResult<CmdbResourceFloorEntity> result = service.findAll(searchMap, pageBounds);
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     *
     * @return
     */
    @RequestMapping("add")
    public String add() {
        return "/resource/configure/floor/add";
    }


    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceFloorEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if (entity.getFloorId() != null) {
            service.update(entity);
        } else {
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        cacheController.cacheFloor();
        return ResponseVo.responseOk(null);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model) {
        CmdbResourceFloorEntity result = service.findById(id, CmdbResourceFloorEntity.class);
        model.addAttribute("configs", result);
        return "/resource/configure/floor/add";
    }

    /**
     * 删除机房信息
     *
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo delete(int id, HttpServletRequest request) {
        PagingResult<CmdbResourceCabinetEntity> cabinetEntityPagingResult = cabinetController.getCabinetData(id, 10, 1);
        if (cabinetEntityPagingResult != null && cabinetEntityPagingResult.getTotal() > 0) {
            return ResponseVo.responseError("请删除依赖的机柜后删除机房" + cabinetEntityPagingResult.getRows().get(0).getCabinetName());
        }
        CmdbResourceFloorEntity result = service.findById(id, CmdbResourceFloorEntity.class);
        service.delete(result);
        logSave.logSave(request, "删除机房" + new Gson().toJson(result));
        return ResponseVo.responseOk(null);
    }
}
