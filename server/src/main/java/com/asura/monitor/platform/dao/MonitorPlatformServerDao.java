package com.asura.monitor.platform.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.platform.entity.MonitorPlatformServerEntity;
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
 * @date 2016-11-07 11:35:05
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorPlatformServerDao")
public class MonitorPlatformServerDao extends BaseDao<MonitorPlatformServerEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorPlatformServerEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorPlatformServerEntity.class,searchMap,pageBounds);
     }
}