package com.asura.monitor.report.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.monitor.entity.CheckCountEntity;
import com.asura.monitor.monitor.entity.CmdbMonitorInformationEntity;
import com.asura.monitor.monitor.entity.CmdbMonitorMessagesEntity;
import com.asura.monitor.monitor.service.MonitorInformationService;
import com.asura.monitor.monitor.service.MonitorMessagesService;
import com.asura.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  监控数据管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/23 18:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/report/")
public class DayReportController {

    @Autowired
    private MonitorInformationService monitorService ;

    @Autowired
    private MonitorMessagesService messagesService;

    @Autowired
    private HoursReportController hoursReportController;

    private Gson gson = new Gson();

    public double getPercent(double totle, double number){
        double result = number/totle*100;
        String r = String.format("%.2f",result);
        return Double.parseDouble(r);
    }




    /**
     * 报警次数分析
     * @param model
     * @return
     */
    @RequestMapping("checkCount")
    public String checkCount(Model model, String groups){
        SearchMap map = new SearchMap();
        if(groups!=null&&groups.length()>1){
            map.put("groups",groups);
        }
        List<CmdbMonitorInformationEntity> m = monitorService.selectCheckCommandCount(map);

        ArrayList<String> checkName = new ArrayList();
        for(CmdbMonitorInformationEntity c:m){
            if(!checkName.contains(c.getName())){
                checkName.add(c.getName());
            }
        }


        Set<CheckCountEntity> check = new HashSet<>();
        for(String  name:checkName) {
            CheckCountEntity temp = new CheckCountEntity();
            temp.setName(name);
              for(CmdbMonitorInformationEntity c:m){
                if(name.equals(c.getName())) {
                    if (c.getStatus().equals("正常")) {
                        temp.setOk(c.getNoticeNumber());
                    }
                    if (c.getStatus().equals("危险")) {
                        temp.setDanger(c.getNoticeNumber());
                    }

                    if (c.getStatus().equals("未知")) {
                        temp.setUnknown(c.getNoticeNumber());
                    }
                    if (c.getStatus().equals("警告")) {
                        temp.setWarning(c.getNoticeNumber());
                    }
                    check.add(temp);
                }
            }
        }

        model.addAttribute("check",check);
        return "monitor/report/checkCount";
    }

    /**
     * 监控报表页面
     * @return
     */
    @RequestMapping("index")
    public String index(Model model, String groups){
        SearchMap map = new SearchMap();
        if(groups!=null&&groups.length()>1){
            map.put("groups",groups);
        }
        double ok = 0.0;
        double warning = 0;
        double unknown = 0;
        double danger = 0;
        List<CmdbMonitorInformationEntity> c = monitorService.selectAlarmCount(map);
        for(CmdbMonitorInformationEntity m:c){
            if(m.getStatus().equals("正常")){
                ok = m.getNoticeNumber();
            }
            if(m.getStatus().equals("危险")){
                danger = m.getNoticeNumber();
            }
            if(m.getStatus().equals("未知")){
                unknown = m.getNoticeNumber();
            }
            if(m.getStatus().equals("警告")){
                warning = m.getNoticeNumber();
            }
        }
        double totle = ok + danger + unknown + warning;
        model.addAttribute("groups",groups);
        model.addAttribute("totle", totle);
        model.addAttribute("ok",ok);
        model.addAttribute("warning",warning);
        model.addAttribute("unknown",unknown);
        model.addAttribute("danger",danger);
        model.addAttribute("okP",getPercent(totle,ok));
        model.addAttribute("warningP",getPercent(totle,warning));
        model.addAttribute("unknownP",getPercent(totle,unknown));
        model.addAttribute("dangerP",getPercent(totle,danger));
        return "monitor/report/index";
    }


    /**
     * 展示异常的信息
     * @param model
     * @return
     */
    @RequestMapping("dangerData")
    public String dangerData(Model model, String groups){
        PageBounds pageBounds = PageResponse.getPageBounds(100000,1);
        SearchMap map = new SearchMap();
        map.put("noStatus","正常");
        if(groups!=null&&groups.length()>1){
            map.put("groups",groups);
        }
        PagingResult<CmdbMonitorInformationEntity> check = monitorService.findAll(map,pageBounds);
        model.addAttribute("check",check.getRows());
        return "monitor/report/dangerData";
    }

    /**
     * 分组报表页面
     * @return
     */
    @RequestMapping("groupsReport")
    public String groupsReport(){
        return "monitor/report/groupsReport";
    }

    /**
     * 每日监控报表
     * @param model
     * @return
     */
    @RequestMapping("dayMonitorReport")
    public String dayMonitorReport(Model model,String groups){
        model = getMonitorMessages(model,groups);
        return "monitor/report/dayMonitorReport";
    }

    /**
     * 时间间断数据
     * @return
     */
    public SearchMap getSearchMap(){
        Timestamp[] t = DateUtil.getDayStartEnd();
        SearchMap map = new SearchMap();
        Timestamp start = t[0];
        Timestamp end = t[1];
        map.put("start",start);
        map.put("end",end);
        return map;
    }

