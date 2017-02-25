package com.asura.monitor.socket.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.PushServerEntity;
import com.asura.monitor.socket.thread.StartUDPServerThread;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class UDPServer {

    public static final Logger logger = LoggerFactory.getLogger(UDPServer.class);
    private static Map<String, Long> threadMap;

    /**
     *
     * @param ip
     * @param update
     * @param serverEntities
     * @return
     */
    static ArrayList<PushServerEntity> setPushServer(String ip, Long update,   ArrayList<PushServerEntity> serverEntities){
        PushServerEntity pushServerEntity = new PushServerEntity();
        pushServerEntity.setIp(ip);
        pushServerEntity.setUpdate(update);
        serverEntities.add(pushServerEntity);
        return serverEntities;
    }

    /**
     * 设置push的地址
     * @throws UnknownHostException
     */
    public static void setPushServer() throws UnknownHostException {
        String addr = InetAddress.getLocalHost().getHostAddress();
        logger.info("获取到本机ip地址 " + addr);
        if (addr.contains("127.0.0.1")){
            logger.error("请绑定host到你的服务器IP地址");
        }else{
            ArrayList<PushServerEntity> serverEntities = new ArrayList<>();
            RedisUtil redisUtil = new RedisUtil();
            String key = MonitorCacheConfig.cachePushServer;
            String  addrs = redisUtil.get(key);
            if (addrs != null && addrs.length()>8){
                Type type = new TypeToken<ArrayList<PushServerEntity>>() {
                }.getType();
                List<PushServerEntity> list = new Gson().fromJson(addrs, type);
                Long update ;
                for (PushServerEntity entity: list){
                    update = entity.getUpdate();
                    // 排除5分钟没有更新的机器
                    if (System.currentTimeMillis() / 1000 - update > 300){
                        continue;
                    }
                    if (entity.getIp().equals(addr)){
                        continue;
                    }
                    setPushServer(entity.getIp(), entity.getUpdate(), serverEntities);
                }
            }
            setPushServer(addr, System.currentTimeMillis() / 1000, serverEntities);
            Gson gson = new Gson();
            String serverData = gson.toJson(serverEntities);
            logger.info("set push server" + serverData);
            redisUtil.set(key, serverData);
        }
    }

    /**
     * 初始化udp端口
     */
    public static void startPort() {
        if (threadMap == null){
            threadMap = new HashMap<>();
            threadMap.put("counter", 0L);
        }

        try {
            setPushServer();
        }catch (Exception e){
            e.printStackTrace();
        }

        for (int port= 50000 ; port < 50300; port++) {
            StartUDPServerThread thread = new StartUDPServerThread(threadMap, port);
            thread.start();
        }
    }
}
