/**
 * 
 */
package com.asura.framework.dao.strategy;


/**
 * 分表策略接口基类；
 * 
 * @author 
 * 
 */
public abstract class ShardStrategy implements IStrategy {

	protected String strategyName ; 
	protected String[] shardTableArray ; 
	
	public ShardStrategy( ){
		this.strategyName = "NULL_NAME_STRATEGY" ; 
	}
	public ShardStrategy(String strategyName ){
		this.strategyName = strategyName ; 
	}
	
	public ShardStrategy(String strategyName ,String[] shardTableArray ){
		this.strategyName = strategyName ; 
		this.shardTableArray = shardTableArray ; 
	}
	
}
