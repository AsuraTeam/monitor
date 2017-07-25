package com.asura.monitor.api;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.PageResponse;
import com.asura.monitor.api.entity.PrometheusEntity;
import com.asura.monitor.configure.entity.MonitorIndexFromScriptsEntity;
import com.asura.monitor.configure.service.MonitorIndexFromScriptsService;
import com.asura.monitor.graph.controller.CommentController;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.platform.entity.MonitorPlatformServerEntity;
import com.asura.monitor.platform.service.MonitorPlatformServerService;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyun on 2017/7/4.
 */
@Controller
@RequestMapping("/api/v1/")
public class DataApiController {

    @Autowired
    private MonitorIndexFromScriptsService indexService;

    private Logger logger = LoggerFactory.getLogger(DataApiController.class);

    @Autowired
    private MonitorPlatformServerService serverService;

    /**
     * 获取数据
     * @return
     */
    @RequestMapping("label/__name__/values")
    @ResponseBody
    public String nameValues(){
        Gson gson = new Gson();
        PrometheusEntity prometheusEntity = new PrometheusEntity();
        prometheusEntity.setStatus("success");
        SearchMap searchMap = new SearchMap();
        ArrayList arrayList = new ArrayList();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000000, 1);
        PagingResult<MonitorIndexFromScriptsEntity> result = indexService.findAll(searchMap, pageBounds, "selectIndexName");
        for (MonitorIndexFromScriptsEntity entity: result.getRows()){
            arrayList.add(entity.getIndexName());
        }
        prometheusEntity.setData(arrayList);
        String datas  =  gson.toJson(prometheusEntity);
        return  datas;
    }


    /**
     *
     * @param request
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "series",  produces = {"application/json;charset=utf8"})
    @ResponseBody
    public String queryRange(HttpServletRequest request, Long start, Long end){
        Gson gson = new Gson();
        SearchMap searchMap = new SearchMap();
        String query = request.getParameter("match[]");
        Map<String, String> queryMap = new HashMap();
        List<Map<String, String>> queryList = new ArrayList<>();
        if (CheckUtil.checkString(query)) {
            try {
                if (query.contains("[") && query.contains("]")){
                    Type type = new TypeToken<ArrayList<Map>>(){}.getType();
                    queryList = gson.fromJson(query, type);
                }else {
                    queryMap = gson.fromJson(query, HashMap.class);
                }
            }catch (Exception e){
                searchMap.put("search", query);
            }
        }
        if (null != queryMap){
            for (Map.Entry<String, String> entry:queryMap.entrySet()){
                searchMap.put(entry.getKey(), entry.getValue().replace("?", ""));
            }
        }
        if (null != queryList){
            for (Map<String, String> map:queryList){
                for (Map.Entry<String, String> entry:map.entrySet()){
                    searchMap.put(entry.getKey(), entry.getValue().replace("?", ""));
                }
            }
        }
        logger.info(request.getQueryString());
        Map map = new HashMap();
        map.put("status", "success");
        ArrayList data = new ArrayList();
        Map dataMap ;
        map.put("data", data);
        PagingResult<MonitorPlatformServerEntity> result = serverService.findAll(searchMap, PageResponse.getPageBounds(25, 10), "selectByAll");
        for (MonitorPlatformServerEntity entity: result.getRows()){
            dataMap =  new HashMap();
            dataMap.put("__name__", entity.getIp());
            data.add(dataMap);
        }
        return new Gson().toJson(map);
    }

    /**
     *    /api/v1/
     *    queyr 用逗号分隔
     * @param request
     * @return
     */
    @RequestMapping(value = "query_range",  produces = {"application/json;charset=utf8"})
    @ResponseBody
    public String queryRange(String query , HttpServletRequest request,long step, Long start, Long end, PrometheusEntity prometheusEntity){
        if (query.contains("uptime")){
        }
        String[] names = query.split("\\|");
        if (names.length < 2){
            names[0] = "loadavg";
            names[1] = "system.load.1";
        }
        String dataType = "";
        if (names.length> 2){
            dataType = names[2];
        }
        String host = "";
        String name = "";
        String[] hostsData = names[1].split(",");
        if (hostsData.length > 1){
            host = hostsData[1].split("\\{")[0];
            name = hostsData[0];
        }
        logger.info(request.getQueryString());
        Gson gson = new Gson();
        String startT = DateUtil.timeStamp2Date(start+"000", "yyyy-MM-dd");
        String endT = DateUtil.timeStamp2Date(end+"000", "yyyy-MM-dd");

        ArrayList<ArrayList> list = FileRender.readHistory(host,names[0], name,startT,endT,null, true, null);
        ArrayList valueList = new ArrayList();
        Long times;
        for (ArrayList data:list){
            times = (Long)data.get(0);
            if ( times >= start && times <= end ){
                    valueList.add(data);
            }
        }

        ArrayList result = new ArrayList();
        Map metricMap = new HashMap();

        Map<String, String> metric = new HashMap();
        metric.put("__name__", name);
        metricMap.put("metric", metric);
        metricMap.put("values", valueList);
        result.add(metricMap);


        Map data = new HashMap();
        data.put("resultType", "matrix");
        data.put("result", result);

        Map map = new HashMap<>();
        map.put("status","success");
        map.put("data", data);
        return gson.toJson(map);
    }
}
