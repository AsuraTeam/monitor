package com.asura.monitor.graph.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.graph.entity.MonitorImagesMergerEntity;
import com.asura.monitor.graph.dao.MonitorImagesMergerDao;
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
 * @date 2017-04-02 05:47:52
 * @since 1.0
 */
@Repository("com.asura.monitor.graph.dao.MonitorImagesMergerDao")
public class MonitorImagesMergerDao extends BaseDao<MonitorImagesMergerEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorImagesMergerEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorImagesMergerEntity.class,searchMap,pageBounds);
     }
}