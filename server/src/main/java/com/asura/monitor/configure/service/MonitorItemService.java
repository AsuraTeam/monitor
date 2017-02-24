package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorConfigureEntity;
import com.asura.monitor.configure.entity.MonitorItemEntity;
import com.asura.monitor.configure.dao.MonitorItemDao;
import org.springframework.stereotype.Service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

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
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016-08-20 16:45:10
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorItemService")
public class MonitorItemService extends BaseService<MonitorItemEntity,MonitorItemDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorItemDao")
    private MonitorItemDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorItemEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorItemEntity> getDataList(SearchMap searchMap, String sqlId){
        return dao.getDataList(searchMap, sqlId);
    }
}