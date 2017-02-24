package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.MonitorImagesCollectionEntity;
import com.asura.monitor.graph.service.MonitorImagesCollectionService;
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
@RequestMapping("/monitor/graph/collection/")
public class CollectionGraphController {

    @Autowired
    private MonitorImagesCollectionService collectionService;

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
        if(id>0){
            MonitorImagesCollectionEntity result = collectionService.findById(id, MonitorImagesCollectionEntity.class);
            model.addAttribute("configs",result);
        }
        return "/monitor/graph/collection/add";
    }

    /**
     * 图像收藏页面
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/monitor/graph/collection/list";
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
        PagingResult<MonitorImagesCollectionEntity> result = collectionService.findAll(searchMap, pageBounds, "selectByAll");
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
        if (search != null && search.length()>1){
            searchMap.put("key", search);
        }
        PagingResult<MonitorImagesCollectionEntity> result = collectionService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo messagesSave(MonitorImagesCollectionEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setUser(user);
        String time = DateUtil.getDate(DateUtil.TIME_FORMAT);
        entity.setLastModifyTime(time);
        if (entity.getIp()==null&&entity.getIp().length()<5){
            return ResponseVo.responseError(null);
        }
        if (entity.getImages()==null&&entity.getImages().length()<5){
            return ResponseVo.responseError(null);
        }
        if (entity.getCollectionId() != null) {
            MonitorImagesCollectionEntity update = collectionService.findById(entity.getCollectionId(), MonitorImagesCollectionEntity.class);
            if (update.getUser().equals(user)) {
                collectionService.update(entity);
            }
        } else {
            SearchMap searchMap = new SearchMap();
            PageBounds pageBounds = PageResponse.getPageBounds(1,300);
            PagingResult<MonitorImagesCollectionEntity> result = collectionService.findAll(searchMap, pageBounds, "selectByAll");
            if (result.getTotal() < 300) {
                entity.setCreateTime(time);
                collectionService.save(entity);
            }
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
        MonitorImagesCollectionEntity entity = collectionService.findById(id, MonitorImagesCollectionEntity.class);
        String user = permissionsCheck.getLoginUser(request.getSession());
        if (entity.getUser().equals(user)) {
            collectionService.delete(entity);
            logSave.logSave(request, "删除图像收录" + new Gson().toJson(entity));
            return ResponseVo.responseOk(null);
        }
        return ResponseVo.responseError(null);
    }
}
