package com.asura.monitor.platform.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.PageResponse;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.platform.entity.IndexPerformanceEntity;
import com.asura.resource.controller.ServerController;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.CheckUtil;
import com.asura.util.MapSortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.graph.util.FileWriter.dataDir;
import static com.asura.monitor.graph.util.FileWriter.separator;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 指标性能排序查看
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/index/performance/")
public class IndexPerformanceController {

    @Autowired
    private CmdbResourceServerService serverService;

    private static String dir = dataDir + separator + "graph" + separator;

    @Autowired
    private ServerController serverController;

    /**
     * 列表页
     * @return
     */
    @RequestMapping("list")
    public String list(Model model){
        serverController.getData(model);
        return "/monitor/index/performance/list";
    }

    /**
     * @param entname
     * @param indexName
     * @param groupsName
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String listData(String indexName, String groupsName, String entname, int start, int length, int draw, String username, String typeId, String serverType){

        Gson gson = new Gson();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000, 1);
        SearchMap searchMap = new SearchMap();
        String fileTypeId = "";
        if(CheckUtil.checkString(groupsName)){
            fileTypeId += "group." + Integer.valueOf(groupsName);
            searchMap.put("groupsId", Integer.valueOf(groupsName));
        }
        if (CheckUtil.checkString(entname)){
            fileTypeId += "ent." + Integer.valueOf(entname);
            searchMap.put("entId", Integer.valueOf(entname));
        }
        if (CheckUtil.checkString(username)){
            fileTypeId += "user." + Integer.valueOf(username);
            searchMap.put("userId", Integer.valueOf(username));
        }
        if (CheckUtil.checkString(typeId)){
            fileTypeId += "type." + Integer.valueOf(typeId);
            searchMap.put("typeId", Integer.valueOf(typeId));
        }
        if (CheckUtil.checkString(serverType)){
            fileTypeId += "service." + Integer.valueOf(serverType);
            searchMap.put("serviceId", Integer.valueOf(serverType));
        }
        String file;
        String[] indexs = indexName.split("\\|");
        if (CheckUtil.checkString(fileTypeId)) {
            file = dir + FileRender.replace(indexs[1]) + "_" + fileTypeId;
        }else{
            file = dir + FileRender.replace(indexs[1]);
        }
        PagingResult<CmdbResourceServerEntity> result = serverService.findAll(searchMap, pageBounds, "selectAllIp");
        ArrayList<String> server = new ArrayList<>();
        for (CmdbResourceServerEntity entity: result.getRows()){
            server.add(entity.getIpAddress());
        }
        ArrayList resultData = new ArrayList();
        String data = orderByIndexPerformance(indexName, server, file, indexs);
        Type type = new TypeToken<ArrayList<IndexPerformanceEntity>>() {
        }.getType();
        List<IndexPerformanceEntity> list = new Gson().fromJson(data, type);
        int counter = 0;
        Long time =  new File(file).lastModified();
        SearchMap searchMap1 ;
        List<CmdbResourceServerEntity> serverEntities ;
        for (IndexPerformanceEntity entity: list){
            if (counter >= start / length * length  && counter <= start  + length -1 ) {
                searchMap1 = new SearchMap();
                searchMap1.put("ip", entity.getKey());
                serverEntities = serverService.getDataList(searchMap1, "selectGroupsHostname");
                entity.setLastModifyTime(time);
                if (serverEntities.size() > 0) {
                    entity.setHostname(serverEntities.get(0).getHostname());
                    entity.setGroupsName(serverEntities.get(0).getGroupsName());
                }
                resultData.add(entity);
            }
            counter += 1;
        }
        Map map = new HashMap();
        map.put("data", resultData);
        map.put("recordsTotal", list.size());
        map.put("recordsFiltered", list.size());
        map.put("draw", draw);
        return gson.toJson(map);
    }

    /**
     * 指标排序
     * @param indexName
     * @param server
     */
    String orderByIndexPerformance(String indexName, ArrayList<String> server, String file, String[] indexs){
        File oldFile = new File(file);
        if (oldFile.exists()){
            if ( System.currentTimeMillis() / 1000 - oldFile.lastModified() / 1000 < 300 ){
               return FileRender.readFile(file);
            }
        }
        Map<String, Double> map = new HashMap<>();
        String value;
        for (String ip: server) {
            value = FileWriter.getIndexData(ip, indexs[0], indexs[1], 1);
            try {
                map.put(ip, Double.valueOf(value));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        FileWriter.writeFile(file, MapSortUtil.doubleSort(map), false);
        return MapSortUtil.doubleSort(map);
    }
}
