package com.asura.monitor.configure.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorSystemScriptsEntity;
import com.asura.monitor.configure.dao.MonitorSystemScriptsDao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;



/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016-11-05 20:19:24
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorSystemScriptsDao")
public class MonitorSystemScriptsDao extends BaseDao<MonitorSystemScriptsEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorSystemScriptsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorSystemScriptsEntity.class,searchMap,pageBounds);
     }
}