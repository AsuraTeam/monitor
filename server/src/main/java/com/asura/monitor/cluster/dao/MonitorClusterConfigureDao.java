package com.asura.monitor.cluster.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.cluster.entity.MonitorClusterConfigureEntity;
import com.asura.monitor.cluster.dao.MonitorClusterConfigureDao;
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
 * @date 2017-03-09 18:27:05
 * @since 1.0
 */
@Repository("com.asura.monitor.cluster.dao.MonitorClusterConfigureDao")
public class MonitorClusterConfigureDao extends BaseDao<MonitorClusterConfigureEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorClusterConfigureEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorClusterConfigureEntity.class,searchMap,pageBounds);
     }
}