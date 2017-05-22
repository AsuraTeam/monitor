package com.asura.agent.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.agent.conf.MonitorCacheConfig;
import com.asura.agent.controller.MonitorController;
import com.asura.agent.entity.PushServerEntity;
import com.asura.agent.thread.SocketThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class SocketSendUtil {

    public static final Logger logger = LoggerFactory.getLogger(SocketSendUtil.class);
    private static InetAddress server;
    private static ArrayList<InetAddress> serverList;
    private static int port;
    private static ArrayList<Integer> ports;
    private static RedisUtil redisUtil;
    private static Random random;

    /**
     * 获取服务器列表大小
     *
     * @return
     */
    public static int getServerListSize() {
        if (serverList != null) {
            return serverList.size();
        } else {
            return 0;
        }
    }

    /**
     * 先优先使用redis的
     */
    static void setServerList() {
        InetAddress addr;
        if (redisUtil == null) {
            redisUtil = new RedisUtil();
        }
        if (serverList == null ){
            serverList = new ArrayList<>();
        }
        String pushServers = redisUtil.get(MonitorCacheConfig.cachePushServer);
        if (pushServers != null && pushServers.length() > 0) {
            Type type = new TypeToken<ArrayList<PushServerEntity>>() {
            }.getType();
            List<PushServerEntity> serverEntities = new Gson().fromJson(pushServers, type);
            for (PushServerEntity serverEntity : serverEntities) {
                try {
                    addr = InetAddress.getByName(serverEntity.getIp());
                    if (!serverList.contains(addr)) {
                        logger.info("添加push服务Redis " + addr);
                        serverList.add(addr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("获取push server失败 1", e);
                }
            }
        }
    }

    /**
     * 随机链接服务器
     *
     * @return
     */
    public static InetAddress getServer(InetAddress address) {
        if (MonitorController.getErrorNumber()){
            return null;
        }
        if (serverList != null && serverList.size() > 0) {
            if (address == null) {
                random = new Random();
                int id = random.nextInt(serverList.size());
                return serverList.get(id);
            }
            // 去除坏了的连接
            for (int i = 0; i < serverList.size(); i++) {
                if (serverList.get(i) != address) {
                    return serverList.get(i);
                }
            }
        } else {
            serverList = new ArrayList<>();
            setServerList();
            return getServer(null);
        }
        return null;
    }

    /**
     * @param addr
     *
     * @return
     */
    static boolean checkServerValid(String addr) {
        String port = redisUtil.get(MonitorCacheConfig.getCachePushServerPort.concat(addr.toString().replace("/", "")).trim());
        if (port.length() > 1) {
            return true;
        }
        return false;
    }

    /**
     * 每10分钟重新获取一次
     * 设置上传服务器
     */
    public static void setServer() {
        serverList = new ArrayList<>();
        RedisUtil redisUtil = new RedisUtil();
        String key = MonitorCacheConfig.cachePushServer;
        String addrs = redisUtil.get(key);
        if (addrs != null && addrs.length() > 8) {
            Type type = new TypeToken<ArrayList<PushServerEntity>>() {
            }.getType();
            List<PushServerEntity> list = new Gson().fromJson(addrs, type);
            for (PushServerEntity entity : list) {
                try {
                    InetAddress address = InetAddress.getByName(entity.getIp());
                    if (!serverList.contains(address)) {
                        if (SocketThread.sendData("[{}]", address, 50329) && checkServerValid(address.toString())) {
                            logger.info("获取到PUSH服务器地址" + entity.getIp());
                            serverList.add(address);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static void init() {
        if (redisUtil == null) {
            redisUtil = new RedisUtil();
        }
        if (serverList == null) {
            setServerList();
        }
        if (random == null) {
            random = new Random();
        }
        try {
            if (ports == null) {
                ports = new ArrayList();
                for (int i = 50000; i < 50300; i++) {
                    ports.add(i);
                }
            }
        } catch (Exception e) {
            logger.info("ERROR:获取服务地址失败" + "server");
        }
    }

    /**
     * @param data
     *
     * @return
     */
    public static void sendData(String data) {
        init();
        server = getServer(null);
        if (server == null) {
            logger.error("获取push服务失败..");
            return;
        }
        port = ports.get(random.nextInt(299));
        SocketThread thread = new SocketThread(data, server, port);
        thread.start();
        long start = System.currentTimeMillis() / 1000;
        while (1 == 1) {
            if (!thread.isAlive()) {
                break;
            }
            if (System.currentTimeMillis() / 1000 - start > 2) {
                logger.error("udp线程超时");
                if (thread.isAlive()) {
                    try {
                        thread.interrupt();
                    } catch (Exception e) {
                    }
                }
                break;
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

}
