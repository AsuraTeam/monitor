package com.asura.resource.service;
import com.asura.common.service.BaseService;
import com.asura.resource.entity.CmdbResourceInventoryEntity;
import com.asura.resource.dao.CmdbResourceInventoryDao;
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
 * @date 2017-01-13 08:06:42
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceInventoryService")
public class CmdbResourceInventoryService extends BaseService<CmdbResourceInventoryEntity,CmdbResourceInventoryDao>{

    @Resource(name="com.asura.resource.dao.CmdbResourceInventoryDao")
    private CmdbResourceInventoryDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceInventoryEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}