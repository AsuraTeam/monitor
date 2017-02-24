/**
 * @FileName: PagingResult.java
 * @Package com.asura.framework.paging
 * 
 * @author zhangshaobin
 * @created 2012-12-12 下午7:24:43
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.paging;

import java.io.Serializable;
import java.util.List;

import com.asura.framework.base.entity.DataTransferObject;


/**
 * <p>分页查询结果</p>
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
public class PagingResult<T> implements Serializable {
	
	private static final long serialVersionUID = -6293650025117429528L;
	/**
	 * 默认构造方法
	 */
	public PagingResult(){
        
    }
	/**
	 * 分页查询结果构造器
	 * 
	 * @param total	总记录数
	 * @param rows	每页记录
	 */
	public PagingResult(long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	/**
	 * 总记录数
	 */
	private long total;
	
	/**
	 * 每页数据记录
	 */
	private List<T> rows;

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	/**
	 * 
	 * 将PagingResult转换为DataTransferObject
	 *
	 * @author zhangshaobin
	 * @created 2012-12-13 下午4:40:35
	 *
	 * @param key	放入DataTransferObject时需要的key值
	 * @return	新的DataTransferObject对象
	 */
	public DataTransferObject toDataTransferObject(String key) {
		DataTransferObject dto = new DataTransferObject();
		dto.putValue(key, rows);
		return dto;
	}
	
}
