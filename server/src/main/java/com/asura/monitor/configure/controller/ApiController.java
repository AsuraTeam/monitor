package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.conf.SevertityConfig;
import com.asura.monitor.configure.entity.MonitorIndexAlarmEntity;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.service.MonitorIndexAlarmService;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.monitor.configure.service.MonitorMessagesService;
import com.asura.monitor.configure.util.MessagesChannelUtil;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.monitor.controller.MonitorGlobaltController;
import com.asura.monitor.report.entity.MonitorReportDayEntity;
import com.asura.monitor.report.service.MonitorReportDayService;
import com.asura.util.Base64Util;
import com.asura.util.DateUtil;
import com.asura.util.RedisUtil;
import com.asura.util.RequestClientIpUtil;
import com.asura.util.ScriptUtil;
import com.asura.util.ding.DingUtil;
import com.asura.util.email.MailEntity;
import com.asura.util.weixin.WeixinUtil;
import org.apache.log4j.Logger;
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

import static com.asura.monitor.configure.util.MessagesChannelUtil.getChannelEntity;
import static com.asura.monitor.configure.util.MessagesChannelUtil.getEmailEntity;
import static com.asura.monitor.graph.util.FileWriter.dataDir;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 监控数据上传,处理报警灯，数据记录
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/monitor/api/")
public class ApiController {


    // 获取临时文件目录
    public static final String separator = System.getProperty("file.separator");
    private static final String tempDir = System.getProperty("java.io.tmpdir") + separator + "monitor" + separator;
    private static final Logger LOGGER = Logger.getLogger(ApiController.class);

    private static final RedisUtil redisUtil = new RedisUtil();

    private static final Gson gson = new Gson();

    @Autowired
    private MonitorMessagesService messagesService;

    @Autowired
    private MonitorIndexAlarmService alarmService;

    @Autowired
    private MonitorMessageChannelService channelService;

    private static MonitorGlobaltController globaltController ;
    private static final Runtime runtime = Runtime.getRuntime();

    @Autowired
    private MonitorReportDayService reportDayService;

    // 报错报警的参数信息，启动或更改时改数据
    private static Map<String, MonitorIndexAlarmEntity> alarmEntities;
    private static long alarmEntitiesTime;


    /**
     * 获取indexname
     * 20161123
     * @param indexName
     * @return
     */
    String getIndexName(String  indexName){
        String[] index = indexName.split("\\.");
        String result = "";
        for (int i=1;i< index.length;i++){
            result += index[i] +".";
        }
        result = result.substring(0,result.length()-1);
        return result;
    }

