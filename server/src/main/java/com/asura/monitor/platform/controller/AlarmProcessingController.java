package com.asura.monitor.platform.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.conf.SevertityConfig;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.monitor.configure.util.MessagesChannelUtil;
import com.asura.monitor.report.entity.MonitorReportDayEntity;
import com.asura.monitor.report.service.MonitorReportDayService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import com.asura.util.ding.DingUtil;
import com.asura.util.email.MailEntity;
import com.asura.util.weixin.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.asura.monitor.configure.util.MessagesChannelUtil.getContact;
import static com.asura.monitor.configure.util.MessagesChannelUtil.getEmailEntity;
import static com.asura.monitor.configure.util.MessagesChannelUtil.sendEmail;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 报警处理
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/platform/alarm")
public class AlarmProcessingController {

    @Autowired
    private MonitorReportDayService reportDayService;

    @Autowired
    private MonitorMessageChannelService channelService;

    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     *
     * @return
     */
    @RequestMapping("list")
    public String list(){
        reportDayService.updateAutoRecover();
        return "/monitor/platform/alarm/list";
    }

    /**
     * 获取报警异常处理数据清空
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int length, int start, int draw, String  changeStatus, HttpServletRequest request){
        SearchMap searchMap = new SearchMap();
        if (changeStatus!=null&&changeStatus.length()>0){
            searchMap.put("changeStatus", changeStatus);
        }
        if (changeStatus!=null&&changeStatus.equals("0")){
            searchMap.remove("changeStatus");
            searchMap.put("changeStatusNull", "1");
        }
        String search = request.getParameter("search[value]");
        if(search!=null&&search.length()>1){
            searchMap.put("ipAddress", search);
        }
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<MonitorReportDayEntity> result = reportDayService.findAll(searchMap, pageBounds, "getHoursAlarmData");
        return PageResponse.getMap(result,draw);
    }

    /**
     * 报警进度处理通知
     * 发送给默认管理员
     * 发邮件，钉钉，微信，不发短信
     */
    void sendMessages(int reportId) {
        RedisUtil redisUtil = new RedisUtil();
        MonitorReportDayEntity entity  = reportDayService.findById(reportId, MonitorReportDayEntity.class);

        String messages = "故障处理通知:" +
                "\n处理人:      " + entity.getOperator() +
                "\n处理时间:    " + DateUtil.getDate(DateUtil.TIME_FORMAT) +
                "\n处理进度:    " + SevertityConfig.getStatus(entity.getChangeStatus()) +
                "\n故障开始时间:" + entity.getStartTime() +
                "\n报警状态:    " + SevertityConfig.getSevertity(entity.getSeverityId(), false)+
                "\n报警服务器:  " + entity.getIpAddress() +
                "\n报警指标:    " + entity.getIndexName().replace("SLASH", "/");

        String adminGroup = redisUtil.get(MonitorCacheConfig.cacheIsAdmin);
        MonitorMessagesEntity messagesEntity = new MonitorMessagesEntity();
        messagesEntity.setMessages(messages);
        messagesEntity.setWeixin(getContact(adminGroup, "weixin"));
        messagesEntity.setDing(getContact(adminGroup, "ding"));
        messagesEntity.setEmail(getContact(adminGroup, "email"));
        // 发送微信
        WeixinUtil.sendWeixin(MessagesChannelUtil.getChannelEntity(channelService, "weixin"), messagesEntity);

        // 发送邮件
        MailEntity mailEntity = getEmailEntity(channelService, messagesEntity);
        mailEntity.setMessage(messagesEntity.getMessages());
        sendEmail(false, mailEntity);

        // 发送钉钉
        messagesEntity.setDing(messagesEntity.getDing().replaceAll(",", "|"));
        DingUtil.sendDingDing(MessagesChannelUtil.getChannelEntity(channelService, "ding"), messagesEntity);
    }

    /**
     * @param reportId
     * @param changeStatus
     * @return
     */
    @RequestMapping(value = "changeAlarm", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String changeAlarm(int reportId, String changeStatus, HttpSession session){
        String user = permissionsCheck.getLoginUser(session);
        MonitorReportDayEntity entity = new MonitorReportDayEntity();
        entity.setOperator(user);
        entity.setChangeStatus(changeStatus);
        entity.setReportId(reportId);
        entity.setOperatorTime(DateUtil.getDateStamp() +"");
        reportDayService.update(entity);
        sendMessages(reportId);
        return "ok";
    }
}
