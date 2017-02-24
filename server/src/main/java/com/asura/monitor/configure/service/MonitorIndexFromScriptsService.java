package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorIndexFromScriptsEntity;
import com.asura.monitor.configure.dao.MonitorIndexFromScriptsDao;
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
 * @date 2016-10-30 16:37:47
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorIndexFromScriptsService")
public class MonitorIndexFromScriptsService extends BaseService<MonitorIndexFromScriptsEntity,MonitorIndexFromScriptsDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorIndexFromScriptsDao")
    private MonitorIndexFromScriptsDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorIndexFromScriptsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}