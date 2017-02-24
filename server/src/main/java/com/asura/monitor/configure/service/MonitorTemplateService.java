package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorTemplateEntity;
import com.asura.monitor.configure.dao.MonitorTemplateDao;
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
 * @date 2016-09-05 16:41:59
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorTemplateService")
public class MonitorTemplateService extends BaseService<MonitorTemplateEntity,MonitorTemplateDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorTemplateDao")
    private MonitorTemplateDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorTemplateEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}