package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.entity.CmdbResourceServiceEntity;
import com.asura.resource.dao.CmdbResourceServiceDao;
import org.springframework.stereotype.Service;

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
 * @date 2016-07-28 11:40:35
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceServiceService")
public class CmdbResourceServiceService extends BaseService<CmdbResourceServiceEntity,CmdbResourceServiceDao>{

    @Resource(name="com.asura.resource.dao.CmdbResourceServiceDao")
    private CmdbResourceServiceDao dao;

    /**
     * 查询所有
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceServiceEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return dao.findAll(searchMap,pageBounds);
    }
}