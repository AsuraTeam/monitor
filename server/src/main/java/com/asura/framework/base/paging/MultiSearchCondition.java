/**
 * @FileName: MultiSearchCondition.java
 * @Package com.asura.framework.paging
 * 
 * @author zhangshaobin
 * @created 2012-12-19 上午9:37:25
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.paging;

/**
 * <p>组合条件查询</p>
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
public class MultiSearchCondition extends SearchCondition {

	private static final long serialVersionUID = 6757678883125402584L;

	/**
	 * 第二个参数比较类型
	 */
	private int operateType2;
	
	/**
	 * 第二个参数值
	 */
	private Object value2;

	/**
	 * @return the operator2
	 */
	public int getOperateType2() {
		return operateType2;
	}

	/**
	 * @param operator2 the operator2 to set
	 */
	public void setOperateType2(int operateType2) {
		this.operateType2 = operateType2;
	}

	/**
	 * @return the value2
	 */
	public Object getValue2() {
		return value2;
	}

	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(Object value2) {
		this.value2 = value2;
	}
	
}
