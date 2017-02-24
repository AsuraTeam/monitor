package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.entity.CmdbResourceOsTypeEntity;
import com.asura.resource.dao.CmdbResourceOsTypeDao;
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
 * @date 2016-07-28 11:31:44
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceOsTypeService")
public class CmdbResourceOsTypeService extends BaseService<CmdbResourceOsTypeEntity,CmdbResourceOsTypeDao>{

    @Resource(name="com.asura.resource.dao.CmdbResourceOsTypeDao")
    private CmdbResourceOsTypeDao dao;

    /**
     * 查询所有
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceOsTypeEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return dao.findAll(searchMap,pageBounds);
    }
}