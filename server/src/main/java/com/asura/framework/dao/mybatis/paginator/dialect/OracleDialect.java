package com.asura.framework.dao.mybatis.paginator.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

/**
 * 
 * <p>oracle方言</p>
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
public class OracleDialect extends Dialect{

    public OracleDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
        super(mappedStatement, parameterObject, pageBounds);
    }

    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
            setPageParameter("__offsetEnd",offset+limit,Integer.class);
            setPageParameter(offsetName,offset,Integer.class);
		}
		else {
			pagingSelect.append(" ) where rownum <= ?");
            setPageParameter(limitName,limit,Integer.class);
		}

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		
		return pagingSelect.toString();
	}

}
