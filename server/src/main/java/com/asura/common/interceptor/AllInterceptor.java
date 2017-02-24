package com.asura.common.interceptor;

import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author zy
 * @Date 2016-06-21 登陆拦截器
 */
@Component
public class AllInterceptor extends HandlerInterceptorAdapter {


	@Value("#{'${monitor.nocheck.url}'.trim()}")
	private String urls;

	private static ArrayList urlList ;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse reponse, Object handler) throws Exception {
		if (urlList==null){
			urlList = new ArrayList();
			String[] urlSplit = urls.split(",");
			for (int i = 0; i < urlSplit.length; i++) {
				String matchString = urlSplit[i];
				urlList.add(matchString);
			}
		}
		String url = request.getRequestURI();
		if (urlList.contains(url)){
			return super.preHandle(request,reponse,handler);
		}

		if (!url.toLowerCase().contains("save")){
			return super.preHandle(request,reponse,handler);
		}


		// 从redis获取session数据
		String sessionId = request.getSession().getId();
		try {

			RedisUtil redisUtil = new RedisUtil();
			String value = redisUtil.get(sessionId);
			if (value!=null && value.contains("username")) {
				return super.preHandle(request,reponse,handler);
			}else {
				reponse.sendRedirect(request.getContextPath()+"/islogin");
				return false;
			}

//			// 检查权限
//			if(!PermissionsCheck.checkUserPermissions(PermissionsCheck.getLoginUser(sessionId),url)){
//			    reponse.sendRedirect(request.getContextPath()+"/noPermissions.do");
//				return false;
//			}

		} catch (Exception e) {
			reponse.sendRedirect(request.getContextPath()+"/loginPage.do");
			return false;
		}
	}

}
