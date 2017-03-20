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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