    /**
     * 设置报警检查的配置信息，放在内存中
     * 数据结构为
     * ip +  是否发送报警 + indexName
     */
    void setAlarmEntities() {
        alarmEntities = new HashMap<>();
        alarmEntities.put("1",null);
        PageBounds pageBounds = PageResponse.getPageBounds(10000000, 1);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorIndexAlarmEntity> result = alarmService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorIndexAlarmEntity entity:result.getRows()){
            String key = entity.getIpAddress();
            key += entity.getIsAlarm();
            key += getIndexName(entity.getIndexName());
            alarmEntities.put(key, entity);
        }
    }

    /**
     * 设置缓存
     */
      void setCache(){
        if (alarmEntities==null){
            setAlarmEntities();
            alarmEntitiesTime = System.currentTimeMillis() / 1000;
        }
        if (System.currentTimeMillis()/1000 - alarmEntitiesTime > 600 ){
            setAlarmEntities();
            alarmEntitiesTime = System.currentTimeMillis() / 1000;
        }

    }


    /**
     * 检查是否发送报警.
     * @param alarm
     * @param pushEntity
     * @return
     */
     MonitorIndexAlarmEntity checkAlarm(String alarm, PushEntity pushEntity){
        MonitorIndexAlarmEntity entity = new MonitorIndexAlarmEntity();
        entity.setSendAlarm(false);
        entity.setIsConfigure(false);
        if (alarmEntities.containsKey(alarm)){
            String mess = "";
            boolean sendAlarm = false;
            entity = alarmEntities.get(alarm);
            String gtValue = entity.getGtValue();
            String ltValue = entity.getLtValue();
            double pushValue = Double.valueOf(pushEntity.getValue());
            if (ltValue!=null&&ltValue.length()>0){
                if (Double.valueOf(ltValue) < pushValue){
                    mess = "期望值小于实际值: " + pushValue + " < " +  ltValue;
                    sendAlarm =  true;

                }
            }
            if (gtValue!=null&&gtValue.length()>0){
                if ( pushValue > Double.valueOf(gtValue) ){
                    mess = "期望值大于实际值: " + pushValue + " > " + gtValue;
                    sendAlarm =  true;
                }
            }
            String eqValue = entity.getEqValue();
            if (eqValue!=null&&eqValue.length()>0){
                if(pushEntity.getValue().equals(eqValue)){
                    mess = "期望值等于实际值: " + eqValue + " = " + pushValue;
                    sendAlarm =  true;
                }
            }
            String notEqValue = entity.getNotEqValue();
            if (notEqValue!=null&&notEqValue.length()>0) {
                if (!notEqValue.equals(pushEntity.getValue())) {
                    mess = "期望值不等于实际值: " + notEqValue + " != " + pushValue;
                    sendAlarm =  true;
                }
            }
            entity.setSendMessages(mess);
            entity.setSendAlarm(sendAlarm);
            // 设置是否有配置
            if (sendAlarm) {
                entity.setIsConfigure(true);
            }
        }
        return entity;
    }

    /**
     * 检查是否需要发送报警
     * @return
     */
    MonitorIndexAlarmEntity checkAlarm(PushEntity pushEntity, String ip){
        setCache();
        // 不发送报警检查
        String noAlarm = ip + "0" + pushEntity.getName();
        if (alarmEntities.containsKey(noAlarm)){
            LOGGER.info("获取到包含不发送报警的配置");
            LOGGER.info(gson.toJson(alarmEntities));
            MonitorIndexAlarmEntity entity = new MonitorIndexAlarmEntity();
            entity.setSendAlarm(false);
            entity.setIsConfigure(true);
            return entity;
        }
        // 发送报警检查
        String alarm = ip + "1" + pushEntity.getName();
        return checkAlarm(alarm, pushEntity);
    }


    /**
     * 状态正常的数据提交
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("/success")
    @ResponseBody
    public ResponseVo successPush(String lentity, PushEntity entity, HttpServletRequest request){
        lentity = Base64Util.decode(lentity);
        writePush(lentity, request, "success");
        entity.setServer(request.getLocalAddr());
        return ResponseVo.responseOk(entity);
    }

    /**
     * 每天一个目录，每半小时更新一次数据
     * 上传系统信息数据
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("/sysInfo")
    @ResponseBody
    public ResponseVo sysInfo(String sysInfo, PushEntity entity, HttpServletRequest request){
        entity.setServer(request.getLocalAddr());
        String  ip = RequestClientIpUtil.getIpAddr(request);
        String dir = FileWriter.getSysInfoDir();
        FileWriter.makeDirs(dir);
        sysInfo = Base64Util.decode(sysInfo);
        FileWriter.writeFile(dir+ip, sysInfo, false);
        return ResponseVo.responseOk(entity);
    }


    /**
     * 监控的失败数据的提交, 失败数据每天写一个文件
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("/faild")
    @ResponseBody
    public ResponseVo faildPush(PushEntity entity,String lentity, HttpServletRequest request){
        lentity = Base64Util.decode(lentity);
        writePush(lentity, request, "success");
        entity.setServer(request.getLocalAddr());
        return ResponseVo.responseOk(entity);
    }

    /**
     * 发送消息
     * @param messagesEntity
     */
    void sendMessages(MonitorMessagesEntity messagesEntity){
        messagesService.save(messagesEntity);
        sendEmail(messagesEntity);
        sendMobile(messagesEntity);
        sendDing(messagesEntity);
        sendWeixin(messagesEntity);
    }

    /**
     * 消息发送
     * @param messagesEntity
     */
    void sendMonitorMessages(MonitorMessagesEntity messagesEntity){
//        MonitorMessagesEntity messagesEntity = gson.fromJson(queueData, MonitorMessagesEntity.class);
        if (messagesEntity != null) {
            // 报警开关
            String alarmSwitch = redisUtil.get(MonitorCacheConfig.cacheAlarmSwitch);
            if (alarmSwitch != null && alarmSwitch.length() > 0 && alarmSwitch.equals("1")) {
                LOGGER.info("开始发送报警短信" + gson.toJson(messagesEntity));
                PushEntity entity = new PushEntity();
                if (messagesEntity.getValue() != null) {
                    entity.setIp(messagesEntity.getIp());
                    entity.setValue(messagesEntity.getValue());
                    entity.setName(messagesEntity.getIndexName());
                }
                if (entity != null) {
                    LOGGER.info("开始检查是否可以发送报警");
                    MonitorIndexAlarmEntity alarmEntity = checkAlarm(entity, messagesEntity.getIp());
                    LOGGER.info(alarmEntity);
                    if (!alarmEntity.getSendAlarm() && alarmEntity.getIsConfigure()) {
                        LOGGER.info("检查到不能发送报警信息" + gson.toJson(messagesEntity));
                        return;
                    }
                }
                sendMessages(messagesEntity);
            }
        }
    }

    /**
     * 从redis队列读取要发送的消息
     */
    void sendQueueMessages(){
        // 获取队列的大小
        Long queueLength = redisUtil.llen(MonitorCacheConfig.cacheAlarmQueueKey);

        // 从队列获取报警的数据
        for (int i=0 ; i< queueLength ;i++) {
            String queueData =  redisUtil.rpop(MonitorCacheConfig.cacheAlarmQueueKey);
            if (queueData !=null && queueData.length() > 0) {
                MonitorMessagesEntity messagesEntity = gson.fromJson(queueData, MonitorMessagesEntity.class);
                sendMonitorMessages(messagesEntity);
            }
        }
    }

    /**
     * 发送监控报警信息，
     * 从队列读取数据进行发送
     * 接收到消息后，从队列读取数据
     * @param request
     * @param monitorMessagesEntity
     * @return
     */
    @RequestMapping("/send/messages")
    @ResponseBody
    public ResponseVo send(MonitorMessagesEntity monitorMessagesEntity, HttpServletRequest request){
        PushEntity entity = new PushEntity();
        LOGGER.info(gson.toJson(monitorMessagesEntity));
        if (monitorMessagesEntity == null || monitorMessagesEntity.getMessages() == null || monitorMessagesEntity.getServerId() == null){
            entity.setMessages("从redis队列获取报警信息");
            sendQueueMessages();
        }else{
            entity.setMessages("直接发送报警信息");
            sendMonitorMessages(monitorMessagesEntity);
        }
        entity.setServer(request.getLocalAddr());
        return ResponseVo.responseOk(entity);
    }

    /**
     * @param messages
     * @return
     */
    String getMessages(String messages){
        messages = messages.replaceAll(" ", "&nbsp;");
        messages = messages.replaceAll("(\\n|\n|\r\n|\\r\\n|\r|\\r)", "</br>");
        messages = messages.replaceAll("(\\t|\t)", "&nbsp;&nbsp;&nbsp;");
        return messages;
    }



    /**
     * 发送微信消息
     * @param entity
     */
    void sendWeixin(MonitorMessagesEntity entity){
        if (entity.getWeixin()!=null && entity.getWeixin().length()>1) {
            MonitorMessageChannelEntity channelEntity = getChannelEntity(channelService,"weixin");
            WeixinUtil.sendWeixin(channelEntity, entity);
        }
    }

    /**
     * 发送消息到短信,通过配置的脚本发送
     * 加权限控制
     * @param entity
     */
    void sendMobile(MonitorMessagesEntity entity){
        if (entity.getMobile() != null && entity.getMobile().length() > 1) {
            MonitorMessageChannelEntity channelEntity = getChannelEntity(channelService,"mobile");
            String script = channelEntity.getChannelScript();
            String tempFile = tempDir + "sendsms.txt";
            FileWriter.writeFile(tempFile, script, false);

            try {
                runtime.exec("chmod a+x " + tempFile);
                String mobile = entity.getMobile();
                String content = entity.getMessages();
                content = content.replaceAll("(\\n|\n|\r\n|\\r\\n|\r|\\r)", "");
                content = content.replace("'", "\\'");
                content = "  '" + content + "' ";
                String cmd = tempFile + " " + mobile + content;
                ScriptUtil.run(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送报警到钉钉
     */
    void sendDing(MonitorMessagesEntity entity){
        if (entity.getDing() !=null && entity.getDing().length()>1){
            MonitorMessageChannelEntity channelEntity = getChannelEntity(channelService,"ding");
            MonitorMessagesEntity messagesEntity = new MonitorMessagesEntity();
            messagesEntity.setDing(entity.getDing().replaceAll(",","|"));
            messagesEntity.setMessages(entity.getMessages());
            DingUtil.sendDingDing(channelEntity, messagesEntity);
        }
    }

    /**
     * 发送邮件
     * @param entity
     */
    void sendEmail(MonitorMessagesEntity entity){
        if (entity.getEmail()!=null && entity.getEmail().length()>1) {
            globaltController = new MonitorGlobaltController();
            MonitorMessageChannelEntity channelEntity = getChannelEntity(channelService, "email");
            if (entity.getEmail() != null && entity.getEmail().length() > 1) {
                MailEntity mailEntity = getEmailEntity(channelService, entity);
                String template = channelEntity.getMailTemplate();
                String status = SevertityConfig.getSevertity(entity.getSevertityId(), false);
                String command = globaltController.getScriptName(redisUtil.getJedis(), entity.getScriptsId() + "");

                String server = "";
                server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + entity.getServerId());
                LOGGER.info("sendEmail server 1" + server);
                if (server == null || server.length() < 5) {
                    server = entity.getServerId() + "".replace("999", ".");
                }
                if (entity.getIpAddress()!=null){
                    LOGGER.info("获取到非自身IP地址.." + entity.getIpAddress());
                   server = entity.getIpAddress();
                }
                LOGGER.info("sendEmail server length " + server.length());
                LOGGER.info("sendEmail server 2" + server);
                String subject = command + " " + server + " " + status;
                template = template.replace("${STATUS}", SevertityConfig.getSevertity(entity.getSevertityId(), true));
                template = template.replace("${SERVER}", server);
                template = template.replace("${SUBJECT}", subject);
                template = template.replace("${ALARM_COUNT}", entity.getAlarmCount() - 1 + "");
                template = template.replace("${CONTENT}", getMessages(entity.getMessages()));
                template = template.replace("${TIME}", DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
                template = template.replace("${COMMAND}", command);
                mailEntity.setMessage(template);
                mailEntity.setSubject(subject);
                for (int i=0;i<5;i++) {
                    boolean send = MessagesChannelUtil.sendEmail(false, mailEntity);
                    if (send){
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param lentity
     * @param request
     */
    void writePush(String lentity, HttpServletRequest request, String writeType){
        if( lentity!=null ){
            Type type = new TypeToken<ArrayList<PushEntity>>() {}.getType();
            List<PushEntity> list = new Gson().fromJson(lentity, type);
            for(PushEntity entity1:list){
                if(entity1==null){
                    continue;
                }
                pushMonitorData(entity1,request, writeType);
            }
        }
    }


    /**
     *
     * @param entity
     */
    void writeReportDayData(PushEntity entity){
        if (Integer.valueOf(entity.getStatus()) > 1 ){
            MonitorReportDayEntity dayEntity = new MonitorReportDayEntity();
            dayEntity.setScriptsId(Integer.valueOf(entity.getScriptId()));
            dayEntity.setSeverityId(Integer.valueOf(entity.getStatus()));
            dayEntity.setAlarmTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
            dayEntity.setGroupsId(Integer.valueOf(entity.getGroupsName()));
            dayEntity.setServerId(Long.valueOf(entity.getServer()));
            dayEntity.setIndexName(entity.getGroups()+"."+entity.getName());
            dayEntity.setStartTime(dayEntity.getAlarmTime());
            SearchMap searchMap = new SearchMap();
            searchMap.put("serverId", dayEntity.getServerId());
            searchMap.put("scriptsId",Integer.valueOf(entity.getScriptId()));
            searchMap.put("groupsId", Integer.valueOf(entity.getGroupsName()));
            searchMap.put("indexName", entity.getGroups()+"."+entity.getName());
            List<MonitorReportDayEntity> result = reportDayService.getData(searchMap, "getIsMax");
            if (result==null || result.size()<1) {
                reportDayService.save(dayEntity);
            }else{
                reportDayService.update(dayEntity);
            }
        }
    }

    /**
     * 记录每个指标的服务器地址
     * @param entity
     * @param ip
     */
    void writeIndexHosts(PushEntity entity,String ip){
        // 拼接文件目录
        String dir = dataDir + separator + "graph" + separator +"index" +separator;
        dir = dir + entity.getGroups()+"."+entity.getName()+separator;
        dir = FileRender.replace(dir);
        FileWriter.makeDirs(dir);
        FileWriter.writeFile(dir+"id", entity.getScriptId(), false);
    }

    /**
     * 通过报警指标配置的报警发送
     * 对信息进行过滤
     * @param entity
     * @param ip
     */
    void alarmSet(PushEntity entity, String ip){
        try {
            MonitorIndexAlarmEntity alarmEntity = checkAlarm(entity, ip);
            if (alarmEntity.getSendAlarm()) {
                int alarmMax = 3;
                if (alarmEntity.getAlarmCount()!=null && alarmEntity.getAlarmCount().length()>0){
                    alarmMax = Integer.valueOf(alarmEntity.getAlarmCount());
                }
                String  key =  ip + "1" + alarmEntity.getIndexName()+"_alarm";
                String  keyTime =  ip + "1" + alarmEntity.getIndexName()+"_alarm_time";
                String  result = redisUtil.get(key);
                int alarmCount = 1;
                long maxTime = 600;
                long lastTime = System.currentTimeMillis() / 1000 ;
                if (result!=null&&result.length()>0){
                    String timeDate = redisUtil.get(keyTime);
                    if (timeDate!=null&&timeDate.length()>0){
                        lastTime = Long.valueOf(timeDate);
                    }
                    if (alarmEntity.getAlarmInterval()!=null&&alarmEntity.getAlarmInterval().length()>0){
                        maxTime = Long.valueOf(alarmEntity.getAlarmInterval())*60;
                    }
                    if (System.currentTimeMillis()/1000 - lastTime > maxTime ) {
                        alarmCount += Integer.valueOf(result);
                        redisUtil.setex(key, 43200, String.valueOf(Integer.valueOf(result) + 1));
                        redisUtil.setex(keyTime,43200, System.currentTimeMillis()/1000+"");
                    }else {
                        LOGGER.info("报警间隔太短" + lastTime);
                        return;
                    }
                }else{
                    redisUtil.setex(key,43200, "1");
                    redisUtil.setex(keyTime,43200, System.currentTimeMillis()/1000+"");
                }
                if (alarmCount> alarmMax){
                    LOGGER.info("已超过报警短信" + key);
                    return;
                }
                MonitorMessagesEntity messagesEntity = new MonitorMessagesEntity();
                String alarmMessages = "";
                messagesEntity.setSevertityId(Integer.valueOf(entity.getStatus()));
                messagesEntity.setScriptsId(Integer.valueOf(entity.getScriptId()));
                alarmMessages += "指标报警通知: ";
                alarmMessages += "\n 指标: " + entity.getName();
                alarmMessages += "\n value: " + entity.getValue();
                alarmMessages += "\n ip: " + entity.getIp();
                alarmMessages += "\n 时间: " + DateUtil.getDate(DateUtil.TIME_FORMAT);
                alarmMessages += "\n 违反规则: " + alarmEntity.getSendMessages();
                messagesEntity.setMessages(alarmMessages);
                LOGGER.info(gson.toJson(alarmEntity));
                if (alarmEntity.getAllGroups()!=null&&alarmEntity.getAllGroups().length()>0){
                    messagesEntity.setEmail(MessagesChannelUtil.getContact(alarmEntity.getAllGroups(), "email"));
                    messagesEntity.setDing(MessagesChannelUtil.getContact(alarmEntity.getAllGroups(), "ding"));
                    messagesEntity.setWeixin(MessagesChannelUtil.getContact(alarmEntity.getAllGroups(), "weixin"));
                    messagesEntity.setMobile(MessagesChannelUtil.getContact(alarmEntity.getAllGroups(), "mobile"));
                }else{
                    messagesEntity.setEmail(MessagesChannelUtil.getContact(alarmEntity.getEmailGroups(), "email"));
                    messagesEntity.setDing(MessagesChannelUtil.getContact(alarmEntity.getDingGroups(), "ding"));
                    messagesEntity.setWeixin(MessagesChannelUtil.getContact(alarmEntity.getWeixinGroups(), "weixin"));
                    messagesEntity.setMobile(MessagesChannelUtil.getContact(alarmEntity.getMobileGroups(), "mobile"));
                }

                LOGGER.info(gson.toJson(messagesEntity));
                sendEmail(messagesEntity);
                sendWeixin(messagesEntity);
                sendMobile(messagesEntity);
                sendDing(messagesEntity);
                messagesService.save(messagesEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("alarm错误", e);
        }
    }

    /**
     * 数据写入
     * @param entity
     * @param request
     */
    public void pushMonitorData(PushEntity entity, HttpServletRequest request, String writeType) {

        if (entity.getStatus().equals("0")){
            entity.setServer("1");
         }
        try {
            writeReportDayData(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        String ipAddr;
        String historyName = FileRender.replace(entity.getName());
        String name = FileRender.replace(entity.getScriptId() +"_"
                + FileRender.replace(entity.getStatus())) + "_"
                + entity.getGroups()+ "_"+entity.getName();

        String groups = FileRender.replace(entity.getGroups());
        String value = entity.getValue();
        // 获取客户端IP
        if (entity.getIp() == null || entity.getIp().length() < 10) {
            ipAddr = RequestClientIpUtil.getIpAddr(request);
        } else {
            ipAddr = FileRender.replace(entity.getIp());
        }
        // 报警发送处理
        alarmSet(entity, ipAddr);
        // 记录每个指标的服务器地址
        writeIndexHosts(entity, ipAddr);

        // 只将数据写入到文件
        if (name != null && value != null && value.length() > 0) {
            // 画图数据生成
            FileWriter.writeHistory(groups, ipAddr, historyName, value);

            if(writeType.equals("success")) {
                // 监控历史数据生成
                FileWriter.writeMonitorHistory( FileRender.replace(entity.getServer()), name, entity);
            }
        }
    }
}