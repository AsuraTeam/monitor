package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.MonitorGraphTemplateEntity;
import com.asura.monitor.graph.dao.MonitorGraphTemplateDao;
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
 * @date 2017-04-16 13:52:16
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.MonitorGraphTemplateService")
public class MonitorGraphTemplateService extends BaseService<MonitorGraphTemplateEntity,MonitorGraphTemplateDao>{

    @Resource(name="com.asura.monitor.graph.dao.MonitorGraphTemplateDao")
    private MonitorGraphTemplateDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorGraphTemplateEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}