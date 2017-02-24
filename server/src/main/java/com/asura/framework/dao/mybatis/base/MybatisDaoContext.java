/**
 * @FileName: MybatisDaoContext.java
 * @Package com.asura.framework.dao.mybatis.base
 * 
 * @author zhangshaobin
 * @created 2015年6月14日 下午2:47:23
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.mybatis.base;

import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.paginator.domain.PageList;

/**
 * <p>
 * mybatis基类
 * 从库操作：findAll   findForPage   count  findXXXSlave
 * 主库操作：其他方法全部主库主库操作
 * </p>
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
public class MybatisDaoContext extends BaseMybatisDaoSupport implements IBaseDao{

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findAll(java.lang.String)
	 */
	@Override
	public <T extends BaseEntity> List<T> findAll(String sqlId) {
		return getReadSqlSessionTemplate().selectList(sqlId);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findAll(java.lang.String, java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T extends BaseEntity> List<T> findAll(String sqlId, Object params) {
		return getReadSqlSessionTemplate().selectList(sqlId, params);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findForPage(java.lang.String, java.lang.Class, java.lang.Object, com.asura.framework.dao.mybatis.paginator.domain.PageBounds)
	 */
	@Override
	public <T extends BaseEntity> PagingResult<T> findForPage(String sqlId,
			Class<T> clazz, Object params, PageBounds pageBounds) {
		List<T> list = getReadSqlSessionTemplate().selectList(sqlId, params, pageBounds);
		// 后端如果不分页，直接返回的类型为ArrayList，需要作出判断
		if (list instanceof PageList) {
			PageList<T> pl = (PageList<T>) list;
			return new PagingResult<>(pl.getPaginator().getTotalCount(), pl);
		} else {
			return new PagingResult<>(0, list);
		}
	}


	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findOne(java.lang.String, java.lang.Class, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOne(String sqlId, Class<T> clazz,
			Map<String, Object> params) {
		return (T)getWriteSqlSessionTemplate().selectOne(sqlId, params);
	}
	
	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findOne(java.lang.String, java.lang.Class, Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOne(String sqlId, Class<T> clazz,
			Object params) {
		return (T)getWriteSqlSessionTemplate().selectOne(sqlId, params);
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T findOne(String sqlId, Class<T> clazz){
		return (T)getWriteSqlSessionTemplate().selectOne(sqlId);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#findOne(java.lang.String, java.lang.Class, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOne(String sqlId, Class<T> clazz, int param) {
		return (T)getWriteSqlSessionTemplate().selectOne(sqlId, param);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOneSlave(String sqlId, Class<T> clazz,
			Map<String, Object> params) {
		return (T)getWriteSqlSessionTemplate().selectOne(sqlId, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOneSlave(String sqlId, Class<T> clazz,
			Object params) {
		return (T)getReadSqlSessionTemplate().selectOne(sqlId, params);
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T findOneSlave(String sqlId, Class<T> clazz){
		return (T)getReadSqlSessionTemplate().selectOne(sqlId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOneSlave(String sqlId, Class<T> clazz, int param) {
		return (T)getReadSqlSessionTemplate().selectOne(sqlId, param);
	}
	

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#count(java.lang.String)
	 */
	@Override
	public long count(String sqlId) {
		final Object obj = getWriteSqlSessionTemplate().selectOne(sqlId);
		if (obj instanceof Long) {
			return (Long) obj;
		} else if (obj instanceof Integer) {
			return Long.parseLong(((Integer) obj).toString());
		} else {
			return (Long) obj;
		}
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#count(java.lang.String, java.util.Map)
	 */
	@Override
	public long count(String sqlId, Map<String, Object> params) {
		final Object obj = getWriteSqlSessionTemplate().selectOne(sqlId, params);
		if (obj instanceof Long) {
			return (Long) obj;
		} else if (obj instanceof Integer) {
			return Long.parseLong(((Integer) obj).toString());
		} else {
			return (Long) obj;
		}
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#save(java.lang.String)
	 */
	@Override
	public int save(String sqlId) {
		return getWriteSqlSessionTemplate().insert(sqlId);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#save(java.lang.String, com.asura.framework.base.entity.BaseEntity)
	 */
	@Override
	public int save(String sqlId, BaseEntity entity) {
		return getWriteSqlSessionTemplate().insert(sqlId,entity);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#update(java.lang.String)
	 */
	@Override
	public int update(String sqlId) {
		return getWriteSqlSessionTemplate().update(sqlId);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#update(java.lang.String, com.asura.framework.base.entity.BaseEntity)
	 */
	@Override
	public int update(String sqlId, BaseEntity entity) {
		return getWriteSqlSessionTemplate().update(sqlId,entity);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#update(java.lang.String, java.util.Map)
	 */
	@Override
	public int update(String sqlId, Map<String, Object> params) {
		return getWriteSqlSessionTemplate().update(sqlId,params);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#delete(java.lang.String)
	 */
	@Override
	public int delete(String sqlId) {
		return getWriteSqlSessionTemplate().delete(sqlId);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#delete(java.lang.String, java.util.Map)
	 */
	@Override
	public int delete(String sqlId, Map<String, Object> params) {
		return getWriteSqlSessionTemplate().delete(sqlId,params);
	}
	
	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#delete(java.lang.String, java.util.Map)
	 */
	@Override
	public int delete(String sqlId, BaseEntity entity) {
		return getWriteSqlSessionTemplate().delete(sqlId,entity);
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#batchDelete(java.lang.String, java.util.List)
	 */
	@Override
	public int batchDelete(String sqlId, List<? extends BaseEntity> params) {
		int count = 0;
		for (int i = 0; i < params.size(); i++) {
			count = count + delete(sqlId, params.get(i));
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#batchUpdate(java.lang.String, java.util.List)
	 */
	@Override
	public int batchUpdate(String sqlId, List<? extends BaseEntity> params) {
		int count = 0;
		for (int i = 0; i < params.size(); i++) {
			count = count + update(sqlId, params.get(i));
		}
		return count;
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-19 下午5:51:23
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果集合
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(String sqlId, Class<T> clazz, Object params){
		return (List<T>)getReadSqlSessionTemplate().selectList(sqlId, params);
	}
	
	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-19 下午5:51:23
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllByMaster(String sqlId, Class<T> clazz, Object params){
		return (List<T>)getWriteSqlSessionTemplate().selectList(sqlId, params);
	}
	
	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#count(java.lang.String)
	 */
	@Override
	public long countBySlave(String sqlId) {
		final Object obj = getReadSqlSessionTemplate().selectOne(sqlId);
		if (obj instanceof Long) {
			return (Long) obj;
		} else if (obj instanceof Integer) {
			return Long.parseLong(((Integer) obj).toString());
		} else {
			return (Long) obj;
		}
	}

	/* (non-Javadoc)
	 * @see com.asura.framework.dao.mybatis.base.IBaseDao#count(java.lang.String, java.util.Map)
	 */
	@Override
	public long countBySlave(String sqlId, Map<String, Object> params) {
		final Object obj = getReadSqlSessionTemplate().selectOne(sqlId, params);
		if (obj instanceof Long) {
			return (Long) obj;
		} else if (obj instanceof Integer) {
			return Long.parseLong(((Integer) obj).toString());
		} else {
			return (Long) obj;
		}
	}

}
