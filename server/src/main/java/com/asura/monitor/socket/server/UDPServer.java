package com.asura.monitor.socket.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.PushServerEntity;
import com.asura.monitor.graph.quartz.PushServerQuartz;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.socket.thread.StartUDPServerThread;
import com.asura.util.CheckUtil;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

import static com.asura.monitor.configure.conf.MonitorCacheConfig.getCachePushServerPort;

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
@ComponentScan
public class UDPServer {

    public static final Logger LOGGER = LoggerFactory.getLogger(UDPServer.class);
    private static Map<String, Long> threadMap;
    // 存放处理慢的时间
    private static Map<String, Long> slowMap;
    // 存储一个队列大小，用来存储处理慢的请求
    private static Queue slowQueue;
    // 存储监控数据
    private static Map<String, Long> monitorMap;


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
        LOGGER.info("获取到本机ip地址 " + addr);
        if (addr.contains("127.0.0.1")){
            LOGGER.error("请绑定host到你的服务器IP地址");
        }else{
            ArrayList<PushServerEntity> serverEntities = new ArrayList<>();
            RedisUtil redisUtil = new RedisUtil();
            String key = MonitorCacheConfig.cachePushServer;
            String  addrs = redisUtil.get(key);
            if (addrs != null && addrs.length() > 8){
                Type type = new TypeToken<ArrayList<PushServerEntity>>() {
                }.getType();
                List<PushServerEntity> list = new Gson().fromJson(addrs, type);
                Long update ;
                for (PushServerEntity entity: list){
                    update = entity.getUpdate();
                    // 排除5分钟没有更新的机器
                    if (System.currentTimeMillis() / 1000 - update > 30){
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
            LOGGER.info("set push server" + serverData);
            redisUtil.set(key, serverData);
            setPort(addr);

        }
    }

    /**
     * 设置报警开关
     */
    static void setAlarmSwitch(){
        RedisUtil redisUtil = new RedisUtil();
        String key = MonitorCacheConfig.cacheAlarmSwitch;
        String result = redisUtil.get(key);
        if (! CheckUtil.checkString(result)){
            redisUtil.set(key, "1");
        }
    }



    /**
     * 初始化udp端口
     */
    public static void startPort() {
        if (threadMap == null){
            setAlarmSwitch();
            threadMap = new HashMap<>();
            threadMap.put("counter", 0L);
            slowMap = new HashMap<>();
            slowMap.put("slow", 0L);
            slowQueue = new LinkedTransferQueue();
            monitorMap = new HashMap<>();
            monitorMap.put("time", System.currentTimeMillis());
        }
        try {
            setPushServer();
        }catch (Exception e){
            e.printStackTrace();
        }

        for (int port= 50000 ; port < 50300; port++) {
            StartUDPServerThread thread = new StartUDPServerThread(threadMap, port, slowMap, slowQueue, monitorMap);
            thread.start();
        }
    }

    /**
     * 获取本机监听端口
     */
    static int getPort() {
        int port = 8081;
        try {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lstThreads = new Thread[noThreads];
            currentGroup.enumerate(lstThreads);
            String reg;
            for (int i = 0; i < noThreads; i++) {
                if (lstThreads[i].getName().contains("http-")) {
                    reg = lstThreads[i].getName();
                    String number = reg.replaceAll("[^(0-9)]", " ");
                    number = number.replace("  "," ");
                    number = number.replace("  "," ");
                    String[] ports = number.split(" ");
                    if (ports.length > 1) {
                        for (int j=0; j < ports.length; j++) {
                            try {
                                port = Integer.valueOf(ports[j]);
                                LOGGER.info("获取到程序端口号:" + reg + " " + port);
                                break;
                            }catch (Exception e){
                                continue;
                            }
                        }
                    }
                }
            }
            return port;
        } catch (Exception e) {
            LOGGER.info("获取端口错误:", e);
        }
        return port;
    }

    /**
     *
     * @param server
     */
    static void setPort(String server){
        String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "portLock";
        if (FileRender.readFile(tempPath).length() < 5) {
            FileWriter.writeFile(tempPath, System.currentTimeMillis() / 1000 + "", false);
            int port = getPort();
            LOGGER.info("开始设置服务器端口");
            RedisUtil redisUtil = new RedisUtil();
            redisUtil.setex(getCachePushServerPort + server, 2000, port + "");
            LOGGER.info("设置端口完成");
        }else{
            PushServerQuartz.clearCache(tempPath, 1800);
        }
    }
}
