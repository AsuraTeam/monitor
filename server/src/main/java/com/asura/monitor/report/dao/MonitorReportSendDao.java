package com.asura.monitor.report.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.report.entity.MonitorReportSendEntity;
import com.asura.monitor.report.dao.MonitorReportSendDao;
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
 * @date 2017-01-26 15:39:45
 * @since 1.0
 */
@Repository("com.asura.monitor.report.dao.MonitorReportSendDao")
public class MonitorReportSendDao extends BaseDao<MonitorReportSendEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorReportSendEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorReportSendEntity.class,searchMap,pageBounds);
     }
}