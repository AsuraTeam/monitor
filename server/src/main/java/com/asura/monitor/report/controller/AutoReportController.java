package com.asura.monitor.report.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.platform.entity.MonitorPlatformServerEntity;
import com.asura.monitor.platform.service.MonitorPlatformServerService;
import com.asura.monitor.report.entity.MonitorReportSendEntity;
import com.asura.monitor.report.service.MonitorReportSendService;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.email.MailEntity;
import com.asura.util.email.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 自动资源信息报告发送
 *
 * @author zhaozq
 * @version 1.0
 * @Date 2017-01-20
 */

@Controller
@RequestMapping("/monitor/report/auto/")
public class AutoReportController {

    @Autowired
    private MonitorPlatformServerService platformServerService;

    @Autowired
    private CmdbResourceGroupsService groupsService;

    @Autowired
    private MonitorReportSendService service;

    @Autowired
    private IndexController indexController;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private MonitorReportSendService sendService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model) {
        if (id > 0) {
            MonitorReportSendEntity result = service.findById(id, MonitorReportSendEntity.class);
            model.addAttribute("configs", result);
        }
        return "/monitor/report/auto/add";
    }


    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/monitor/report/auto/list";
    }


    /**
     * 获取报警异常处理数据清空
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int length, int start, int draw, HttpServletRequest request){
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if(search!=null&&search.length()>1){
            searchMap.put("key", search);
        }
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<MonitorReportSendEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result,draw);
    }

    /**
     * 项目配置
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo configureSave(MonitorReportSendEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getDate(DateUtil.TIME_FORMAT));
        if (entity.getSendId() != null) {
            service.update(entity);
        } else {
            service.save(entity);
        }
        indexController.logSave(request, "保存自动发送信息通知人 "+ entity.getGroupsId() +" "+ entity.getEmails());
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param data
     * @return
     */
    String getSize(String data){
        try {
            return Integer.valueOf(data) / 1024 / 1024 + "";
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 报表发送
     *
     * @return
     */
    @RequestMapping(value = "send", produces = {"application/json;charset=utf8"})
    @ResponseBody
    public String send() {
        Gson gson = new Gson();
        SearchMap searchMap = new SearchMap();
        SearchMap groupsMap;
        String html = "";
        String data1;
        String data2;
        String data3;
        String data4;
        String ip;
        String item = "disk|system.disk.";
        html = "<style>" +
                "table td{border-left:1px solid #D0D0D0;border-top:1px solid #D0D0D0; align:center;padding:8px;} " +
                "table {border-right:1px solid #D0D0D0;border-bottom:1px solid #D0D0D0;}" +
                "table th{border-left:1px solid #D0D0D0;border-top:1px solid #D0D0D0; align:center;padding:8px;} " +
                "</style> ";
        html += "<a href='http://v.asura.com/asura/graph/disk/list'>详情去可视化平台查看</a><br>";
        html += "<a href='http://v.asura.com/asura/graph/memory/list'>内存</a><br>";
        html += "<a href='http://v.asura.com/asura/graph/loadavg/list'>系统负载</a><br>";
        html += "<a href='http://v.asura.com/asura/graph/cpu/list'>CPU</a><br>";
        html += "<a href='http://v.asura.com/asura/report/auto/list'>如果缺少通知人,请点击添加</a><br>";
        String htmls = html;

        PagingResult<CmdbResourceGroupsEntity> groups = groupsService.findAll(searchMap, PageResponse.getPageBounds(100000, 1));
        for (CmdbResourceGroupsEntity entity : groups.getRows()) {
            html = "<table><tr><th>主机名</th><th>ip地址</th><th>磁盘名称</th><th>总大小(GB)</th><th>已使用(GB)</th><th>可使用(GB)</th><th>使用百分比(%)</th><th>数据更新时间</th></tr>";
            groupsMap = new SearchMap();
            groupsMap.put("groupsId", entity.getGroupsId());
            // 生产环境ID
            groupsMap.put("entId", 3);
            String email = "";
            PagingResult<MonitorPlatformServerEntity> servers = platformServerService.findAll(groupsMap, PageResponse.getPageBounds(100000, 1), "selectByAll");
            for (MonitorPlatformServerEntity entity1 : servers.getRows()) {
                html += "<tr>";
                ip = entity1.getIp();
                data1 = FileWriter.getItemData(item + "totle.SLASH", ip, 1);
                data2 = FileWriter.getItemData(item + "free.SLASH", ip, 1);
                data3 = FileWriter.getItemData(item + "used.percent.SLASH", ip, 1);
                data4 = FileWriter.getItemData(item + "in_use.SLASH", ip, 1);
                if(data1.length()>0 && data2.length()>0 && data3.length()>0 && data4.length()>0) {
                    String date = FileWriter.getItemData(item + "totle.SLASH", ip, 0);

                    if (Integer.valueOf(data3)> 80 ){
                        data3 = "<font color='red'>"+data3+"%</font>";
                    }else
                    if (Integer.valueOf(data3)> 70 ){
                        data3 = "<font color='#ffdc36'>"+data3+"%</font>";
                    }else {
                        data3 = data3 + "%";
                    }
                    html += "<td>"+entity1.getHostname() + "</td>" +
                            "<td>" + ip + "</td>" +
                            "<td>/</td>" +
                            "<td>" + getSize(data1) + "</td>" +
                            "<td>" + getSize(data4) + "</td>" +
                            "<td>" + getSize(data2) + "</td>" +
                            "<td>" + data3 + "</td>" +
                            "<td>" + DateUtil.timeStamp2Date(date)+ "</td>";
                }
                html += "</tr>";
            }
            PagingResult<MonitorReportSendEntity>  sendData =  sendService.findAll(groupsMap, PageResponse.getPageBounds(2,1), "selectByAll");
            if (sendData!=null&&sendData.getTotal()>0) {
                html += htmls;
                html  += "</table>";
                MailEntity mailEntity = new MailEntity();
                mailEntity.setReceiver(sendData.getRows().get(0).getEmails().replaceAll("\n",","));
                mailEntity.setAuth(false);
                mailEntity.setHost("127.0.0.1");
                mailEntity.setMessage(html);
                mailEntity.setSender("monitor@monitor.com");
                mailEntity.setSubject(entity.getGroupsName() + " 系统资源使用报告");
                for (int i=0;i<20;i++) {
                    if(MailUtil.sendMail(mailEntity)){
                        break;
                    }
                }
            }
        }
        return "ok";
    }
}
