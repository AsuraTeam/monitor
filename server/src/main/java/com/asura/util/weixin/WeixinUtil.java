package com.asura.util.weixin;

import com.google.gson.Gson;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.util.HttpUtil;

import java.util.HashMap;
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

public class WeixinUtil {

    private static Gson gson = new Gson();
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WeixinUtil.class);

    /**
     * 企业版获取token
     * @param CorpID
     * @param SecretId
     * @return
     */
    static String  getToken(String CorpID, String SecretId) {
        String url = "http://out.mappings.asura.com/4/qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+CorpID+"&corpsecret="+SecretId;
        String tokenData ;
        try {
            tokenData = HttpUtil.sendGet(url);
            logger.info(tokenData);
        }catch (Exception e){
            logger.error(e);
            tokenData = "";
        }
        WeixinEntity weixinEntity = gson.fromJson(tokenData, WeixinEntity.class);
        logger.info(gson.toJson(weixinEntity));
        return weixinEntity.getAccess_token();
    }

    /**
     * 发送微信消息
     * @param channelEntity
     * @param monitorMessagesEntity
     */
    public static void sendWeixin(MonitorMessageChannelEntity channelEntity, MonitorMessagesEntity monitorMessagesEntity){
        String token = getToken(channelEntity.getWeixinCorpId(), channelEntity.getWeixinSecretId());
        String sendUrl =  "http://out.mappings.asura.com/5/qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token;
        WeixinEntity entity = new WeixinEntity();
        entity.setTouser(monitorMessagesEntity.getWeixin().replace(",","|"));
        entity.setMsgtype("text");
        entity.setAccess_token(token);
        entity.setAgentid(channelEntity.getWeixinAppId());
        Map map = new HashMap();
        map.put("content", monitorMessagesEntity.getMessages());
        entity.setText(map);
        logger.info(gson.toJson(entity).replace("\\u003e",">"));
        try {
            String result = HttpUtil.httpPostJson(sendUrl, gson.toJson(entity).replace("\\u003e", ">"),"POST");
            logger.info(result);
        }catch (Exception e){
            logger.error(e);
        }
    }

}
