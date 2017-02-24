package com.asura.util;

import com.google.gson.Gson;
import com.asura.common.entity.LoginEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author zy
 * @Date 2016-06-26 检查用户权限工具
 */
@Component
public class PermissionsCheck {


    RedisUtil redisUtil = new RedisUtil();

    /**
     * @param session
     * @return
     */
    public String getRedisSession(HttpSession session) {
        String sessionId = session.getId();
        String result = redisUtil.get(sessionId);
        return result;
    }

    /**
     * @param session
     * @return 获取登陆的用户名
     */
    public String getLoginUser(HttpSession session) {
        String result = getRedisSession(session);
        LoginEntity loginDao = new Gson().fromJson(result, LoginEntity.class);
        try{
            return loginDao.getUsername();
        }catch (Exception e){
            return "";
        }

    }


}
