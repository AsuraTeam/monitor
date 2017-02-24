package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorConfigureEntity;
import com.asura.monitor.configure.dao.MonitorConfigureDao;
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
 * @date 2016-09-15 22:44:11
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorConfigureService")
public class MonitorConfigureService extends BaseService<MonitorConfigureEntity,MonitorConfigureDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorConfigureDao")
    private MonitorConfigureDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorConfigureEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorConfigureEntity> getDataList(SearchMap searchMap, String sqlId){
        return dao.getDataList(searchMap, sqlId);
    }

}