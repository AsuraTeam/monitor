/**
 * @FileName: ShardTableRule.java
 * @Package com.asura.framework.dao.rule
 * 
 * @author zhangshaobin
 * @created 2013-12-24 下午5:57:09
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.rule;

import java.util.Map;

/**
 * 分表策略对象
 * @since 1.0
 * @version 1.0
 */
public class ShardTableRule {
	
	protected Map<String,Rule> routerMap ;

	public ShardTableRule(Map<String, Rule> routerMap){
		this.routerMap = routerMap ; 
	}
	public Map<String, Rule> getRouterMap() {
		return routerMap;
	}

	public void setRouterMap(Map<String, Rule> routerMap) {
		this.routerMap = routerMap;
	} 
	
	

}
