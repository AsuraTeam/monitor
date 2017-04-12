package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.MonitorGraphMergerEntity;
import com.asura.monitor.graph.dao.MonitorGraphMergerDao;
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
 * @date 2017-04-10 14:23:52
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.MonitorGraphMergerService")
public class MonitorGraphMergerService extends BaseService<MonitorGraphMergerEntity,MonitorGraphMergerDao>{

    @Resource(name="com.asura.monitor.graph.dao.MonitorGraphMergerDao")
    private MonitorGraphMergerDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorGraphMergerEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}