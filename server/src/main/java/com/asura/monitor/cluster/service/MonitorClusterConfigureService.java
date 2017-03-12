package com.asura.monitor.cluster.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.cluster.entity.MonitorClusterConfigureEntity;
import com.asura.monitor.cluster.dao.MonitorClusterConfigureDao;
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
 * @date 2017-03-09 18:27:05
 * @since 1.0
 */
@Service("com.asura.monitor.cluster.service.MonitorClusterConfigureService")
public class MonitorClusterConfigureService extends BaseService<MonitorClusterConfigureEntity,MonitorClusterConfigureDao>{

    @Resource(name="com.asura.monitor.cluster.dao.MonitorClusterConfigureDao")
    private MonitorClusterConfigureDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorClusterConfigureEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}