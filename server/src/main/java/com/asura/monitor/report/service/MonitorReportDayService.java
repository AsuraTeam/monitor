package com.asura.monitor.report.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.monitor.report.dao.MonitorReportDayDao;
import com.asura.monitor.report.entity.MonitorReportDayEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


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
 * @date 2016-11-03 10:33:03
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorReportDayService")
public class MonitorReportDayService extends BaseService<MonitorReportDayEntity,MonitorReportDayDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorReportDayDao")
    private MonitorReportDayDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorReportDayEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorReportDayEntity>  getData(SearchMap searchMap, String sqlId){
        return dao.getData(searchMap, sqlId);
    }

    /**
     * @return
     */
    public long updateAutoRecover(){
        return dao.updateAutoRecover();
    }
}