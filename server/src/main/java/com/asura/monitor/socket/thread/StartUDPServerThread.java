package com.asura.monitor.socket.thread;

import com.asura.monitor.util.MonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
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

public class StartUDPServerThread extends Thread{

    public static final Logger logger = LoggerFactory.getLogger(StartUDPServerThread.class);
    private  Map<String, Long> threadMap;
    private int port;

    public StartUDPServerThread(Map<String, Long> threadMap, int port) {
        this.threadMap = threadMap;
        this.port = port;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet ;
            Long counter;
            String ip;
            byte[] data ;
            logger.info("***服务器端启动，等待发送数据*** " + port);
            while (true) {
                data = new byte[1024000];//创建字节数组，指定接收的数据包的大小
                packet = new DatagramPacket(data, data.length);
                socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
                data = packet.getData();
                ip = packet.getAddress().toString().replace("/", "").trim();
                MonitorUtil.writePush(new String(data, 0, packet.getLength()), "success", ip);
                data = null;
                counter = threadMap.get("counter") + 1;
                logger.info("连接次数：" + counter + " " + port + " "+ ip);
                threadMap.put("counter", counter);
            }
        } catch (Exception e) {
            logger.error("UDP线程错误" ,e);
        }
    }
}
