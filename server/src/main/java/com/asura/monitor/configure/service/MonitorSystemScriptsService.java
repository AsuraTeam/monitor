package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorSystemScriptsEntity;
import com.asura.monitor.configure.dao.MonitorSystemScriptsDao;
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
 * @date 2016-11-05 20:19:24
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorSystemScriptsService")
public class MonitorSystemScriptsService extends BaseService<MonitorSystemScriptsEntity,MonitorSystemScriptsDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorSystemScriptsDao")
    private MonitorSystemScriptsDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorSystemScriptsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}