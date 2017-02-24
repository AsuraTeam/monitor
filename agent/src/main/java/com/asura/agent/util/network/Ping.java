package com.asura.agent.util.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    static  String replace(String ip){
        String[] list = {" ","{", "`", "{", "&", ";"};
        for(int i=0;i<list.length;i++){
            ip = ip.replace(list[i], "");
        }
        return ip;
    }

    public static String execPing(String ip){
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
            command = "ping -c 60 -i 1 ";
        }else{
            // windows
            command = "ping -n 2 -w 1 ";
        }

        String result = "";
        try {
            process = runtime.exec(command + ip); // PING
            is = process.getInputStream(); // 实例化输入流
            isr = new InputStreamReader(is);// 把输入流转换成字节流
            br = new BufferedReader(isr);// 从字节中读取文本
            while ((line = br.readLine()) != null) {
                result += line +"\n";
                if (line.toUpperCase().contains("TTL")) {
                    is.close();
                    isr.close();
                    br.close();
                    break;
                }
            }
        } catch (IOException e) {
            result = e.toString();
        }
        return result;
    }


}
