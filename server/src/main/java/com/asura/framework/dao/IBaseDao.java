/**
 * @FileName: IBaseDaoInterface.java
 * @Package com.asura.framework.dao
 * 
 * @author zhangshaobin
 * @created 2013-12-6 上午10:54:36
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao;

import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>TODO</p>
 * 
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public interface IBaseDao {

	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:32:51
	 *
	 * @param sqlId	SQL语句ID
	 * @return	查询到的实体集合
	 */
	public <T extends BaseEntity> List<T> findAll(String sqlId);

	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:34:26
	 *
	 * @param sqlId	SQL语句ID
	 * @param SearchModel	查询模型
	 * @return	查询到的实体集合
	 */
	public <T extends BaseEntity> List<T> findAll(String sqlId, SearchModel searchModel);

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
	public <T> List<T> findAll(String sqlId, Class<T> clazz, Map<String, Object> params);

	/**
	 * 
	 * 查询指定SQL语句的所有记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:11:46
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果集合
	 */
	public <T> List<T> findAll(String sqlId, Class<T> clazz, int param);

	/**
	 * 
	 * 分页查询指定SQL语句的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:39:36
	 *
	 * @param countSqlId	总数查询SQL语句ID
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @param clazz	查询结果类型
	 * @return	分页查询结果PagingResult
	 */
	public <T extends BaseEntity> PagingResult<T> findForPage(String countSqlId, String sqlId, SearchModel searchModel,
			Class<T> clazz);

	/**
	 * 
	 * 分页查询指定SQL语句的数据（ID）
	 *
	 * @author zhangshaobin
	 * @created 2013-1-25 下午4:10:53
	 *
	 * @param countSqlId	总数查询SQL语句ID
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @return	分页查询结果List<Integer>
	 */
	public PagingResult<Integer> findForPage(String countSqlId, String sqlId, SearchModel searchModel);

	/**
	 * 
	 * 查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:45:55
	 *
	 * @param sqlId	SQL语句ID
	 * @return	查询到的实体
	 */
	public <T extends BaseEntity> T findOne(String sqlId);

	/**
	 * 
	 * 根据条件查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-14 下午6:29:09
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOne(String sqlId, Class<T> clazz, Map<String, Object> params);

	/**
	 * 
	 * 根据条件查询指定SQL语句的一条记录，主要用于关联查询的情况
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:02:04
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOne(String sqlId, Class<T> clazz, int param);

	/**
	 * 
	 * 查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:48:02
	 *
	 * @param sqlId	SQL语句ID
	 * @param param	SQL语句中占位符对应的值
	 * @return	查询到的实体
	 */
	public <T extends BaseEntity> T findOne(String sqlId, SearchModel searchModel);

	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:52:26
	 *
	 * @param sqlId	SQL语句ID
	 * @return	记录数
	 */
	public long count(String sqlId);

	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2013-6-7 上午10:16:05
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	参数
	 * @return	符合条件的记录数
	 */
	public long count(String sqlId, Map<String, Object> params);

	/**
	 * 
	 * 查询指定SQL语句所包含的记录数
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:55:36
	 *
	 * @param sqlId	SQL语句ID
	 * @param searchModel	查询模型
	 * @return	符合条件的记录数
	 */
	public long count(String sqlId, SearchModel searchModel);

	/**
	 * 
	 * 插入指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:59:43
	 *
	 * @param sqlId	SQL语句ID
	 * @return	插入对象的主键
	 */
	public Object save(String sqlId);

	/**
	 * 
	 * 插入指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:15:07
	 *
	 * @param sqlId	SQL语句ID
	 * @param entity	要插入的实体
	 * @return	插入对象的主键
	 */
	public Object save(String sqlId, BaseEntity entity);

	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:18:34
	 *
	 * @param sqlId	SQL语句ID
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId);

	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:25:49
	 *
	 * @param sqlId	SQL语句ID
	 * @param entity	要更新的对象
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId, BaseEntity entity);

	/**
	 * 
	 * 更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-12-17 下午6:38:55
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	参数
	 * @return	成功更新的记录数
	 */
	public int update(String sqlId, Map<String, Object> params);

	/**
	 * 
	 * 删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:30:00
	 *
	 * @param sqlId	SQL语句ID
	 * @return	成功删除的记录数
	 */
	public int delete(String sqlId);

	/**
	 * 
	 * 删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:33:17
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	查询参数
	 * @return	成功删除记录数
	 */
	public int delete(String sqlId, Map<String, Object> params);

	/**
	 * 
	 * 批量删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-12-3 下午2:19:55
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	SQL语句中占位符对应的值
	 * @return	成功更新的记录数
	 */
	public int[] batchDelete(final String sqlId, final List<BaseEntity> params);

	/**
	 * 
	 * 批量更新指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:36:32
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	SQL语句中占位符对应的值
	 * @return	成功更新的记录数
	 */
	public int[] batchUpdate(final String sqlId, final List<? extends BaseEntity> params);

	public <T extends BaseEntity> T findOneByM(String sqlId);

	public <T> T findOneByM(String sqlId, Class<T> clazz, Map<String, Object> params);

	public <T extends BaseEntity> T findOneByM(String sqlId, SearchModel searchModel);

	public <T> T findOneByM(String sqlId, Class<T> clazz, int param);

	public long countByM(String sqlId);

	public long countByM(String sqlId, Map<String, Object> params);

	public <T> List<T> findAllByM(String sqlId, Class<T> clazz, Map<String, Object> params);

	public <T extends BaseEntity> List<T> findAllByM(String sqlId);
}
