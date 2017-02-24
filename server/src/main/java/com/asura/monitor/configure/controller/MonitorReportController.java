package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.entity.MonitorMessagesSendReportEntity;
import com.asura.monitor.configure.service.MonitorMessagesService;
import com.asura.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     * @return
     */
    @RequestMapping(value = "messagesData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String messagesData(int start, int length, String startTime, String endTime) {
        String[] times = DateUtil.getMonthStartEnd();
        SearchMap searchMap = new SearchMap();
        searchMap.put("start", times[0]);
        searchMap.put("end", times[1]);
        if (startTime!=null && startTime.length()>1){
            searchMap.put("start", startTime);
        }
        if (endTime!=null && endTime.length()>1){
            searchMap.put("end", endTime);
        }

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
                PagingResult<MonitorMessagesEntity> result1 = messagesService.findAll(searchMap, pageBounds, "reportMessages");
                String cnt = "0";
                if (result1.getTotal()>0) {
                    MonitorMessagesEntity entity = result1.getRows().get(0);
                    cnt = entity.getCnt();
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
        map.put("recordsTotal", list.size());
        map.put("recordsFiltered", list.size());
        map.put("draw", 1);
        return new Gson().toJson(map);
    }
}
