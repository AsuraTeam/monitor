/**
 * @FileName: BaseService.java
 * @Package: com.asura.monitor.common.service
 * @author liusq23
 * @created 7/5/2016 4:22 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.service;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.common.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
public abstract class BaseService<T extends BaseEntity, E extends BaseDao> {

    @Autowired
    private E e;

    /**
     * 保存实体
     *
     * @param t
     */
    @Transactional
    public void save(T t) {
        e.save(t);
    }

    /**
     * 更新实体
     *
     * @param t
     */
    @Transactional
    public void update(T t) {
        e.update(t);
    }

    /**
     * 查找实体
     *
     * @param id
     * @param clazz
     * @return
     */
    public T findById(Integer id, Class<T> clazz) {
        return (T) e.findById(id, clazz);
    }


    /**
     * 删除实体
     *
     * @param t
     */
    @Transactional
    public void delete(T t) {
        e.delete(t);
    }


}
