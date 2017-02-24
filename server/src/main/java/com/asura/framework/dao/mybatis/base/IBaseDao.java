/**
 * @FileName: IBaseDao.java
 * @Package com.asura.framework.dao.mybatis.base
 * 
 * @author zhangshaobin
 * @created 2013-12-6 上午10:54:36
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.dao.mybatis.base;

import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

/**
 * <p>dao基类接口</p>
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
	 * @created 2012-12-19 下午5:51:23
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果集合
	 */
	public <T extends BaseEntity> List<T> findAll(String sqlId, Object params);

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
	public <T> List<T> findAll(String sqlId, Class<T> clazz, Object params);

	/**
	 * 
	 * 分页查询
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:39:36
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回对象
	 * @param params	查询条件
	 * @param pageBounds	分页对象
	 * @return	分页查询结果PagingResult
	 */
	public <T extends BaseEntity> PagingResult<T> findForPage(String sqlId, Class<T> clazz, Object params, PageBounds pageBounds);


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
	 * 从库查询, 根据条件查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-14 下午6:29:09
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOneSlave(String sqlId, Class<T> clazz, Map<String, Object> params);
	
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
	public <T> T findOne(String sqlId, Class<T> clazz, Object params);
	
	/**
	 * 
	 * 从库查询, 根据条件查询指定SQL语句的一条记录
	 *
	 * @author zhangshaobin
	 * @created 2012-12-14 下午6:29:09
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param params	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOneSlave(String sqlId, Class<T> clazz, Object params);

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
	 * 从库查询, 根据条件查询指定SQL语句的一条记录，主要用于关联查询的情况
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:02:04
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOneSlave(String sqlId, Class<T> clazz, int param);
	
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
	public <T> T findOne(String sqlId, Class<T> clazz);
	
	/**
	 * 
	 * 从库查询, 根据条件查询指定SQL语句的一条记录，主要用于关联查询的情况
	 *
	 * @author zhangshaobin
	 * @created 2012-12-24 下午5:02:04
	 *
	 * @param sqlId	SQL语句ID
	 * @param clazz	返回值类型
	 * @param param	条件参数
	 * @return	查询到的结果
	 */
	public <T> T findOneSlave(String sqlId, Class<T> clazz);


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
	 * 插入指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:59:43
	 *
	 * @param sqlId	SQL语句ID
	 * @return	插入对象的主键
	 */
	public int save(String sqlId);

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
	public int save(String sqlId, BaseEntity entity);

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
	 * 删除指定SQL的数据
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:33:17
	 *
	 * @param sqlId	SQL语句ID
	 * @param params	查询参数
	 * @return	成功删除记录数
	 */
	public int delete(String sqlId, BaseEntity entity);

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
	public int batchDelete(final String sqlId, final List<? extends BaseEntity> params);

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
	public int batchUpdate(final String sqlId, final List<? extends BaseEntity> params);
	
	
	public <T> List<T> findAllByMaster(String sqlId, Class<T> clazz, Object params);
	
	public long countBySlave(String sqlId) ;
	
	public long countBySlave(String sqlId, Map<String, Object> params);
	
}
