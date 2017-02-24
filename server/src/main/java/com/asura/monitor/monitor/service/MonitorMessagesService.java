package com.asura.monitor.monitor.service;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.monitor.monitor.dao.CmdbMonitorMessagesDao;
import com.asura.monitor.monitor.entity.CmdbMonitorMessagesEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  监控数据管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/24 09:12
 * @since 1.0
 */
@Service("com.asura.monitor.monitor.service.MonitorMessagesService")
public class MonitorMessagesService extends BaseService<CmdbMonitorMessagesEntity,CmdbMonitorMessagesDao>{

    @Resource(name="com.asura.monitor.monitor.dao.CmdbMonitorMessagesDao")
    private CmdbMonitorMessagesDao monitorMessagesDao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbMonitorMessagesEntity> findAll(SearchMap searchMap, PageBounds pageBounds) {
        return monitorMessagesDao.findAll(searchMap, pageBounds);
    }


    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbMonitorMessagesEntity>  getMessageList(SearchMap searchMap,String sqlId){
        return monitorMessagesDao.getMessageList(searchMap, sqlId);
    }
}