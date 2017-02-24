package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.dao.MonitorMessageChannelDao;
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
 * @date 2016-10-05 18:15:34
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorMessageChannelService")
public class MonitorMessageChannelService extends BaseService<MonitorMessageChannelEntity,MonitorMessageChannelDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorMessageChannelDao")
    private MonitorMessageChannelDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorMessageChannelEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}