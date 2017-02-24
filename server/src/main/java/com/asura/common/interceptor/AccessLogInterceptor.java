/**
 * @FileName: LoggerInterception.java
 * @Package: com.asura.sms.archetype.commons.interceptor
 * @author sence
 * @created 4/13/2016 4:45 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.common.entity.UserAccount;
import com.asura.util.CookieUtil;
import com.asura.util.RequestClientIpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccessLogInterceptor.class);
    public static final String REQUEST_PRE_HANDLE_MILLSECOND = "__request_pre_handle_millseconds";
    public static final String REQUEST_USER_SESSION = "__user_session";
    public static final String REQUEST_USER_UID="__request_user_id";
    public static final String REQUEST_USER_TOKEN="__request_user_token";

    @Value("#{'${monitor.cookie.token}'.trim()}")
    private String accessTokenName;

    @Value("#{'${monitor.cookie.domain}'.trim()}")
    private String domain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //time
        long ct = new Date().getTime();

        request.setAttribute(REQUEST_PRE_HANDLE_MILLSECOND, ct);
        //UID
        UserAccount userAccount = (UserAccount) request.getSession().getAttribute(REQUEST_USER_SESSION);
        String uid = "";
        //用户没登录
        if (!Check.NuNObj(userAccount)) {
            uid = userAccount.getUserId();
        }
        request.setAttribute(REQUEST_USER_UID, uid);
        //TOKEN
        String userToken = CookieUtil.getCookieValue(accessTokenName, request);
        //首次访问，没有TOKEN 则分配Token并加入Cookie
        if (Check.NuNStr(userToken)) {
            userToken = UUIDGenerator.hexUUID();
            CookieUtil.addCookie(accessTokenName, userToken, "/", domain, null, response);
        }
        request.setAttribute(REQUEST_USER_TOKEN, userToken);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //UID
        String uid = (String) request.getAttribute(REQUEST_USER_UID);
        //IP
        String ipAddr = RequestClientIpUtil.getIpAddr(request);
        //URL
        String requestUrl = request.getRequestURI().toString();
        //TOKEN
        String userToken = (String) request.getAttribute(REQUEST_USER_TOKEN);
        //REQUEST USE TIME
        long startTime = (long) request.getAttribute(REQUEST_PRE_HANDLE_MILLSECOND);
        long useTime = System.currentTimeMillis() - startTime;
        if (LOGGER.isInfoEnabled()) {
            if (useTime > 500) {
                LOGGER.info("{url:{},ip:{},use:{} ms }", requestUrl, ipAddr, useTime);
            }
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
