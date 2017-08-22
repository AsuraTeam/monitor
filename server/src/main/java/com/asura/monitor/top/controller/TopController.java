package com.asura.monitor.top.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.top.entity.MonitorTopEntity;
import com.asura.monitor.top.entity.MonitorTopImagesEntity;
import com.asura.monitor.top.service.MonitorTopImagesService;
import com.asura.monitor.top.service.MonitorTopService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/top/")
public class TopController {

    @Autowired
    private MonitorTopService topService;

    @Autowired
    private IndexController indexController;

    @Autowired
    private MonitorTopImagesService topImagesService;

    private final static Gson GSON = new Gson();

    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     *
     * @return
     */
    @RequestMapping("top")
    public String top(Model model, String topId){
        if (CheckUtil.checkString(topId)){
            MonitorTopEntity topEntity = topService.findById(Integer.valueOf(topId), MonitorTopEntity.class);
            model.addAttribute("gsonData", topEntity.getGsonData());
            model.addAttribute("imageName", topEntity.getImageName());
            model.addAttribute("topId", topId);
        }
        model.addAttribute("tops", topService.getListData(new SearchMap(), "selectNames"));
        model.addAttribute("images", topImagesService.getListData(new SearchMap(), "selectByAll"));
        return "/monitor/top/top";
    }

    /**
     *
     * @return
     */
    @RequestMapping("images")
    public String images(){
        return "/monitor/top/images";
    }

    /**
     *
     * @return
     */
    @RequestMapping("topIframe")
    public String topIframe(){
        return "/monitor/top/topIframe";
    }

    /**
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public String detail(int id){
        MonitorTopEntity topEntity = topService.findById(id, MonitorTopEntity.class);
         return "/monitor/top/detail";
    }

    /**
     *
     * @param entity
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(MonitorTopEntity entity, HttpServletRequest request){
        entity.setDescription(DateUtil.getDate(DateUtil.TIME_FORMAT));
        entity.setTopComm(permissionsCheck.getLoginUser(request.getSession()));
        if (entity.getTopId() > 0){
            topService.update(entity);
            indexController.logSave(request, "修改top图:".concat(GSON.toJson(entity)) );
        }else {
            topService.save(entity);
            indexController.logSave(request, "添加top图:".concat(GSON.toJson(entity)) );
        }
        return ResponseVo.responseOk(null);
    }

    /**
     * @param topId
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo delete(int topId, HttpServletRequest request){
        MonitorTopEntity topEntity = topService.findById(topId, MonitorTopEntity.class);
        topService.delete(topEntity);
        indexController.logSave(request, "删除top图:".concat(GSON.toJson(topEntity)) );
        return ResponseVo.responseOk(null);
    }


    /**
     *
     * @param draw
     * @param start
     * @param length
     * @param request
     * @param topId
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String recordData(int draw, int start, int length, HttpServletRequest request, String topId) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)) {
            searchMap.put("search", search);
        }
        if (CheckUtil.checkString(topId)){
            searchMap.put("topId", Integer.valueOf(topId));
        }
        PagingResult<MonitorTopEntity> result = topService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }


    /**
     * 消息通道
     *
     * @return
     */
    @RequestMapping(value = "imagesData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String imagesData(int draw, int start, int length, HttpServletRequest request, String size) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 1) {
            searchMap.put("search", search);
        }
        searchMap.put("sizes", size);
        PagingResult<MonitorTopImagesEntity> result = topImagesService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }
}