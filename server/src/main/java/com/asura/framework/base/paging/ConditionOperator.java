/**
 * @FileName: ConditionOperator.java
 * @Package com.asura.framework.paging
 * 
 * @author zhangshaobin
 * @created 2012-12-13 上午9:15:31
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.paging;

import java.io.Serializable;

/**
 * <p>查询条件比较符号</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public final class ConditionOperator implements Serializable {
	
	private static final long serialVersionUID = 1972093917037650503L;

	/**
	 * 等于
	 */
	public static final int EQ = 1;

	/**
	 * 大于
	 */
	public static final int GT = 2;

	/**
	 * 大于等于
	 */
	public static final int GTE = 3;

	/**
	 * 小于
	 */
	public static final int LT = 4;

	/**
	 * 小于等于
	 */
	public static final int LTE = 5;

	/**
	 * 不等于
	 */
	public static final int NE = 6;

	/**
	 * 字符串Like
	 */
	public static final int LIKE = 7;
	
	/**
	 * 字符串包含，In
	 */
	public static final int IN = 8;

}
