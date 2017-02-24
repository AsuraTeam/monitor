package com.asura.framework.dao.mybatis.paginator.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;


/**
 * 
 * <p>TODO</p>
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
public class SQLServerDialect extends Dialect{

    public SQLServerDialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
        super(mappedStatement, parameterObject, pageBounds);
    }

	
	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf( "select" );
		final int selectDistinctIndex = sql.toLowerCase().indexOf( "select distinct" );
		return selectIndex + ( selectDistinctIndex == selectIndex ? 15 : 6 );
	}


    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
		if ( offset > 0 ) {
			throw new UnsupportedOperationException( "sql server has no offset" );
		}
//		if(limitPlaceholder != null) {
//			throw new UnsupportedOperationException(" sql server not support variable limit");
//		}
        setPageParameter(limitName, limit, Integer.class);
		return new StringBuffer( sql.length() + 8 )
				.append( sql )
				.insert( getAfterSelectInsertPoint( sql ), " top " + limit )
				.toString();
	}
	
	// TODO add Dialect.supportsVariableLimit() for sqlserver 
//	public boolean supportsVariableLimit() {
//		return false;
//	}

}
