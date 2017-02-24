package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorGroupsEntity;
import com.asura.monitor.configure.dao.MonitorGroupsDao;
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
 * @date 2016-08-20 10:18:32
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorGroupsService")
public class MonitorGroupsService extends BaseService<MonitorGroupsEntity,MonitorGroupsDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorGroupsDao")
    private MonitorGroupsDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorGroupsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}