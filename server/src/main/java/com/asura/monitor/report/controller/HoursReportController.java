package com.asura.monitor.report.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.monitor.monitor.entity.CmdbMonitorMessagesEntity;
import com.asura.monitor.monitor.service.MonitorMessagesService;
import com.asura.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  每小时报表数据
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/26 9:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/report/hours/")
public class HoursReportController {


    @Autowired
    private MonitorMessagesService messagesService;

    @Autowired
    private DayReportController reportController;

    private Gson gson = new Gson();


    /**
     * 每小时数据公用map
     *
     * @param hours
     *
     * @return
     */
    public SearchMap getHoursSearchMap(int hours,String groups) {
        String endHours = "";
        String startHours = "";
        Timestamp[] t = DateUtil.getDayStartEnd();
        SearchMap searchMap = new SearchMap();
        String start = String.valueOf(t[0]);

        if (hours < 10) {
            endHours = "0" + (hours + 1);
            startHours = "0" + hours;
        } else {
            endHours = Integer.valueOf(hours) + 1 + "";
            startHours = hours + "";
        }

        if(groups!=null&&groups.length()>1){
            searchMap.put("groups",groups);
        }
        searchMap.put("start", start.substring(0, 11) + startHours + start.substring(13, 19));
        searchMap.put("end", start.substring(0, 11) + endHours + start.substring(13, 19));
        return searchMap;
    }

    /**
     * 统计分析各个报警数据，
     *
     * @return
     */
    @RequestMapping(value = "selectHoursMonitorNameCount", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getMonitorCntData(int hours, int draw, String groups) {
        SearchMap searchMap = getHoursSearchMap(hours,groups);
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(searchMap,"selectMonitorNameCount");
        return reportController.getCountGroupMap(result, draw);
    }

    /**
     * @param hours
     * @param draw
     *
     * @return
     */
    @RequestMapping(value = "selectHoursGroupsCount", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectHoursGroupsCount(int hours, int draw,String groups) {
        SearchMap searchMap = getHoursSearchMap(hours,groups);
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(searchMap,"selectGroupsCount");
        return reportController.getCountGroupMap(result, draw);
    }

    /**
     * 每小时监控分析页面
     *
     * @return
     */
    @RequestMapping("hoursReportPage")
    public String hoursReportPage(int hours, Model model, String groups) {
        PageBounds pageBounds = new PageBounds(10000, 1);
        model.addAttribute("hours", hours + "点-" + (hours + 1) + "点 ");
        PagingResult result = messagesService.findAll(getHoursSearchMap(hours,groups), pageBounds);
        model.addAttribute("totle", result.getTotal());
        model.addAttribute("currhours", hours);
        model.addAttribute("groups",groups);
        return "monitor/report/hours/hoursReport";
    }


    /**
     * 查询每小时不同分钟报警数据
     *
     * @return
     */
    @RequestMapping(value="selectMinuteCount",produces={"application/json;chartset:utf-8"})
    @ResponseBody
    public String selectMinuteCount(int hours, String groups) throws Exception {
        SearchMap map = getHoursSearchMap(hours,groups);
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(map,"selectMinuteCount");
        ArrayList set = new ArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        for(CmdbMonitorMessagesEntity c:result){
            long time = dateFormat.parse(c.getHours()).getTime();
            long[] t = new long[2];
            t[0] = time;
            t[1] = c.getCnt();
            set.add(t);
        }
        return gson.toJson(set);
    }

    @RequestMapping(value="select10MinuteCount",produces={"application/json;chartset=utf-8"})
    @ResponseBody
    public String select10MinuteCount(int hours, String groups) throws Exception {
        SearchMap map = getHoursSearchMap(hours,groups);
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(map,"select10MinuteCount");
        return reportController.getCountGroupMap(result, 1);
    }
}