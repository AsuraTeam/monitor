/**
 * @FileName: DivisionShardStrategy.java
 * @Package com.asura.framework.dao.strategy
 * 
 * @author zhangshaobin
 * @created 2013-12-19 上午11:49:10
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.strategy;

/**
 * <p>取余数分表策略</p>
 * 
 * 
 * @since 1.0
 * @version 1.0
 */
public class DivisionHashShardStrategy extends ShardStrategy  {

	private int divisor = 3 ; 
	public DivisionHashShardStrategy(){
		super();
	}
	public DivisionHashShardStrategy(String strategyName){
		super(strategyName) ;
	}
	public DivisionHashShardStrategy(String strategyName , String[] shardTableArray){
		super(strategyName , shardTableArray) ;
		divisor = shardTableArray.length ; 
	}
	
	public DivisionHashShardStrategy(String strategyName ,String[] shardTableArray , int divisor){
		super(strategyName , shardTableArray) ;
		this.divisor = divisor ; 
	}
	
	
	public void initStrategy(String[] shardTableArray){
		this.shardTableArray = shardTableArray ; 
		divisor = shardTableArray.length ;
	}
	
	@Override
	public String getTargetTable(Object paramValue) {
		int offset =0 ;
		if ( paramValue instanceof Long ){
			offset = Long.bitCount((Long)paramValue % divisor) ;  
		}else if(paramValue instanceof Integer){
			offset = (Integer)paramValue % divisor ;  
		}else{
			offset = paramValue.hashCode()% divisor ; 
		}
		return shardTableArray[offset];
	}
	
	public String toString(){
		return "strategyName:"+strategyName+",divisor:"+divisor+",shardTableArray:"+shardTableArray+strategyName ; 
	}
	public static void main(String[] args) {
		Long i = 124222L ; 
		Integer in = 222;
		String str = "222" ; 
		System.out.println(i.hashCode());
		System.out.println(in.hashCode());
		System.out.println(str.hashCode());
	}

}
