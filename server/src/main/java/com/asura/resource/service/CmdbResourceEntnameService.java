package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.entity.CmdbResourceEntnameEntity;
import com.asura.resource.dao.CmdbResourceEntnameDao;
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
 * @date 2016-07-28 11:30:55
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceEntnameService")
public class CmdbResourceEntnameService extends BaseService<CmdbResourceEntnameEntity,CmdbResourceEntnameDao>{

    @Resource(name="com.asura.resource.dao.CmdbResourceEntnameDao")
    private CmdbResourceEntnameDao dao;

    /**
     * 查询所有
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceEntnameEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return dao.findAll(searchMap,pageBounds);
    }
}