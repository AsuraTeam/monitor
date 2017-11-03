package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.entity.MonitorMessagesSendReportEntity;
import com.asura.monitor.configure.service.MonitorMessagesService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
@RequestMapping("/monitor/report/")
public class MonitorReportController {

    @Autowired
    private MonitorMessagesService messagesService;


    /**
     * 报警消息报表信息
     *
     * @return
     */
    @RequestMapping("messages")
    public String messages() {
        return "/monitor/report/messages/list";
    }

    /**
     * 报警趋势统计
     * @return
     */
    @RequestMapping("alarmCount")
    public String alarmCount(ModelMap modelMap,String startTime, String endTime) {
        modelMap.addAttribute("startTime", startTime);
        modelMap.addAttribute("endTime", endTime);
        return "/monitor/report/messages/alarmCount";
    }

    /**
     *
     * @param startT
     * @param endT
     * @param model
     * @return
     */
    @RequestMapping("messages/detail/messages")
    public String messagesDay( String startT, String endT, Model model)
    {
        if (CheckUtil.checkString(startT) && CheckUtil.checkString(endT)) {
            model.addAttribute("startT", startT);
            model.addAttribute("endT", endT);
        }
        return "/monitor/report/messages/detail";
    }


    /**
     *
     * @param model
     * @param startT
     * @param endT
     * @return
     */
    @RequestMapping("messages/detail/list")
    public String messagesDetailList(Model model, String startT, String endT) {
        SearchMap searchMap = getSearchMap(startT, endT);
        PageBounds pageBounds = PageResponse.getPageBounds(1000000, 1);
        model.addAttribute("startT", searchMap.get("start"));
        model.addAttribute("endT", searchMap.get("end"));
        try {
            PagingResult<MonitorMessagesEntity> result = messagesService.findAll(searchMap, pageBounds, "selectCountGroupsNumber");
            model.addAttribute("totle", result.getRows().get(0).getCnt());
        }catch (Exception e){
            model.addAttribute("totle", 0);
        }
        return "/monitor/report/messages/detailList";
    }

