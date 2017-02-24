package com.asura.monitor.configure.util;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MonitorContactGroupEntity;
import com.asura.monitor.configure.entity.MonitorContactsEntity;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.util.RedisUtil;
import com.asura.util.email.MailEntity;

import java.util.HashSet;

import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheContactGroupKey;
import static com.asura.monitor.configure.conf.MonitorCacheConfig.cacheContactKey;
import static com.asura.util.email.MailUtil.sendMail;

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

public class MessagesChannelUtil {

    /**
     *
     * @param type
     *  email
     *  mobile
     *  ding
     *  weixin
     * @return
     */
    public  static MonitorMessageChannelEntity getChannelEntity(MonitorMessageChannelService channelService, String type){
        Gson gson = new Gson();
        RedisUtil redisUtil = new RedisUtil();
        String result = redisUtil.get(MonitorCacheConfig.cacheChannelKey+type);
        MonitorMessageChannelEntity channelEntity;
        if (result != null && result.length()>0){
            channelEntity = gson.fromJson(result, MonitorMessageChannelEntity.class);
        }else {
            SearchMap map = new SearchMap();
            map.put("channelTp", type);
            PagingResult<MonitorMessageChannelEntity> result1 = channelService.findAll(map, PageResponse.getPageBounds(2,1),
                    "selectByAll");
            channelEntity = result1.getRows().get(0);
        }
        return channelEntity;
    }

    /**
     *
     * @param send
     * @param mailEntity
     * @return
     */
   public static boolean sendEmail(boolean send, MailEntity mailEntity){
        for (int i = 0; i < 5; i++) {
            if (sendMail(mailEntity)) {
                send = true;
                break;
            }
        }
       return send;
    }

    /**
     *
     * @param channelService
     * @param entity
     */
   public static MailEntity getEmailEntity(MonitorMessageChannelService channelService, MonitorMessagesEntity entity) {
        MonitorMessageChannelEntity channelEntity = getChannelEntity(channelService, "email");
            MailEntity mailEntity = new MailEntity();
            mailEntity.setAuth(channelEntity.getSmtpAuth().equals("1"));
            mailEntity.setHost(channelEntity.getSmtpServer());
            mailEntity.setReceiver(entity.getEmail());
            mailEntity.setSender(channelEntity.getSmtpSender());
            mailEntity.setPassword(channelEntity.getSmtpPass());
            mailEntity.setUsername(channelEntity.getSmtpUser());

        return mailEntity;
    }

    /**
     * @param contactsEntity
     * @param type
     *
     * @return
     */
    public static HashSet getContact(MonitorContactsEntity contactsEntity, String type, HashSet contactSet) {
        switch (type) {
            case "mobile":
                contactSet.add(contactsEntity.getMobile());
                break;
            case "email":
                contactSet.add(contactsEntity.getMail());
                break;
            case "weixin":
                contactSet.add(contactsEntity.getMobile());
                break;
            case "ding":
                contactSet.add(contactsEntity.getNo());
                break;
        }
        return contactSet;
    }

    /**
     * 获取联系人
     * @return
     */
    public static  String getContact(String groups, String type) {
        Gson gson = new Gson();
        RedisUtil redisUtil = new RedisUtil();
        if (groups.length() < 1) {
            return "";
        }
        String[] g = groups.split(",");
        String contact = "";
        for (String group : g) {
            if (group.length() < 1) {
                continue;
            }
                String result = redisUtil.get(cacheContactGroupKey + group);
                if (result != null) {
                    MonitorContactGroupEntity entity = gson.fromJson(result, MonitorContactGroupEntity.class);
                    contact = entity.getMember();
                }
        }
        HashSet<String> contactSet = new HashSet();
        String[] contacts = contact.split(",");
        MonitorContactsEntity entity = new MonitorContactsEntity();
        for (String c : contacts) {
                String result = redisUtil.get(cacheContactKey + c);
                if (result != null) {
                    entity = gson.fromJson(result, MonitorContactsEntity.class);
                }
            contactSet = getContact(entity, type, contactSet);
        }

        String result = "";
        for (String c : contactSet) {
            result += c + ",";
        }
        return result.substring(0, result.length() - 1);
    }
}
