package com.asura.agent.util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(HttpUtil.class);

    public static String sendPost(String url, String param){
        for (int i=0 ; i < 3; i++) {
            String result = sendPost(url, param, ""+i);
            if (result != null){
                return result;
            }
        }
        return "";
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *         发送请求的 URL
     * @param param
     *         请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String tid) {
        logger.info(tid);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(4000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            result = null;
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                result = null;
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param urls
     * @param file
     *
     * @throws MalformedURLException
     */
    public static void downloadNet(String urls, String file) {
        int byteRead;

        try {
            URL url = new URL(urls);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(file);

            byte[] buffer = new byte[1204];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            fs.close();
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }
}