package com.asura.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import sun.net.www.protocol.https.Handler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.support.WebContentGenerator.METHOD_GET;

public class HttpUtil {
    private static final String DEFAULT_CHARSET = "utf-8";
    private static Logger log = Logger.getLogger(HttpUtil.class);

    /**
     * post方式请求服务器(https协议)
     *
     * @param url
     *            请求地址
     * @param content
     *            参数
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] post(String url, String content, String charset)
            throws  Exception
             {
        URL console = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) console.openConnection();

        //conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

        conn.setDoOutput(true);

        conn.setAllowUserInteraction(true);
        conn.setRequestMethod("POST");
       // System.out.println(conn.getRequestMethod());
        conn.setUseCaches(false);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return outStream.toByteArray();
        }
        return null;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param url
     *            请求地址
     * @param content
     *            参数
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String  httpPostJson(String url, String content, String method) throws IOException{
        URL console = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) console.openConnection();
        //conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.setAllowUserInteraction(true);
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes("UTF-8"));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is ;
        try {
            is = conn.getInputStream();
        }catch (Exception e){
            return e.toString();
        }
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len ;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            byte[] r = outStream.toByteArray();
            String result = new String(r, "UTF-8");
            return result;
        }
        conn.disconnect();
        return null;
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param url
     *            请求地址
     * @param content
     *            参数
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String  postJson(String url, String content, String method)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                new SecureRandom());
        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());

        //conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

        conn.setDoOutput(true);

        conn.setAllowUserInteraction(true);
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes("UTF-8"));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is ;
        try {
            is = conn.getInputStream();
        }catch (Exception e){
            return e.toString();
        }
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len ;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            byte[] r = outStream.toByteArray();
            String result = new String(r, "UTF-8");
            return result;
        }
        conn.disconnect();
        return null;
    }

    private static HttpsURLConnection getConnection(URL JNurl, String method, String ctype)
            throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) JNurl.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        //conn.setRequestProperty("Accept", "text/plain");
        //conn.setRequestProperty("User-Agent", "stargate");
        //conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }


    protected static String getResponseAsString(HttpsURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    public static String request(String url, String ctype) throws Exception {
        int connectTimeout = 1000;
        int readTimeout = 1000;
        HttpsURLConnection conn = null;
        String rsp = null;
        try {
            try{
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                conn = getConnection(new URL(null,url,new Handler()), METHOD_GET, ctype);
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }catch(Exception e){
                log.error("doGet GET_CONNECTOIN_ERROR, URL = " + url, e);
                throw e;
            }
            try{
                rsp = getResponseAsString(conn);
            }catch(IOException e){
                log.error("doGet REQUEST_RESPONSE_ERROR, URL = " + url, e);
                throw e;
            }

        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
        @Override
        public void checkClientTrusted(
                X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
        @Override
        public void checkServerTrusted(
                X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }


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
    public static String sendPost(String url, String param) {
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
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}

