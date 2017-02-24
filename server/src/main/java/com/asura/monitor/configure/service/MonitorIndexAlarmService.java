package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorIndexAlarmEntity;
import com.asura.monitor.configure.dao.MonitorIndexAlarmDao;
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
 * @date 2016-11-18 08:59:10
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorIndexAlarmService")
public class MonitorIndexAlarmService extends BaseService<MonitorIndexAlarmEntity,MonitorIndexAlarmDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorIndexAlarmDao")
    private MonitorIndexAlarmDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorIndexAlarmEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}