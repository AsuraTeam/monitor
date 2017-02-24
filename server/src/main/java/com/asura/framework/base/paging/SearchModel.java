/**
 * @FileName: SearchModel.java
 * @Package com.asura.framework.paging
 * 
 * @author zhangshaobin
 * @created 2012-12-12 下午7:32:46
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.paging;

import java.io.Serializable;
import java.util.List;

/**
 * <p>查询模型</p>
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
public class SearchModel implements Serializable {
	
	private static final long serialVersionUID = 5580142689208420784L;

	/**
	 * 当前页码
	 */
	private int page;
	
	/**
	 * 每页记录数
	 */
	private int pageSize = 20;
	
	/**
	 * 查询条件组
	 */
	private List<SearchCondition> searchConditionList;
	
	/**
	 * 排序
	 */
	private List<Sort> sortList;

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
		
		if (page < 0) {
			this.page = 0;
		}
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * @return the searchConditionList
	 */
	public List<SearchCondition> getSearchConditionList() {
		return searchConditionList;
	}

	/**
	 * @param searchConditionList the searchConditionList to set
	 */
	public void setSearchConditionList(List<SearchCondition> searchConditionList) {
		this.searchConditionList = searchConditionList;
	}
	
	/**
	 * @return the sortList
	 */
	public List<Sort> getSortList() {
		return sortList;
	}

	/**
	 * @param sortList the sortList to set
	 */
	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}

	/**
	 * 
	 * 得到当前页第一行数据的行号
	 *
	 * @author zhangshaobin
	 * @created 2012-12-13 上午9:34:40
	 *
	 * @return
	 */
	public int getFirstRowNum() {
		return ((page - 1) * pageSize);
	}
	
}
