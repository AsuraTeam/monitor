package com.asura.monitor.configure.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorSeverityEntity;
import com.asura.monitor.configure.dao.MonitorSeverityDao;
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
 * @date 2016-08-20 09:37:40
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorSeverityDao")
public class MonitorSeverityDao extends BaseDao<MonitorSeverityEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorSeverityEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorSeverityEntity.class,searchMap,pageBounds);
     }
}