/**
 * @FileName: IStrategy.java
 * @Package com.asura.framework.dao.strategy
 * 
 * @author zhangshaobin
 * @created 2013-12-24 下午2:58:24
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.strategy;

/**
 * 策略接口
 * @since 1.0
 * @version 1.0
 */
public interface IStrategy {
	public  String getTargetTable(Object paramValue);
	public void initStrategy(String[] shardTableArray) ; 
}
