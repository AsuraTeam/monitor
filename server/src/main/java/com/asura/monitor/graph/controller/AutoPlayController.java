package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.MonitorGraphAutoPlayEntity;
import com.asura.monitor.graph.service.MonitorGraphAutoPlayService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/monitor/graph/auto/play/")
public class AutoPlayController {

    @Autowired
    private MonitorGraphAutoPlayService playService;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private IndexController logSave;

    /**
     * 图像编辑
     * @return
     */
    @RequestMapping("add")
    public String groupsAdd(int id, Model model){
        if(id > 0){
            MonitorGraphAutoPlayEntity result = playService.findById(id, MonitorGraphAutoPlayEntity.class);
            model.addAttribute("configs", result);
        }
        return "/monitor/graph/auto/play/add";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getUrl", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUrl(int id){
        Map map = new HashMap<>();
        MonitorGraphAutoPlayEntity result = playService.findById(id, MonitorGraphAutoPlayEntity.class);
        map.put("url", result.getUrl());
        map.put("time", result.getIntervals());
        return new Gson().toJson(map);
    }

    /**
     * 图像收藏页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/monitor/graph/auto/play/list";
    }

    /**
     * 图像收藏页面
     * @return
     */
    @RequestMapping("play")
    public String play(String ids , Model model){
        model.addAttribute("ids", ids.split(","));
        return "/monitor/graph/auto/play/autoPlay";
    }

    /**
     * 收录信息
     * @return
     */
    @RequestMapping(value = "number", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String informationList(HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        PageBounds pageBounds = PageResponse.getPageBounds(1000, 1);
        SearchMap searchMap = new SearchMap();
        searchMap.put("user", user);
        PagingResult<MonitorGraphAutoPlayEntity> result = playService.findAll(searchMap, pageBounds, "selectByAll");
        return result.getTotal()+"";
    }

    /**
     * 收录信息
     * @return
     */
    @RequestMapping(value = "listSaveData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String informationList(int draw, int start, int length, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        searchMap.put("user", user);
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)){
            searchMap.put("key", search);
        }
        PagingResult<MonitorGraphAutoPlayEntity> result = playService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo messagesSave(MonitorGraphAutoPlayEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        String time = DateUtil.getDate(DateUtil.TIME_FORMAT);
        entity.setLastModifyTime(time);

        if (entity.getPlayId() != null) {
            MonitorGraphAutoPlayEntity update = playService.findById(entity.getPlayId(), MonitorGraphAutoPlayEntity.class);
            if (update.getLastModifyUser().equals(user)) {
                playService.update(entity);
            }
        } else {
                playService.save(entity);
        }
        logSave.logSave(request, "保存图像收录" + new Gson().toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     * 删除监控
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo deleteConfigure(int id, HttpServletRequest  request){
        MonitorGraphAutoPlayEntity entity = playService.findById(id, MonitorGraphAutoPlayEntity.class);
        String user = permissionsCheck.getLoginUser(request.getSession());
        if (entity.getLastModifyUser().equals(user)) {
            playService.delete(entity);
            logSave.logSave(request, "删除图像自动播放" + new Gson().toJson(entity));
            return ResponseVo.responseOk(null);
        }
        return ResponseVo.responseError(null);
    }
}
