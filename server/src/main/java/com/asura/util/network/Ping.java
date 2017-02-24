package com.asura.util.network;

import com.asura.framework.base.paging.SearchMap;
import com.asura.monitor.graph.thread.CommentThread;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 实现ping
 * @author zhaoyun
 * @version 1.0
 * @since 1.0
 */
public class Ping extends Thread{

    private ArrayList<String> ip ;
    private ArrayList active = new ArrayList();
    private ArrayList shutdown = new ArrayList();
    private  CmdbResourceServerService service;
    private  CmdbResourceServerEntity entity;

    private  CmdbResourceServerEntity temp = new CmdbResourceServerEntity();

    public Ping(ArrayList<String> ip, CmdbResourceServerService service, CmdbResourceServerEntity entity){
        this.ip = ip;
        this.service = service;
        this.entity = entity;
    }

    public Ping(){
    }

    public void run(){
        CommentThread.sleep();
        ping(this.ip);
        try {
            super.clone();
            super.interrupt();
        }catch (Exception e){

        }
    }

    public String replace(String ip){
        String[] list = {" ","{", "`", "{", "&", ";"};
        for(int i=0;i<list.length;i++){
            ip = ip.replace(list[i], "");
        }
        return ip;
    }

    public int execPing(String ip){
        ip = replace(ip);
        Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
        Process process = null; // 声明处理类对象
        String line = null; // 返回行信息
        InputStream is = null; // 输入流
        InputStreamReader isr = null; // 字节流
        BufferedReader br = null;
        String file = System.getProperty("file.separator");
        String command;
        if(file.equals("/")){
            // linux
            command = "ping -c 2 -w 1 ";
        }else{
            // windows
            command = "ping -n 2 -w 1 ";
        }

        int res = 0;// 结果
        try {
            process = runtime.exec(command + ip); // PING
            is = process.getInputStream(); // 实例化输入流
            isr = new InputStreamReader(is);// 把输入流转换成字节流
            br = new BufferedReader(isr);// 从字节中读取文本
            while ((line = br.readLine()) != null) {
                if (line.toUpperCase().contains("TTL")) {
                    res = 1;
                    is.close();
                    isr.close();
                    br.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = 0;
        }
        return res;
    }

    public  void ping(ArrayList<String> ip) {
        for(int i=0;i<ip.size();i++) {
            String ipadd = ip.get(i);
            if(ipadd==null){continue;}
            int res = execPing(ipadd);
            if (res == 1) {
                active.add(ipadd);
            } else {
                shutdown.add(ipadd);
            }
        }
        SearchMap map ;
        map = new SearchMap();
        if(active.size()>0) {
            System.out.println(active.size());
            map.put("status", 1);
            map.put("hosts", active);
            service.updatePing(map);
        }

        if(shutdown.size()>0) {
            System.out.println(shutdown.size());
            map.put("status", 0);
            map.put("hosts", shutdown);
            service.updatePing(map);
        }
    }
}
