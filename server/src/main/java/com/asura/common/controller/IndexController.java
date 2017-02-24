package com.asura.common.controller;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.novell.ldap.LDAPException;
import com.asura.common.entity.AuthorityLogEntity;
import com.asura.common.entity.AuthorityUserEntity;
import com.asura.common.entity.LoginEntity;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.common.service.AuthorityLogService;
import com.asura.common.service.AuthorityUserService;
import com.asura.util.DateUtil;
import com.asura.util.HttpClientIpAddress;
import com.asura.util.LdapAuthenticate;
import com.asura.util.Md5Util;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2016/7/22.
 */
@Controller
public class IndexController {

    @Autowired
    private LdapAuthenticate ldapAuthenticate;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private AuthorityLogService logService;

    @Autowired
    private AuthorityUserService userService;


    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "/monitor/global/newIndex";
    }

    /**
     * 用户管理页面
     * @return
     */
    @RequestMapping("/user/list")
    public String userList(){
        return "/index/userlist";
    }

    /**
     * 修改密码页面
     * @return
     */
    @RequestMapping("/reset/password")
    public String password(HttpServletRequest request, Model model){
        String user = user(request);
        model.addAttribute("user", user);
        return "index/password";
    }

    /**
     * 删除用户
     * @param request
     * @param username
     * @return
     */
    @RequestMapping("/reset/userSaveDelete")
    @ResponseBody
    public String userSaveDelete(HttpServletRequest request, String username){
        String user = user(request);
        if (user.equals("admin") && ! username.equals("admin")){
            AuthorityUserEntity entity = new AuthorityUserEntity();
            entity.setUsername(username);
            userService.delete(entity);
        }else{
            return "必须由admin账号操作";
        }
        return "ok";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/reset/save/password",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public ResponseVo resetPassword(String newPassword, String oldPassword, String username, HttpServletRequest request){
        String user = user(request);
        if(user.length()<1){
            return ResponseVo.responseError("请登陆后操作");
        }
        // 修改自己的密码
        if(username.equals(user) && ! oldPassword.equals("")) {
            SearchMap searchMap = new SearchMap();
            searchMap.put("username", user);
            searchMap.put("password", Md5Util.MD5(oldPassword).toLowerCase());
            PagingResult<AuthorityUserEntity> result = userService.findAll(searchMap, PageResponse.getPageBounds(0, 1), "selectByAll");
            if (result.getTotal() > 0) {
                AuthorityUserEntity entity = result.getRows().get(0);
                entity.setPassword(Md5Util.MD5(newPassword).toLowerCase());
                userService.update(entity);
                logSave(request, "修改密码 " + user);
                return ResponseVo.responseOk("修改密码完成");
            }
        }
        // 管理员添加用户
        if (user.equals("admin") && oldPassword.equals("") ){
            AuthorityUserEntity entity = new AuthorityUserEntity();
            entity.setPassword(Md5Util.MD5(newPassword));
            entity.setUsername(username);
            try {
                userService.save(entity);
            }catch (Exception e){
                userService.update(entity);
            }
            return ResponseVo.responseOk("修改密码或添加用户完成");
        }
        return ResponseVo.responseError("操作失败");
    }

    /**
     * 登陆提示
     * @return
     */
    @RequestMapping(value = "/islogin", produces = {"application/json;charset=utf-8"})
    public String islogin(){
        return "/index/islogin";
    }

    /**
     * 没有权限
     * @return
     */
    @RequestMapping(value = "/noPermissions", produces = {"application/json;charset=utf-8"})
    public String noPermissions(){
        return "/index/noPermissions";
    }

    /**
     * 获取登陆的用户
     * @param request
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public String user(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        Gson gson = new Gson();
        RedisUtil redisUtil = new RedisUtil();
        String result = redisUtil.get(sessionId);
        if (result!=null&&result.length()>0){
            LoginEntity entity = gson.fromJson(result, LoginEntity.class);
            return entity.getUsername();
        }
        return "";
    }

    /**
     * 登陆页面
     * @return
     */
    @RequestMapping("/loginPage")
    public String loginPage(){
        return "index/login";
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginOut.do")
    public String loginOut(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.del(sessionId);
        return "index/login";
    }

    /*
       * 登陆验证数据
       */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String login(String username, String  password, HttpServletRequest request) throws LDAPException {
        boolean isLogin = false;
        boolean loginFlag = false;
        RedisUtil redisUtil = new RedisUtil();
        String sessionId = request.getSession().getId();
        // 数据库本地验证
        LoginEntity loginEntity = new LoginEntity();
        SearchMap searchMap = new SearchMap();
        searchMap.put("username", username);
        searchMap.put("password", Md5Util.MD5(password).toLowerCase());
        AuthorityUserEntity entity = new AuthorityUserEntity();
        entity.setLastLogin(DateUtil.getDate(DateUtil.TIME_FORMAT));
        PagingResult<AuthorityUserEntity> result = userService.findAll(searchMap, PageResponse.getPageBounds(0,1), "selectByAll");
        if (result.getTotal() >0  && result.getRows().get(0)!=null){
            entity.setLoginFaildCount(0);
            userService.update(entity);
            loginFlag = true;
            isLogin = true;
        }else{
            SearchMap searchMap2 = new SearchMap();
            searchMap2.put("username", username);
            PagingResult<AuthorityUserEntity> result2 = userService.findAll(searchMap2, PageResponse.getPageBounds(1,1), "selectByAll");
            if (result2.getTotal() > 0 && result2.getRows().get(0) !=  null ){
                entity.setUsername(username);
                entity.setLoginFaildCount(result2.getRows().get(0).getLoginFaildCount() + 1);
                userService.update(entity);
            }
        }

        // ldap验证
        if (!isLogin) {
            //登录
            loginFlag = ldapAuthenticate.connetLDAP(username, password);
        }
        if (loginFlag) {
            loginEntity.setUsername(username);
            loginEntity.setLoginTime(DateUtil.getDate(DateUtil.TIME_FORMAT));
            redisUtil.setex(sessionId, 86400, JSON.toJSONString(loginEntity));
            return "true";
        }
        return "用户名或者密码错误";
    }

    /**
     *
     * @param request
     * @param info
     */
    @RequestMapping("/log")
    @ResponseBody
    public String logSave(HttpServletRequest request, String info){
        try {
            HttpSession session = request.getSession();
            String user = permissionsCheck.getLoginUser(session);
            String clientIp = HttpClientIpAddress.getIpAddr(request);
            AuthorityLogEntity entity = new AuthorityLogEntity();
            entity.setUser(user);
            entity.setIp(clientIp);
            entity.setInfo(info);
            entity.setTime(String.valueOf(DateUtil.getTimeStamp()));
            logService.save(entity);
        }catch (Exception e){
             e.printStackTrace();
        }
        return "ok";
    }


    /**
     * 列表数据
     * @return
     */
    @RequestMapping(value = "/user/userListData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length, HttpServletRequest request) {
        String key = request.getParameter("search[value]");
        SearchMap searchMap = new SearchMap();
        if (key!=null&&key.length()>0){
            searchMap.put("key", key);
        }
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        PagingResult<AuthorityUserEntity> result = userService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

}
