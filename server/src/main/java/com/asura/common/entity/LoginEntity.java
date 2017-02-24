package com.asura.common.entity;



import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zy
 * @Date 2016-06-21
 * 登陆放的redis session里面的数据
 */

public class LoginEntity {


	private String username;
	private String loginTime;
	private String loginIp;
	private String realName;

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	private Map  userMap = new HashMap();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Map getUserMap() {
		return userMap;
	}
	public void setUserMap(Map userMap) {
		this.userMap = userMap;
	}

}


