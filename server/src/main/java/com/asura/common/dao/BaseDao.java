/**
 * @FileName: BaseDao.java
 * @Package: com.asura.monitor.common.dao
 * @author liusq23
 * @created 7/5/2016 4:06 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.dao;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseDao<T extends BaseEntity> {

    @Resource(name = "monitor.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存实体类
     * this.getClass().getName() 为当前实现类的类名
     *
     * @param t
     */
    public void save(T t) {
        mybatisDaoContext.save(this.getClass().getName() + ".save", t);
    }

    /**
     * 更新实体
     *
     * this.getClass().getName() 为当前实现类的类名
     *
     * @param t
     */
    public void update(T t) {
        mybatisDaoContext.update(this.getClass().getName() + ".update", t);
    }

    /**
     * 查找实体类
     * this.getClass().getName() 为当前实现类的类名
     * @param id
     * @param
     * @return
     */
    public T findById(Integer id, Class<T> clazz) {
        return mybatisDaoContext.findOne(this.getClass().getName() + ".findById", clazz, id);
    }


    /**
     * 删除数据
     * @param t
     * @return
     */
    public  int delete(T t){
        return mybatisDaoContext.delete(this.getClass().getName() + ".delete",t);
    }

}
