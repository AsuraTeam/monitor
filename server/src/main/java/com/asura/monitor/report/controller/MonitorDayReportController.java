package com.asura.monitor.report.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.report.entity.MonitorReportDayEntity;
import com.asura.monitor.report.service.MonitorReportDayService;
import com.asura.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 报警信息报表
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/reports/")
public class MonitorDayReportController {

    @Autowired
    private MonitorReportDayService reportDayService;

    /**
     * 获取所有业务组
     *
     * @param list
     *
     * @return
     */
    HashSet getGroups(List<MonitorReportDayEntity> list) {
        HashSet glist = new HashSet();
        for (MonitorReportDayEntity entity : list) {
            if (!glist.contains(entity.getGroupsName())) {
                glist.add(entity.getGroupsName());
            }
        }
        return glist;
    }

    /**
     * 获取指标名称
     * 2016/11/11
     *
     * @param groupsName
     *
     * @return
     */
    @RequestMapping(value = "getIndexNames", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getIndexNames(String groupsName, String startT, String endT) {
        SearchMap searchMap = getSearchMap(groupsName, null, startT, endT);
        List<MonitorReportDayEntity> result = reportDayService.getData(searchMap, "getIndexNames");
        Set<String> names = new HashSet<>();
        for (MonitorReportDayEntity entity : result) {
            names.add(entity.getIndexName());
        }
        return new Gson().toJson(names);
    }

    /**
     * 获取每个指标指定时间内的每小时报警趋势数据
     *
     * @param groupsName
     * @param startT
     * @param endT
     *
     * @return
     */
    @RequestMapping(value = "indexDataHistory", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String indexDataHistory(String groupsName, String startT, String endT,String indexName) {
        SearchMap searchMap = getSearchMap(groupsName, null, startT, endT);
        searchMap.put("indexName", indexName);
        List<MonitorReportDayEntity> result = reportDayService.getData(searchMap, "indexDataHistory");
        ArrayList<Long[]> datas = new ArrayList<>();
        for (MonitorReportDayEntity entity : result) {
            Long[] data = new Long[2];
            data[0] = Long.valueOf(entity.getAlarmTime());
            data[1] = Long.valueOf(entity.getCnt());
            datas.add(data);
        }
        return new Gson().toJson(datas);
    }

    /**
     * @param groupsName
     * @param month
     *
     * @return
     */
    SearchMap getSearchMap(String groupsName, String month, String startT, String endT) {
        SearchMap searchMap = new SearchMap();
        if (groupsName != null && groupsName.length() > 1) {
            searchMap.put("groupsName", groupsName);
        }
        if (month != null) {
            searchMap.put("month", month);
        }
        if (startT != null && endT != null && startT.length() > 1 && endT.length() > 1) {
            searchMap.put("start", startT);
            searchMap.put("end", endT);
        } else {
            searchMap.put("month", DateUtil.getDate("yyyy-MM"));
        }

        return searchMap;
    }

    /**
     * 各业务线的月度报警报表
     *
     * @return
     */
    @RequestMapping(value = "reportIndexName", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String reportIndexName(String groupsName) {
        SearchMap searchMap = getSearchMap(groupsName, null, null, null);
        List<MonitorReportDayEntity> result = reportDayService.getData(searchMap, "reportIndexName");
        if (result.size() > 0) {
            return new Gson().toJson(result);
        } else {
            return "";
        }
    }

    /**
     * 详细指标使用的数据
     *
     * @return
     */
    @RequestMapping(value = "indexNameDetail", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String indexNameDetail(int start, int length, int draw, String groupsName, String startT, String endT, String indexName) {
        SearchMap searchMap = getSearchMap(groupsName, null, startT, endT);
        searchMap.put("indexName", indexName);
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<MonitorReportDayEntity> list = reportDayService.findAll(searchMap, pageBounds, "indexNameDetail");
        return PageResponse.getMap(list, draw);
    }

    /**
     * 各业务线的月度报警报表
     *
     * @return
     */
    @RequestMapping("reportMonthGroupsNameIndexNameList")
    public String reportMonthGroupsNameIndexNameList(Model model, String groupsName, String startT, String endT, String indexName) {
        SearchMap searchMap = getSearchMap(groupsName, null, startT, endT);
        List<MonitorReportDayEntity> list = getMonitorReportList(searchMap);
        model.addAttribute("groupsName", getGroups(list));
        model.addAttribute("groupName", groupsName);
        if (startT != null && endT != null) {
            model.addAttribute("start", startT);
            model.addAttribute("end", endT);
        } else {
            model.addAttribute("month", DateUtil.getDate("yyyy-MM"));
        }
        if (indexName != null && indexName.length() > 1) {
            model.addAttribute("indexName", indexName);
        }
        return "/monitor/reports/reportMonthGroupsNameIndexNameList";
    }

    /**
     * 获取报警数据
     *
     * @param searchMap
     *
     * @return
     */
    List<MonitorReportDayEntity> getMonitorReportList(SearchMap searchMap) {
        List<MonitorReportDayEntity> listData = reportDayService.getData(searchMap, "reportMonthGroupsNameIndexName");
        return listData;
    }

    String getMap(List<MonitorReportDayEntity> list) {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", list.size());
        map.put("recordsFiltered", list.size());
        map.put("draw", 1);
        return gson.toJson(map);
    }
    /**
     * 获取每个月每个业务部每个指标的报警次数
     * @return
     */
    /**
     * 从redis获取实时监控状态
     */
    @RequestMapping(value = "reportMonthGroupsNameIndexName", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String reportMonthGroupsNameIndexName(String groupsName, String startT, String endT) {

        SearchMap searchMap = getSearchMap(groupsName, null, startT, endT);
        List<MonitorReportDayEntity> list = getMonitorReportList(searchMap);
        return getMap(list);
    }
}
