/**
 * @FileName: Sort.java
 * @Package com.asura.framework.paging
 * 
 * @author zhangshaobin
 * @created 2013-3-18 下午8:26:51
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.paging;

import java.io.Serializable;

/**
 * <p>排序</p>
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
public class Sort implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Sort(int sortType, String field) {
		this.sortType = sortType;
		this.field = field;
	}
	
	/**
	 * 0：降序；1升序
	 */
	private int sortType;
	
	private String field;
	
	public static Sort createSort(int sortType, String field) {
		return new Sort(sortType, field);
	}

	/**
	 * @return the sortType
	 */
	public int getSortType() {
		return sortType;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	
}
