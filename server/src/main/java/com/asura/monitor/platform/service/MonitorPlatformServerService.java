package com.asura.monitor.platform.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.monitor.platform.dao.MonitorPlatformServerDao;
import com.asura.monitor.platform.entity.MonitorPlatformServerEntity;
import org.springframework.stereotype.Service;

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
@Service("com.asura.monitor.configure.service.MonitorPlatformServerService")
public class MonitorPlatformServerService extends BaseService<MonitorPlatformServerEntity,MonitorPlatformServerDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorPlatformServerDao")
    private MonitorPlatformServerDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorPlatformServerEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}