package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.dao.MonitorMessagesDao;
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
 * @date 2016-09-21 08:18:08
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorMessagesService")
public class MonitorMessagesService extends BaseService<MonitorMessagesEntity,MonitorMessagesDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorMessagesDao")
    private MonitorMessagesDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorMessagesEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}