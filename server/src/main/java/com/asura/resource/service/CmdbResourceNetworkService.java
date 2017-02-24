package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.dao.CmdbResourceNetworkDao;
import com.asura.resource.entity.CmdbResourceNetworkEntity;
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
 * @date 2016-08-15 18:10:32
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceNetworkService")
public class CmdbResourceNetworkService extends BaseService<CmdbResourceNetworkEntity,CmdbResourceNetworkDao>{

    @Resource(name="com.asura.resource.dao.CmdbResourceNetworkDao")
    private CmdbResourceNetworkDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceNetworkEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     *
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbResourceNetworkEntity> getDataList(SearchMap searchMap, String sqlId) {
        return dao.getDataList(searchMap, sqlId);
    }

}