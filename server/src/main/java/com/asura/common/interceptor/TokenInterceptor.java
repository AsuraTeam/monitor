/**
 * @FileName: TokenInterceptor.java
 * @Package: com.asura.sms.archetype.commons.interceptor
 * @author sence
 * @created 4/14/2016 4:15 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.interceptor;

import com.asura.common.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <p>为关键节点防止csrf攻击进行token处理</p>
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
public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);
    private static final String USER_CSRF_SESSION_TOKEN_KEY = "__csrf_session_token_key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.saveToken();
                if (needSaveSession) {
                    handSaveSession(request, USER_CSRF_SESSION_TOKEN_KEY);
                }
                boolean needRemoveToken = annotation.removeToken();
                if (needRemoveToken) {
                    if (isRepeatSubmit(request, USER_CSRF_SESSION_TOKEN_KEY)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute(USER_CSRF_SESSION_TOKEN_KEY);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private void handSaveSession(HttpServletRequest request, String sessionKey) {
        String token = UUID.randomUUID().toString();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("token interceptor session token:{}", token);
        }
        request.getSession(false).setAttribute(sessionKey, token);
    }

    private boolean isRepeatSubmit(HttpServletRequest request, String sessionKey) {
        String serverToken = (String) request.getSession(false).getAttribute(sessionKey);
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter(USER_CSRF_SESSION_TOKEN_KEY);
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
