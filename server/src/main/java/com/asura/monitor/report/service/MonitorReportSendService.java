package com.asura.monitor.report.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.report.entity.MonitorReportSendEntity;
import com.asura.monitor.report.dao.MonitorReportSendDao;
import org.springframework.stereotype.Service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

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
@Service("com.asura.monitor.report.service.MonitorReportSendService")
public class MonitorReportSendService extends BaseService<MonitorReportSendEntity,MonitorReportSendDao>{

    @Resource(name="com.asura.monitor.report.dao.MonitorReportSendDao")
    private MonitorReportSendDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorReportSendEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}