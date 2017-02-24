package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorInformationEntity;
import com.asura.monitor.configure.dao.MonitorInformationDao;
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
 * @date 2016-08-20 17:17:43
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorInformationService")
public class MonitorInformationService extends BaseService<MonitorInformationEntity,MonitorInformationDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorInformationDao")
    private MonitorInformationDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorInformationEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}