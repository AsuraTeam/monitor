package com.asura.agent.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class IpUtil {

    /**
     * 获取本机的IP地址
     * @return
     */
    public static HashSet<String> getHostIP() {
        HashSet ipAdd = new HashSet();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        if(isIpv4(inetAddress.getHostAddress())) {
                            ipAdd.add(inetAddress.getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return ipAdd;
    }

    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }
//

    /**
     * ip转成
     * @param ip
     * @return
     */
    public  static  String ipToLong(String ip){
        ip = ip.replace(".","999");
        return ip;
    }

    /**
     * 获取 主机名
     * @return
     */
    public static String getHostname(){
        String windows = System.getenv("COMPUTERNAME");
        String linux = System.getenv("HOSTNAME");
        if (windows!=null&&windows.length()>0){
            return windows;
        }else {
            return linux;
        }
    }

    /**
     *
     * @return
     */
    public static String getServerPort(){
        return System.getProperty("server.port");
    }

}