    /**
     * 获取监控报警信息
     * @param model
     * @return
     */
    public Model getMonitorMessages(Model model,String groups){
        Timestamp[] t = DateUtil.getDayStartEnd();
        SearchMap map = getSearchMap();
        if(groups!=null) {
            map.put("groups", groups);
        }
        PageBounds pageBounds = PageResponse.getPageBounds(10000,1);
        PagingResult<CmdbMonitorMessagesEntity> result = messagesService.findAll(map,pageBounds);
        model.addAttribute("messages",result.getRows());
        model.addAttribute("totle",result.getTotal());
        model.addAttribute("groups",groups);
        return model;
    }

    /**
     * 今日监控异常事件时间树
     * @return
     */
    @RequestMapping("dayMonitor")
    public String  dayMonitor(Model model,String groups){
        model = getMonitorMessages(model,groups);
        return "monitor/report/dayMonitor";
    }

    /**
     * 报警信息
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value="dayMonitorData",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String dayMonitorData(int draw, int start, int length,int hours,String checkCommand,String groups, HttpServletRequest request){
        Timestamp[] t = DateUtil.getDayStartEnd();
        String search = request.getParameter("search[value]");
        PageBounds pageBounds = PageResponse.getPageBounds(length,start);
        SearchMap map;

        if(hours>=0){
            // 获取每小时的数据
            map = hoursReportController.getHoursSearchMap(hours,groups);
        }else{
            // 全天的数据
            map = getSearchMap();
        }

        if(groups!=null&&groups.length()>1){
            map.put("groups",groups);
        }

        map.put("messages",search.trim());
        if(checkCommand.length()>2){
            map.put("checkCommand",checkCommand);
        }
        PagingResult<CmdbMonitorMessagesEntity> result = messagesService.findAll(map,pageBounds);
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取每小时报警的数据
     * @return
     * @throws Exception
     */
    public  ArrayList[] getHoursData(SearchMap map) throws  Exception{

        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(map,"selectHoursCount");
        ArrayList[] set = new ArrayList[24];
        String[] tList;
        int count = 0;
        DateFormat dateFormat;
        for(int i=0;i<24;i++){
            String hT = i+"";
            if(i<10){
                hT = "0"+hT;
            }

            long hours ;
            int cnt;
            String[] t = new String[2];
            t[0] = DateUtil.getDay();
            t[1] = hT;
            tList = t;
            cnt = 0;
            dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            hours = dateFormat.parse(tList[0] + " " + hT + ":00:00").getTime();

            ArrayList temp = new ArrayList();
            for(CmdbMonitorMessagesEntity c:result) {
                tList = c.getHours().split(" ");

                if(tList[1].equals(hT)) {
                    cnt = c.getCnt();
                    dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    hours = dateFormat.parse(tList[0] + " " + tList[1] + ":00:00").getTime();
                    temp.add(hours);
                    temp.add(cnt);
                }
            }
            if(temp.size()<1){
                temp.add(hours);
                temp.add(cnt);
            }
            set[count] = temp;
            count += 1;
        }
        return set;
    }

    /**
     * 每小时报警次数表格数据
     * @return
     */
    @RequestMapping(value="selectHoursCountData",produces={"application/json;chartset:utf-8"})
    @ResponseBody
    public String selectHoursCountData(int draw,String groups) throws Exception{
        SearchMap searchMap = getSearchMap();
        if(groups!=null&&groups.length()>1){
            searchMap.put("groups",groups);
        }
        ArrayList[] set = getHoursData(searchMap);

        Object[] objects = new Object[24];
        int count = 0;
        for(ArrayList a:set){
            Map map = new HashMap();
            map.put("cnt",a.get(1));
            map.put("hours",a.get(0));
            objects[count] = map;
            count += 1;

        }
        Map map = new HashMap();
        map.put("data", objects);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", draw);
       return new Gson().toJson(map);
    }

    /**
     * 查询每小时报警数据
     * @return
     */
    @RequestMapping("selectHoursCount")
    @ResponseBody
    public String selectHoursCount(String groups) throws  Exception {
        SearchMap map = getSearchMap();
        if(groups!=null&&groups.length()>1){
            map.put("groups",groups);
        }
        ArrayList[] set = getHoursData(map);
        return gson.toJson(set);
    }

    /**
     * 公用，list数据
     * @param result
     * @param draw
     * @return
     */
    public String  getCountGroupMap(List<CmdbMonitorMessagesEntity> result, int draw){
        Map map = new HashMap();
        map.put("data", result);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", draw);
        return gson.toJson(map);
    }

    /**
     * 不同监控类型的报警次数
     * @param draw
     * @return
     */
    @RequestMapping(value="selectMonitorNameCount",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String selectmonitorNameCount(int draw, String groups){
        SearchMap searchMap = getSearchMap();
        if(groups.length()>1){
            searchMap.put("groups",groups);
        }
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(searchMap,"selectMonitorNameCount");
        return getCountGroupMap(result, draw);
    }

    /**
     * 不同产品线的每天报警次数
     * @param draw
     * @return
     */
    @RequestMapping(value="selectGroupsCount",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String selectGroupsCount(int draw,String startT, String endT,String groups){
        SearchMap searchMap = getSearchMap();
        if(startT.length()>1){
            searchMap.put("start",startT);
        }
        if(endT.length()>1){
            searchMap.put("end",endT);
        }
        if(groups!=null&&groups.length()>1){
            searchMap.put("groups",groups);
        }
        List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(searchMap,"selectGroupsCount");
        return getCountGroupMap(result, draw);
    }




}
