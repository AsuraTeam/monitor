package com.asura.util;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.authority.entity.AuthorityPagesEntity;
import com.asura.authority.entity.AuthorityPermissionEntity;
import com.asura.authority.service.AuthorityPagesService;
import com.asura.authority.service.AuthorityPermissionService;
import com.asura.common.entity.LoginEntity;
import com.asura.common.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author zy
 * @Date 2016-06-26 检查用户权限工具
 */
@Component
public class PermissionsCheck {

    @Autowired
    private AuthorityPagesService pagesService;

    @Autowired
    private AuthorityPermissionService permissionService;

    private RedisUtil redisUtil = new RedisUtil();

    /**
     * @param session
     * @return
     */
    public String getRedisSession(HttpSession session) {
        String sessionId = session.getId();
        if (redisUtil==null){
             redisUtil = new RedisUtil();
        }
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

    /**
     * 检查用户对某个接口的操作权限
     * @param username
     * @param url
     * @Date 20170218
     * @return
     */
   public boolean checkPagePermission(String username, String url){
       if (username.equals("admin")){
           return true;
       }
       SearchMap searchMap = new SearchMap();
       PageBounds pageBounds = PageResponse.getPageBounds(1000000,1);
       searchMap.put("pagesUrl" , url);
       PagingResult<AuthorityPagesEntity> urlResult = pagesService.findAll(searchMap,  pageBounds, "selectByAll");
       if (urlResult.getTotal()>0) {
           searchMap.put("username", username);
           PagingResult<AuthorityPermissionEntity> permissionResult =  permissionService.findAll(searchMap, pageBounds, "selectByUserPermission");
           if (permissionResult.getTotal()>0){
               return true;
           }else{
               return false;
           }
       }else{
           return true;
       }
   }
}