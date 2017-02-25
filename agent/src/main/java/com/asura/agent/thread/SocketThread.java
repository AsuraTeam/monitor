package com.asura.agent.thread;

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
    public static void sendData(String datas, InetAddress address, int port){
        try {
            byte[] data = datas.getBytes();//将字符串转换为字节数组
            //创建数据报
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            //创建DatagramSocket，实现数据发送和接收
            DatagramSocket socket = new DatagramSocket();
            //向服务器端发送数据报
            socket.send(packet);
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        sendData(data, server, port);
        this.interrupt();
    }
}
