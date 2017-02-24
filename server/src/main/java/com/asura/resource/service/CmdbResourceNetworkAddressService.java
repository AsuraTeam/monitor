package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.common.service.BaseService;
import com.asura.resource.dao.CmdbResourceNetworkAddressDao;
import com.asura.resource.entity.CmdbResourceNetworkAddressEntity;
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
 * @date 2016-08-15 18:28:17
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceNetworkAddressService")
public class CmdbResourceNetworkAddressService extends BaseService<CmdbResourceNetworkAddressEntity,CmdbResourceNetworkAddressDao> {

    @Resource(name = "com.asura.resource.dao.CmdbResourceNetworkAddressDao")
    private CmdbResourceNetworkAddressDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     *
     * @return
     */
    public PagingResult<CmdbResourceNetworkAddressEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        if (searchMap == null) {
            searchMap = new SearchMap();
        }
        if (pageBounds == null) {
            pageBounds = PageResponse.getPageBounds(10000, 1);
        }

        return dao.findAll(searchMap, pageBounds, sqlId);
    }



}