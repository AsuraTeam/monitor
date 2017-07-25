package com.asura.agent.util;

import com.asura.agent.conf.MonitorCacheConfig;
import com.asura.agent.controller.MonitorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
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

public class HttpSendUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpSendUtil.class);
    private static String url;
    public static InetAddress server;
    private static Map<String, Long> pushServerTime;

    /**
     * 初始化server
     */
    static void setServer(InetAddress failServer){
        RedisUtil redisUtil = new RedisUtil();
        InetAddress address = SocketSendUtil.getServer(failServer);
        if (null == address){
            return;
        }
        StringBuilder servers = new StringBuilder();
        String ip = address.toString().replace("/","");
        String port = redisUtil.get(MonitorCacheConfig.getCachePushServerPort.concat(ip).trim());
        server = address;
        if (MonitorController.checkSecurityServer(ip)) {
            servers.append("http://")
                    .append(ip)
                    .append(":")
                    .append(port)
                    .append("/");
            url = servers.toString();
            logger.info(url);
        }
    }

    /**
     *
     * @param apiUrl
     * @param param
     * @return
     */
    public static String sendPost(String apiUrl, String param){
        if (null == url){
            SocketSendUtil.init();
            SocketSendUtil.setServer();
            setServer(null);
            pushServerTime = new HashMap<>();
            pushServerTime.put("time", System.currentTimeMillis() / 1000);
            MonitorUtil.info("初始化pushServer");
        }
        MonitorUtil.info("设置pushserver还剩" + (System.currentTimeMillis() / 1000  - pushServerTime.get("time")));
        if (System.currentTimeMillis() / 1000  - pushServerTime.get("time") > 120){
            setServer(null);
            pushServerTime.put("time", System.currentTimeMillis() / 1000);
            logger.info("重新设置push server" + server.toString());
        }
        for (int i=0 ; i < 3; i++) {
            String result = HttpUtil.sendPost(url, param, ""+i);
            if (null != result){
                return HttpUtil.sendPost(url.concat(apiUrl), param);
            }else{
                setServer(server);
            }
        }
        return "";
    }

}
