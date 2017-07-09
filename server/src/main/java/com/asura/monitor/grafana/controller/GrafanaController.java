package com.asura.monitor.grafana.controller;

import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.monitor.grafana.entity.DashboardEntity;
import com.asura.monitor.grafana.entity.DataSourceEntity;
import com.asura.monitor.grafana.service.DashboardService;
import com.asura.monitor.grafana.service.DataSourceService;
import com.asura.monitor.grafana.template.GrafanaTemplate;
import com.asura.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyun on 2017/7/7.
 */
@Controller
@RequestMapping("/monitor/grafana")
public class GrafanaController {

    private Logger logger = LoggerFactory.getLogger(GrafanaController.class);

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private IndexController logSave;

    /**
     * 初始化grafana数据源
     * @param request
     * @return
     */
    @RequestMapping("initSave")
    @ResponseBody
    public String init(HttpServletRequest request){
        DataSourceEntity dataSourceEntities = dataSourceService.findById(1, DataSourceEntity.class);
        if (null != dataSourceEntities){
            return "error";
        }
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        dataSourceEntity.setId(BigInteger.valueOf(1));
        dataSourceEntity.setOrgId(BigInteger.valueOf(1));
        dataSourceEntity.setVersion(1);
        dataSourceEntity.setType("prometheus");
        dataSourceEntity.setName("asura");
        dataSourceEntity.setAccess("proxy");
        dataSourceEntity.setBasicAuth(0);
        dataSourceEntity.setIsDefault(1);
        dataSourceEntity.setJsonData("{}");
        dataSourceEntity.setSecureJsonData("{}");
        dataSourceEntity.setWithCredentials(0);
        dataSourceEntity.setCreated(DateUtil.getTimeStamp());
        dataSourceEntity.setUpdated(DateUtil.getTimeStamp());
        dataSourceEntity.setUrl("http://{0}:{1}".replace("{0}", request.getServerName()).replace("{1}", ""+request.getServerPort()));
        dataSourceService.save(dataSourceEntity);
        return "ok";
    }


    /**
     * 检查图像是否已经存在
     * @param slug
     * @return
     */
    boolean checkImageExists(String slug){
        SearchMap searchMap = new SearchMap();
        searchMap.put("slug", slug);
        List<DashboardEntity> data = dashboardService.getListData(searchMap, "selectImageExists");
        if (data.size() < 1){
            return true;
        }
        return false;
    }

    /**
     *
     * @param entity
     * @param title
     * @return
     */
    DashboardEntity setDashboardEntity(DashboardEntity entity, String title){
        entity.setTitle(title);
        entity.setSlug(title);
        entity.setPluginId(title);
        return entity;
    }



    /**
     * s
     * @param request
     * @param names
     * @param line
     * @return
     */
    public String getGrafanaTemplate(HttpServletRequest request, String names, String line){
        try {
            init(request);
        }catch (Exception e){

        }
        DashboardEntity entity = new DashboardEntity();
        entity.setCreated(DateUtil.getTimeStamp());
        entity.setUpdated(DateUtil.getTimeStamp());
        entity.setVersion(0);
        entity.setOrgId(BigInteger.valueOf(1));
        entity.setGnetId(BigInteger.valueOf(1));
        entity.setUpdatedBy(1);
        entity.setCreatedBy(1);
        String graphType = line;
        Map map = new HashMap();
        String title;
        // 单线图
        if ("sign".equals(graphType)){
            String[] namesData = names.split(",");
            String[] datas;
            int count = 0;
            for (String sname : namesData) {
                datas = sname.split("\\|");
                logger.info("dsign data " + new Gson().toJson(datas));
                map =  GrafanaTemplate.getGrafanaTemplate(request, graphType, datas[0], datas[1], names);
                title = (String)map.get("title") + count;
                entity = setDashboardEntity(entity, title);
                if (checkImageExists(title)) {
                    entity.setData(new Gson().toJson(map));
                    dashboardService.save(entity);
                }
                count += 1;
            }
        }
        // 多线图
        if ("group".equals(graphType)){
            map =  GrafanaTemplate.getGrafanaTemplate(request, "group", null, null, names);
            title = (String)map.get("title");
            if (checkImageExists(title)) {
                entity = setDashboardEntity(entity, title);
                entity.setData(new Gson().toJson(map));
                dashboardService.save(entity);
            }
        }
        if (graphType.equals("mgroup") || graphType.equals("msign")) {
            map =  GrafanaTemplate.getGrafanaTemplate(request, graphType, null, null, names);
            title = (String)map.get("title");
            if (checkImageExists(title)) {
                entity = setDashboardEntity(entity, title);
                entity.setData(new Gson().toJson(map));
                dashboardService.save(entity);
            }
        }
        return new Gson().toJson(map);
    }
}
