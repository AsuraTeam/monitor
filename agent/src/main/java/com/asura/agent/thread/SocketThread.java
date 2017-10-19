package com.asura.agent.thread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.agent.entity.PushEntity;
import com.asura.agent.util.MonitorUtil;
import com.asura.agent.util.SocketSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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
            logger.info("数据包大小" + datas.length());
            e.printStackTrace();
            return false;
        }
    }

    void sendSplitData(String data){
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

    /**
     *
     * @param lentity
     * @return
     */
    public static List<PushEntity>  getPushEntity(String lentity){
        if( null != lentity  ) {
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(lentity, type);
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * @param gson
     */
    void sendSplitData(List<PushEntity> list, Gson gson){
        sendSplitData(gson.toJson(list));
        MonitorUtil.info("通过数据切割发送数据 " + list.size());
    }

    @Override
    public void run(){
        Gson gson = new Gson();

        if (data.length() > 65534){
            int split = (data.length() / 50000)+2;
            List<PushEntity> newList = new ArrayList<>();
            List<PushEntity> list = getPushEntity(data);
            int counter = 0;
            for (int i=0; i<list.size(); i++){
                if (i % (Integer.valueOf(list.size() / split)) == 0) {
                    sendSplitData(newList, gson);
                    counter = i;
                    newList = new ArrayList<>();
                }else{
                    newList.add(list.get(i));
                }
            }
            if (counter < list.size()){
                newList = new ArrayList<>();
                for (int i= counter; i<list.size();i++){
                    newList.add(list.get(i));
                }
                sendSplitData(newList,gson);
            }
        }else {
            sendData(data, server, port);
        }
    }
}
