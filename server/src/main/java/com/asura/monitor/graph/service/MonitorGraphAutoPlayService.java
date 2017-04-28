package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.MonitorGraphAutoPlayEntity;
import com.asura.monitor.graph.dao.MonitorGraphAutoPlayDao;
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
 * @date 2017-04-18 11:26:39
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.MonitorGraphAutoPlayService")
public class MonitorGraphAutoPlayService extends BaseService<MonitorGraphAutoPlayEntity,MonitorGraphAutoPlayDao>{

    @Resource(name="com.asura.monitor.graph.dao.MonitorGraphAutoPlayDao")
    private MonitorGraphAutoPlayDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorGraphAutoPlayEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}