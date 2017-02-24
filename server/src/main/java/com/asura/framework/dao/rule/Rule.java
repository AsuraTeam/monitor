/**
 * @FileName: Router.java
 * @Package com.asura.framework.dao.rule
 * 
 * @author zhangshaobin
 * @created 2013-12-23 下午3:04:07
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.rule;

import com.asura.framework.dao.strategy.IStrategy;

/**
 * 
 * 分表对象
 * @since 1.0
 * @version 1.0
 */
public class Rule {

	private String ruleSqlId ; 
	private String xmlTableParam ;	//orm文件中配置的表变量名称
	private String fieldParam ; 		//orm文件中配置的分表字段变量名称
	private IStrategy shardStrategy ; 
	
	/**
	 * @return the shardStrategy
	 */
	public IStrategy getShardStrategy() {
		return shardStrategy;
	}

	/**
	 * @return the ruleSqlId
	 */
	public String getRuleSqlId() {
		return ruleSqlId;
	}

	/**
	 * @param ruleSqlId the ruleSqlId to set
	 */
	public void setRuleSqlId(String ruleSqlId) {
		this.ruleSqlId = ruleSqlId;
	}

	/**
	 * @param shardStrategy the shardStrategy to set
	 */
	public void setShardStrategy(IStrategy shardStrategy) {
		this.shardStrategy = shardStrategy;
	}



	/**
	 * @return the fieldParam
	 */
	public String getFieldParam() {
		return fieldParam;
	}

	/**
	 * @param fieldParam the fieldParam to set
	 */
	public void setFieldParam(String fieldParam) {
		this.fieldParam = fieldParam;
	}

	/**
	 * @return the xmlTableParam
	 */
	public String getXmlTableParam() {
		return xmlTableParam;
	}

	/**
	 * @param xmlTableParam the xmlTableParam to set
	 */
	public void setXmlTableParam(String xmlTableParam) {
		this.xmlTableParam = xmlTableParam;
	} 
	
	
}
