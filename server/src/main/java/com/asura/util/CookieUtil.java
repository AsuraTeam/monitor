/**
 * @FileName: CookieUtil.java
 * @Package: com.asura.sms.archetype.commons.util
 * @author sence
 * @created 4/13/2016 5:27 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.util;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.util.Check;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Cookie的操作工具类</p>
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
public class CookieUtil {

    /**
     * 依据cookie的名称得到cookie 内的Object
     *
     * @param cookieName
     * @param cookies
     * @param clazz
     * @return
     * @author liushengqi
     * @created 2014年5月21日 下午4:28:05
     */
    public static <T> T getCookieValue(String cookieName, Cookie[] cookies, Class<T> clazz) {
        Cookie cookie = getCookie(cookieName, cookies);
        if (cookie == null || cookies == null) {
            return null;
        }
        return JSON.parseObject(cookie.getValue(), clazz);
    }

    /**
     * 依据cookie的名称得到cookie 内的Object
     *
     * @param cookieName
     * @param clazz
     * @return
     * @author liushengqi
     * @created 2014年5月21日 下午4:28:55
     */
    public static <T> T getCookieValue(String cookieName, HttpServletRequest request, Class<T> clazz) {
        return getCookieValue(cookieName, request.getCookies(), clazz);
    }

    /**
     * 依据cookie的名称得到cookie value
     *
     * @param cookieName
     * @return
     * @author liushengqi
     * @created 2014年4月23日 下午5:23:54
     */
    public static String getCookieValue(String cookieName, Cookie[] cookies) {

        Cookie cookie = getCookie(cookieName, cookies);
        if (cookie == null || cookies == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 从请求内得到对应名称的cookie value
     *
     * @param cookieName
     * @param request
     * @return
     * @author liushengqi
     * @created 2014年4月23日 下午5:27:10
     */
    public static String getCookieValue(String cookieName, HttpServletRequest request) {
        return getCookieValue(cookieName, request.getCookies());
    }

    /**
     * 依据cookie的名称从请求内得到cookie
     *
     * @param cookieName
     * @param request
     * @return
     * @author liushengqi
     * @created 2014年4月23日 下午6:26:14
     */
    public static Cookie getCookie(String cookieName, HttpServletRequest request) {
        return getCookie(cookieName, request.getCookies());
    }

    /**
     * 依据cookie的名称得到cookie
     *
     * @param cookieName
     * @param cookies
     * @return
     * @author liushengqi
     * @created 2014年4月23日 下午6:27:25
     */
    public static Cookie getCookie(String cookieName, Cookie[] cookies) {

        if (cookieName == null || cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 删除cookie
     *
     * @param path
     * @param domain
     * @param resp
     * @author liushengqi
     * @created 2014年5月28日 下午3:11:41
     */
    public static void delCookie(Cookie cookie, String path, String domain, HttpServletResponse resp) {
        if (path != null && !"".equals(path)) {
            cookie.setPath(path);
        }
        if (domain != null && !"".equals(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param path
     * @param domain
     * @param resp
     * @author liushengqi
     * @created 2014年5月28日 下午3:11:41
     */
    public static void addCookie(String cookieName, String value, String path, String domain, Integer maxAge,
                                 HttpServletResponse resp) {
        Cookie cookie = new Cookie(cookieName, value);
        if (!Check.NuNStr(path)) {
            cookie.setPath(path);
        }
        if (!Check.NuNStr(domain)) {
            cookie.setDomain(domain);
        }
        if (!Check.NuNObj(maxAge)) {
            cookie.setMaxAge(maxAge);
        }
        resp.addCookie(cookie);
    }

}
