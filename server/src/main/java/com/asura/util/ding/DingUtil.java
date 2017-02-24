package com.asura.util.ding;

import com.google.gson.Gson;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.util.HttpUtil;
import org.apache.log4j.Logger;

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

public class DingUtil {
    private static final Logger LOGGER = Logger.getLogger(DingUtil.class);
    private static Gson gson = new Gson();

    public static void   sendDingDing(MonitorMessageChannelEntity channelEntity, MonitorMessagesEntity messagesEntity) {
        try {


            DingEntity dingEntity = new DingEntity();
            String tokenUrl = "http://out.mappings.asura.com/2/oapi.dingtalk.com/gettoken?corpid=" + channelEntity.getDingCorpId() + "&corpsecret=" + channelEntity.getDingSecretId();
            String tokenData = HttpUtil.sendGet(tokenUrl);
            DingEntity dingEntity1 = gson.fromJson(tokenData, DingEntity.class);
            String token = dingEntity1.getAccess_token();
            System.out.println(token);
            String sendUrl = "http://out.mappings.asura.com/3/oapi.dingtalk.com/message/send?access_token=" + token;
            dingEntity.setAgentid(channelEntity.getDingAgentId() + "");
            dingEntity.setMsgtype("text");
            dingEntity.setTouser(messagesEntity.getDing());
            dingEntity.setToparty("");
            Map map = new HashMap();
            map.put("content", messagesEntity.getMessages());
            dingEntity.setText(map);
            String result = gson.toJson(dingEntity);
            LOGGER.info(result);
            LOGGER.info(HttpUtil.httpPostJson(sendUrl, result, "POST"));
        }catch (Exception e){
            LOGGER.error(e);
        }
    }


}