    /**
     * 最小单位为天的报警名字统计
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "selectMonitorMessagesReport", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectMonitorMessagesReport(String startTime, String endTime, String dates, String isMonth){
        SearchMap searchMap = getSearchMap(startTime, endTime);
        if (CheckUtil.checkString(dates)){
            searchMap.put("dates", dates);
        }
        if (CheckUtil.checkString(isMonth)){
            searchMap.put("month", "1");
        }
        PageBounds pageBounds = PageResponse.getPageBounds(1000000, 1);
        PagingResult<MonitorMessagesEntity> result = messagesService.findAll(searchMap, pageBounds, "selectMonitorMessagesReport");
        return PageResponse.getMap(result, 1);
    }


    /**
     *
     * @param startTime
     * @param endTime
     * @param scripts
     * @return
     */
    @RequestMapping(value = "selectMessagesCountDetail", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String messagesData( String startTime, String endTime, String scripts) {
        if (CheckUtil.checkString(endTime)){
            endTime = endTime + " 23:59:59";
        }
        SearchMap searchMap = getSearchMap(startTime, endTime);
        if (CheckUtil.checkString(scripts)) {
            searchMap.put("scriptsId", scripts.split(","));
        }
        HashSet<String> names = new HashSet();
        ArrayList<Long> dates = new ArrayList<>();
        Map<String, String> dataMap = new HashedMap();
        Map map;
        ArrayList arrayList = new ArrayList();
        List<MonitorMessagesEntity> data = messagesService.getDataList(searchMap,"selectMessagesCountDetail");
        for (MonitorMessagesEntity entity:data){
            names.add(entity.getMonitorName());
            if (!arrayList.contains(entity.getTime())) {
                dates.add(entity.getTime());
            }
            dataMap.put(entity.getMonitorName()+entity.getTime(), entity.getCnt());
        }
        ArrayList dataList;
        ArrayList tempList;
        for (String name:names){
            map = new HashedMap();
            map.put("name", name);
            dataList = new ArrayList();
            for (long date:dates){
                tempList = new ArrayList();
                tempList.add(date);
                if (dataMap.containsKey(name+date)){
                    tempList.add(Integer.valueOf(dataMap.get(name+date).toString()));
                }else{
                    tempList.add(0);
                }
                dataList.add(tempList);
            }
            map.put("data", dataList);
            arrayList.add(map);
        }
        return new Gson().toJson(arrayList);
    }

    /**
     *  报警趋势图表数据
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "selectCountMessagesGraph", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectCountMessagesGraph(String startTime, String endTime, String scripts){
        if (CheckUtil.checkString(endTime)){
            endTime = endTime + " 23:59:59";
        }
        SearchMap searchMap = getSearchMap(startTime, endTime);
        if (CheckUtil.checkString(scripts)) {
            searchMap.put("scriptsId", scripts.split(","));
        }
        ArrayList arrayList = new ArrayList();
        ArrayList dataList ;
        List<MonitorMessagesEntity> data = messagesService.getDataList(searchMap, "selectCountMessagesGraph");
        for (MonitorMessagesEntity monitorMessagesEntity:data){
            dataList = new ArrayList();
            dataList.add( monitorMessagesEntity.getTime());
            dataList.add(Integer.valueOf(monitorMessagesEntity.getCnt()));
            arrayList.add(dataList);
        }
        return new Gson().toJson(arrayList);
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "messagesGroupsData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String messagesGroupsData(String startTime, String endTime){
        SearchMap searchMap = getSearchMap(startTime, endTime);
        PageBounds pageBounds = PageResponse.getPageBounds(1000000, 1);
        PagingResult<MonitorMessagesEntity> result = messagesService.findAll(searchMap, pageBounds, "selectGroupsNumber");
        return PageResponse.getMap(result, 1);
    }

    /**
     * 获取search数据
     * @param startTime
     * @param endTime
     * @return
     */
    SearchMap getSearchMap(String startTime, String endTime){
        String[] times = DateUtil.getMonthStartEnd();
        SearchMap searchMap = new SearchMap();
        searchMap.put("start", times[0]);
        searchMap.put("end", times[1]);
        if (CheckUtil.checkString(startTime)){
            searchMap.put("start", startTime);
        }
        if (CheckUtil.checkString(endTime)){
            searchMap.put("end", endTime);
        }
        return searchMap;
    }


        /**
         * @return
         */
    @RequestMapping(value = "messagesData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String messagesData(int start, int length, String startTime, String endTime, int draw) {
        SearchMap searchMap = getSearchMap(startTime, endTime);
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<MonitorMessagesEntity> result = messagesService.findAll(searchMap, pageBounds, "reportMessages");
        List<String> dates = new ArrayList<>();
        for (MonitorMessagesEntity entity : result.getRows()) {
            String string = String.valueOf(entity.getMessagesTime());
            if (!dates.contains(string)) {
                dates.add(string);
            }
        }

        ArrayList<String> types = new ArrayList<>();
        types.add("email");
        types.add("mobile");
        types.add("ding");
        types.add("weixin");

        List<MonitorMessagesSendReportEntity> list = new ArrayList<>();
        for (String date : dates) {
            MonitorMessagesSendReportEntity reportEntity = new MonitorMessagesSendReportEntity();
            reportEntity.setMessagesTime(date);
            for (String type : types) {
                searchMap = new SearchMap<>();
                searchMap.put("type", type);
                searchMap.put("start", date.split(" ")[0] + " 00:00:00");
                searchMap.put("end", date.split(" ")[0] + " 23:59:00");
                searchMap.put(type, type);
                PagingResult<MonitorMessagesEntity> result1 = messagesService.findAll(searchMap, PageResponse.getPageBounds(2,1), "reportMessages");
                String cnt = "0";
                if (result1 != null && result1.getTotal()>0 && result1.getRows().size() > 0) {
                    MonitorMessagesEntity entity = result1.getRows().get(0);
                    cnt = entity.getCnt();
                }else{
                    continue;
                }
                switch (type) {
                    case "email":
                        reportEntity.setEmailCount(cnt);
                        break;
                    case "ding":
                        reportEntity.setDingCount(cnt);
                        break;
                    case "mobile":
                        reportEntity.setMobileCount(cnt);
                        break;
                    case "weixin":
                        reportEntity.setWeixinCount(cnt);
                        break;
                    default:
                            break;
                }
            }
            if (!list.contains(reportEntity)) {
                list.add(reportEntity);
            }
        }
        Map map = new HashMap<>();
        map.put("data", list);
        map.put("recordsTotal", result.getTotal());
        map.put("recordsFiltered", result.getTotal());
        map.put("draw", draw);
        return new Gson().toJson(map);
    }
}
