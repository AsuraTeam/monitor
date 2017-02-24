package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.CmdbQuartzEntity;
import com.asura.monitor.graph.dao.CmdbQuartzDao;
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
 * @date 2016-08-10 11:50:49
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.CmdbQuartzService")
public class CmdbQuartzService extends BaseService<CmdbQuartzEntity,CmdbQuartzDao>{

    @Resource(name="com.asura.monitor.graph.dao.CmdbQuartzDao")
    private CmdbQuartzDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbQuartzEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}