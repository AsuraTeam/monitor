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
import java.util.List;
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
     * 获取图像数据
     * @param searchMap
     * @param pageBounds
     */
    public Map getGraphMap(SearchMap searchMap, PageBounds pageBounds){
        Map map = new HashMap();
        PagingResult<MonitorGraphMergerEntity> result = mergerService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorGraphMergerEntity entity: result.getRows()){
            map.put("graphId"+entity.getGraphId(), entity.getImagesGson().replace("\"", "\\\""));
        }
        return map;
    }

    /**
     * 图像生成吐口页面
     * @return
     */
    @RequestMapping("graph")
    public String graph(int templateId, Model model, String ip){
        Gson gson = new Gson();
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000, 1);
        MonitorGraphTemplateEntity templateEntity = templateService.findById(templateId, MonitorGraphTemplateEntity.class);

        String gsonData = templateEntity.getGsonData();
        if (CheckUtil.checkString(gsonData)) {
            Map<String, String> graphIpMap = new HashMap<>();
            Map<String, String> graphWidthMap = new HashMap<>();
            Map<String, String> graphHeightMap = new HashMap<>();
            Map<String, List<String>> map = gson.fromJson(gsonData, Map.class);
            List<String> graphLists = map.get("selectData");
            List<String> ipLists = map.get("ip");
            List<String> widths = map.get("width");
            List<String> heights = map.get("height");
            String[] ids;
            int ipCounter = 0;
            String key;
            int counter = 0;
            ArrayList templateGraphIds = new ArrayList();
            for (String graphId: graphLists){
                ids = graphId.split(",");
                for (String sgraphId : ids){
                    key = sgraphId + counter;
                    graphIpMap.put(key, ipLists.get(ipCounter));
                    graphHeightMap.put(key, heights.get(ipCounter));
                    graphWidthMap.put(key, widths.get(ipCounter));
                    templateGraphIds.add(sgraphId);
                    counter += 1;
                }
                ipCounter += 1;
            }
            searchMap.put("graphId", templateGraphIds);
            Map templateMap =  getGraphMap(searchMap, pageBounds);
            // 存放每个图像单独对应的IP地址
            model.addAttribute("graphIpMap",  gson.toJson(graphIpMap));
            model.addAttribute("graphHeightMap",  gson.toJson(graphHeightMap));
            model.addAttribute("graphWidthMap",  gson.toJson(graphWidthMap));
            model.addAttribute("graphTemplateMap", gson.toJson(templateMap));
            model.addAttribute("graphIds", templateGraphIds);
            model.addAttribute("ips", ip);
            model.addAttribute("title", templateEntity.getPage());
            return "/monitor/graph/merger/graph";
        }

        String graphId = templateEntity.getGraphIds();
        ArrayList graphIds = new ArrayList();
        searchMap.put("graphId", graphId.split(","));
        for (String id: graphId.split(",")){
            graphIds.add("graphId" + id);
        }
        Map map =  getGraphMap(searchMap, pageBounds);
        String[] ips = ip.split(",");
        ArrayList ipList = new ArrayList();
        for (String ipadd: ips){
            if(!ipList.contains(ipadd)) {
                ipList.add(ipadd);
            }
        }

        model.addAttribute("graphMap", map);
        model.addAttribute("ips", ipList);
        model.addAttribute("graphIds", graphIds);
        model.addAttribute("title", templateEntity.getPage());
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
    public String imagesAdd(int id, Model model, String clone){
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
        if(CheckUtil.checkString(clone)) {
            model.addAttribute("clone", clone);
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
    public String templateAdd(int id, Model model, HttpServletRequest request){
        if(id > 0) {
            Gson gson = new Gson();
            MonitorGraphTemplateEntity result = templateService.findById(id, MonitorGraphTemplateEntity.class);
            model.addAttribute("configs", result);
            String gsonData  = result.getGsonData();
            Map<String, List<String>> map = gson.fromJson(gsonData, Map.class);
            List<String> graphList = map.get("selectData");
            PagingResult<MonitorGraphMergerEntity> graphData;
            ArrayList returnImagesData = new ArrayList();
            for (String graphId: graphList){
                ArrayList graphDatas = new ArrayList();
                graphData = getImagesData(1, 1000, request, graphId);
                for (MonitorGraphMergerEntity entity: graphData.getRows()) {
                    graphDatas.add("<option value=\'" + entity.getGraphId() + "\'>" + entity.getPage() + "</option>");
                }
                returnImagesData.add(graphDatas);
            }
            System.out.println(gson.toJson(returnImagesData));
            model.addAttribute("graphs", gson.toJson(returnImagesData));
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
    public String imagesList(int draw, int start, int length, HttpServletRequest request, String graphId) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = getSearchMap(request);
        if (CheckUtil.checkString(graphId)){
            searchMap.put("graphId", graphId.split(","));
        }
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
    public String getImages(int draw, int start, int length, HttpServletRequest request, String graphId) {
        PagingResult<MonitorGraphMergerEntity> result = getImagesData(start, length, request, graphId);
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @param start
     * @param length
     * @param request
     * @param graphId
     * @return
     */
    PagingResult<MonitorGraphMergerEntity> getImagesData(int start, int length, HttpServletRequest request, String graphId){
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = getSearchMap(request);
        if (CheckUtil.checkString(graphId)){
            searchMap.put("graphId", graphId.split(","));
        }
        PagingResult<MonitorGraphMergerEntity> result = mergerService.findAll(searchMap, pageBounds, "selectImages");
        return result;
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
        entity.setPage(map.get("title"));
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
        entity.setLastModifyTime(DateUtil.getTimeStamp()+"");
        if (entity.getTemplateId() > 0) {
            templateService.update(entity);
        } else {
            templateService.save(entity);
        }
        indexController.logSave(request, "保存图像模板" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }
}