package com.asura.monitor.socket.thread;

import com.google.gson.Gson;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.util.MonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

public class StartUDPServerThread extends Thread {

    public static final Logger logger = LoggerFactory.getLogger(StartUDPServerThread.class);
    private Map<String, Long> threadMap;
    private int port;
    private Map<String, Long> slowMap;
    private Queue<Long> slowQueue;
    private String localIp;
    private Gson gson;
    private Map<String, Long> monitorMap;

    public StartUDPServerThread(Map<String, Long> threadMap, int port, Map<String, Long> slowMap, Queue slowQueue, Map<String, Long> monitorMap) {
        this.threadMap = threadMap;
        this.slowMap = slowMap;
        this.port = port;
        this.slowQueue = slowQueue;
        this.gson = new Gson();
        this.monitorMap = monitorMap;
        try {
            this.localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            logger.error("获取本机IP地址失败，请绑定host后重启服务", e);
        }
    }

    /**
     * 将服务处理时间上传到监控系统, 每1分钟上传一次
     *
     * @param time
     */
    public void serverMonitorPush(long time) {
        if (System.currentTimeMillis() / 1000 - monitorMap.get("time") / 1000 >= 60) {
            List<PushEntity> list = new ArrayList<>();
            PushEntity pushEntity = new PushEntity();
            pushEntity.setIp(localIp);
            pushEntity.setValue(String.valueOf(time));
            pushEntity.setGroups("monitor");
            pushEntity.setStatus("1");
            pushEntity.setScriptId("0");
            pushEntity.setName("monitor.push.time");
            list.add(pushEntity);
            String data = gson.toJson(list);
            MonitorUtil.writePush(data, "success", localIp);
            monitorMap.put("time", System.currentTimeMillis());
        }
    }

    void remove(){
        long remove = slowQueue.poll();
        slowMap.put("slow", slowMap.get("slow") - remove);
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet;
            Long counter;
            String ip;
            Long start;
            Long end;
            Long time;
            Long slowTime;
            StringBuffer sb;
            byte[] data;
            logger.info("***服务器端启动，等待发送数据*** " + port);
            while (true) {
                data = new byte[1024000];//创建字节数组，指定接收的数据包的大小
                packet = new DatagramPacket(data, data.length);
                socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
                start = System.currentTimeMillis();
                data = packet.getData();
                ip = packet.getAddress().toString().replace("/", "").trim();
                // 最近1000次请求都大于1000毫秒，那么久抛弃
                slowTime = slowMap.get("slow") / 1000;
                if (slowQueue.size() == 1000) {
                    if (slowTime > 1000) {
                        logger.error("执行太慢啦，我不执行啦..1000次大于" + slowTime + " " + slowQueue.size());
                        remove();
                        continue;
                    }
                }
                MonitorUtil.writePush(new String(data, 0, packet.getLength()), "success", ip);
                data = null;
                end = System.currentTimeMillis();
                time = end - start;
                counter = threadMap.get("counter") + 1;
                threadMap.put("counter", counter);
                if (slowQueue.size() < 1000) {
                    slowQueue.add(time);
                    slowMap.put("slow", slowMap.get("slow") + time);
                } else {
                   remove();
                }
                // 每100次打印一条日志
                if (counter % 100 == 0) {
                    sb = new StringBuffer();
                    sb.append("次数: ").append(counter).append(" 平均").append(slowTime).append("ms");
                    logger.info(sb.toString());
                    serverMonitorPush(slowTime);
                }
            }
        } catch (Exception e) {
            logger.error("UDP线程错误", e);
        }
    }
}
