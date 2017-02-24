/**
 * @FileName: BaseMybatisDaoSupport.java
 * @Package com.asura.framework.dao.mybatis.base
 * 
 * @author zhangshaobin
 * @created 2015年9月26日 上午11:46:29
 * 
 * Copyright 2011-2015 顺丰优选 版权所有
 */
package com.asura.framework.dao.mybatis.base;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>基类</p>
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
public class BaseMybatisDaoSupport {
	
	protected Logger logger = LoggerFactory.getLogger(BaseMybatisDaoSupport.class);
	
	private SqlSessionTemplate writeSqlSessionTemplate; //写库模板
	
	private SqlSessionTemplate readSqlSessionTemplate; //读库模板
	

	/**
	 * @return the writeSqlSessionTemplate
	 */
	public SqlSessionTemplate getWriteSqlSessionTemplate() {
		return writeSqlSessionTemplate;
	}

	/**
	 * @param writeSqlSessionTemplate the writeSqlSessionTemplate to set
	 */
	public void setWriteSqlSessionTemplate(
			SqlSessionTemplate writeSqlSessionTemplate) {
		this.writeSqlSessionTemplate = writeSqlSessionTemplate;
	}

	/**
	 * @return the readSqlSessionTemplate
	 */
	public SqlSessionTemplate getReadSqlSessionTemplate() {
		return readSqlSessionTemplate;
	}

	/**
	 * @param readSqlSessionTemplate the readSqlSessionTemplate to set
	 */
	public void setReadSqlSessionTemplate(SqlSessionTemplate readSqlSessionTemplate) {
		this.readSqlSessionTemplate = readSqlSessionTemplate;
	}
	
	
	

}
