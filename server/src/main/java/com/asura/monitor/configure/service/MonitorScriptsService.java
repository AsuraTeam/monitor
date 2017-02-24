package com.asura.monitor.configure.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.dao.MonitorScriptsDao;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
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
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016-08-20 10:37:54
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorScriptsService")
public class MonitorScriptsService extends BaseService<MonitorScriptsEntity,MonitorScriptsDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorScriptsDao")
    private MonitorScriptsDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorScriptsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorScriptsEntity> getDataList(SearchMap searchMap, String sqlId){
        return dao.getDataList(searchMap, sqlId);
    }
}