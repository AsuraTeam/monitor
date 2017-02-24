/**
 * @FileName: RequestClientIpUtil.java
 * @Package: com.asura.sms.archetype.commons.util
 * @author sence
 * @created 4/13/2016 5:57 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class RequestClientIpUtil {


    /**
     * 获取真实客户端的IP，可防止反向代理软件代理IP，必须要求反向代理软件传递真实的IP到服务端
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
