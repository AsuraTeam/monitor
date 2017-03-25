package com.asura.monitor.top.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.top.entity.MonitorTopEntity;
import com.asura.monitor.top.service.MonitorTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    private final Gson GSON = new Gson();

    /**
     *
     * @return
     */
    @RequestMapping("top")
    public String top(){
        return "/monitor/top/top";
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
        topService.save(entity);
        indexController.logSave(request, "添加top图:".concat(GSON.toJson(entity)) );
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param entity
     * @return
     */
    @RequestMapping("deleteSave")
    public ResponseVo delete(MonitorTopEntity entity, HttpServletRequest request){
        topService.delete(entity);
        indexController.logSave(request, "删除top图:".concat(GSON.toJson(entity)) );
        return ResponseVo.responseOk(null);
    }

    /**
     * 消息通道
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String recordData(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 1) {
            searchMap.put("search", search);
        }
        PagingResult<MonitorTopEntity> result = topService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }
}