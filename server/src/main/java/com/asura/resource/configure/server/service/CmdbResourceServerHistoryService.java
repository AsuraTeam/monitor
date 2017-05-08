package com.asura.resource.configure.server.service;
import com.asura.common.service.BaseService;
import com.asura.resource.configure.server.entity.CmdbResourceServerHistoryEntity;
import com.asura.resource.configure.server.dao.CmdbResourceServerHistoryDao;
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
 * @date 2017-05-08 12:34:43
 * @since 1.0
 */
@Service("com.asura.resource.configure.server.service.CmdbResourceServerHistoryService")
public class CmdbResourceServerHistoryService extends BaseService<CmdbResourceServerHistoryEntity,CmdbResourceServerHistoryDao>{

    @Resource(name="com.asura.resource.configure.server.dao.CmdbResourceServerHistoryDao")
    private CmdbResourceServerHistoryDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceServerHistoryEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}