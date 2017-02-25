package com.asura.agent.thread;

import com.asura.agent.util.SocketSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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

public class SocketThread extends Thread {

    public static final Logger logger = LoggerFactory.getLogger(SocketThread.class);

    // 要发送的数据
    private String data;
    // 接收数据服务端
    private InetAddress server;
    // 接收数据端口
    private int port;

    public SocketThread(String data, InetAddress server, int port) {
        this.data = data;
        this.server = server;
        this.port = port;
    }

    /**
     * 发送udp数据到服务端
     * @param datas
     * @param address
     * @param port
     */
    public static boolean sendData(String datas, InetAddress address, int port){
        try {
            byte[] data = datas.getBytes();//将字符串转换为字节数组
            //创建数据报
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            //创建DatagramSocket，实现数据发送和接收
            DatagramSocket socket = new DatagramSocket();
            //向服务器端发送数据报
            socket.send(packet);
            socket.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void run(){
        boolean isSendOk = sendData(data, server, port);
        if (isSendOk) {
            this.interrupt();
        }else{
            InetAddress inetAddress;
            inetAddress = server;
            for (int i=0; i < SocketSendUtil.getServerListSize(); i++) {
                inetAddress = SocketSendUtil.getServer(inetAddress);
                isSendOk = sendData(data, inetAddress, port);
                if (isSendOk){
                    logger.info("重试Socket发送" + inetAddress.toString() + " ok ");
                    break;
                }
            }
        }
    }
}
