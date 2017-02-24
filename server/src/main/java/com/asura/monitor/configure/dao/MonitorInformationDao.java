package com.asura.monitor.configure.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorInformationEntity;
import com.asura.monitor.configure.dao.MonitorInformationDao;
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
 * @date 2016-08-20 17:17:43
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorInformationDao")
public class MonitorInformationDao extends BaseDao<MonitorInformationEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorInformationEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorInformationEntity.class,searchMap,pageBounds);
     }
}