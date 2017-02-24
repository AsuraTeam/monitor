package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorContactGroupEntity;
import com.asura.monitor.configure.dao.MonitorContactGroupDao;
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
 * @date 2016-08-20 09:07:21
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorContactGroupService")
public class MonitorContactGroupService extends BaseService<MonitorContactGroupEntity,MonitorContactGroupDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorContactGroupDao")
    private MonitorContactGroupDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorContactGroupEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}