package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.MonitorGraphMergerEntity;
import com.asura.monitor.graph.entity.MonitorGraphTemplateEntity;
import com.asura.monitor.graph.service.MonitorGraphMergerService;
import com.asura.monitor.graph.service.MonitorGraphTemplateService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    @Autowired
    private MonitorGraphTemplateService templateService;

    /**
     * 图像配置入口
     * @return
     */
    @RequestMapping("images/list")
    public String imagesList(){
         return "/monitor/graph/merger/images/list";
     }

    /**
     * 图像模板配置入口
     * @return
     */
    @RequestMapping("template/list")
    public String templateList(){
        return "/monitor/graph/merger/template/list";
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
     * 获取图像数据
     * @param map
     * @param key
     * @return
     * */
     ArrayList getImagesData(ArrayList list, String key, Map<String, String> map){
        String[] names = map.get(key).split(",");
        for (int i=1; i < names.length+1; i++){
            list.add(names[i-1]);
        }
        return list;
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("images/add")
    public String imagesAdd(int id, Model model){
        if(id > 0){
            Gson gson = new Gson();
            MonitorGraphMergerEntity result = mergerService.findById(id, MonitorGraphMergerEntity.class);
            ArrayList names = new ArrayList();
            ArrayList colors = new ArrayList();
            ArrayList sizes = new ArrayList();
            ArrayList seriesMarkers = new ArrayList();
            Map<String, String> map = gson.fromJson(result.getImagesGson(), HashMap.class);
            getImagesData(names, "name", map);
            getImagesData(sizes, "size", map);
            getImagesData(colors, "color", map);
            getImagesData(seriesMarkers, "seriesMarker", map);
            model.addAttribute("names", names);
            model.addAttribute("sizes", sizes);
            model.addAttribute("colors", colors);
            model.addAttribute("seriesMarkers", seriesMarkers);
            model.addAttribute("configs", result);
            model.addAttribute("imagesGson", map);
        }
        return "/monitor/graph/merger/images/add";
    }

    /**
     * 模板添加
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("template/add")
    public String templateAdd(int id, Model model){
        if(id > 0) {
            Gson gson = new Gson();
            MonitorGraphTemplateEntity result = templateService.findById(id, MonitorGraphTemplateEntity.class);
            Map<String, String> map = gson.fromJson(result.getGraphIds(), HashMap.class);
            model.addAttribute("configs", result);
            model.addAttribute("graphMap", map);
        }
        return "/monitor/graph/merger/template/add";
    }

    /**
     *
     * @param request
     * @return
     */
    SearchMap getSearchMap(HttpServletRequest request){
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)){
            searchMap.put("key", search);
        }
        return searchMap;
    }

    /**
     * 获取合并图像数据
     * @return
     */
    @RequestMapping(value = "images/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String imagesList(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = getSearchMap(request);
        PagingResult<MonitorGraphMergerEntity> result = mergerService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @param draw
     * @param start
     * @param length
     * @param request
     * @return
     */
    @RequestMapping(value = "images/getImages", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getImages(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = getSearchMap(request);
        PagingResult<MonitorGraphMergerEntity> result = mergerService.findAll(searchMap, pageBounds, "selectImages");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取合并图像数据
     * @return
     */
    @RequestMapping(value = "template/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String templateList(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = getSearchMap(request);
        PagingResult<MonitorGraphTemplateEntity> result = templateService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 删除数据
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("images/deleteSave")
    @ResponseBody
    public ResponseVo imagesDelete(int id, HttpServletRequest request){
            MonitorGraphMergerEntity entity = mergerService.findById(id, MonitorGraphMergerEntity.class);
            indexController.logSave(request, "删除图像合并数据" + new Gson().toJson(entity));
            mergerService.delete(entity);
            return ResponseVo.responseOk(null);
    }

    /**
     * 删除数据
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("template/deleteSave")
    @ResponseBody
    public ResponseVo templateDelete(int id, HttpServletRequest request){
        MonitorGraphTemplateEntity entity = templateService.findById(id, MonitorGraphTemplateEntity.class);
        indexController.logSave(request, "删除图像模板" + new Gson().toJson(entity));
        templateService.delete(entity);
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("images/save")
    @ResponseBody
    public ResponseVo imagesSave(MonitorGraphMergerEntity entity, String data, int graphId, HttpServletRequest request) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(data, HashMap.class);
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setPage(map.get("page"));
        entity.setDescription(map.get("description"));
        entity.setLastModifyTime(DateUtil.getTimeStamp()+"");
        entity.setImagesGson(data);
        if (graphId > 0) {
            mergerService.update(entity);
        } else {
            mergerService.save(entity);
        }
        indexController.logSave(request, "添加图像合并" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("template/save")
    @ResponseBody
    public ResponseVo templateSave(MonitorGraphTemplateEntity entity, String data, HttpServletRequest request) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(data, HashMap.class);
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setDescription(map.get("description"));
        entity.setLastModifyTime(DateUtil.getTimeStamp()+"");
        entity.setGraphIds(data);
        if (entity.getTemplateId() > 0) {
            templateService.update(entity);
        } else {
            templateService.save(entity);
        }
        indexController.logSave(request, "保存图像模板" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }
}