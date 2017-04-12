package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.MonitorGraphMergerEntity;
import com.asura.monitor.graph.service.MonitorGraphMergerService;
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
 * 图像数据合并展示
 * 自定义颜色大小布局等
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/graph/merger/")
public class ImageMergerController {

    @Autowired
    private MonitorGraphMergerService mergerService;
    @Autowired
    private IndexController indexController;
    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     * 图像合并入口
     * @return
     */
    @RequestMapping("list")
    public String list(){
         return "/monitor/graph/merger/list";
     }

    /**
     * 图像页面
     * @return
     */
    @RequestMapping("graph")
    public String graph(){
        return "/monitor/graph/merger/graph";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String groupsAdd(int id, Model model){
        if(id>0){
            MonitorGraphMergerEntity result = mergerService.findById(id, MonitorGraphMergerEntity.class);
            model.addAttribute("configs", result);
        }
        return "/monitor/graph/merger/add";
    }

    /**
     * 获取合并图像数据
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String informationList(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)){
            searchMap.put("key", search);
        }
        PagingResult<MonitorGraphMergerEntity> result = mergerService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 删除数据
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo deleteConfigure(int id, HttpServletRequest request){
        MonitorGraphMergerEntity entity = mergerService.findById(id, MonitorGraphMergerEntity.class);
            indexController.logSave(request, "删除图像合并数据" + new Gson().toJson(entity));
            return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(MonitorGraphMergerEntity entity, HttpServletRequest request) {
        Gson gson = new Gson();
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp()+"");
        if (entity.getGraphId() != null) {
            mergerService.update(entity);
        } else {
            mergerService.save(entity);
        }
        indexController.logSave(request, "添加图像合并" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }
}