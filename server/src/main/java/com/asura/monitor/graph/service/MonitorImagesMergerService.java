package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.MonitorImagesMergerEntity;
import com.asura.monitor.graph.dao.MonitorImagesMergerDao;
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
 * @date 2017-04-02 05:47:52
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.MonitorImagesMergerService")
public class MonitorImagesMergerService extends BaseService<MonitorImagesMergerEntity,MonitorImagesMergerDao>{

    @Resource(name="com.asura.monitor.graph.dao.MonitorImagesMergerDao")
    private MonitorImagesMergerDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorImagesMergerEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}