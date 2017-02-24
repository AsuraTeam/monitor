package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.dao.CmdbResourceCabinetDao;
import com.asura.resource.entity.CmdbResourceCabinetEntity;
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
 * @date 2016-07-28 11:29:56
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceCabinetService")
public class CmdbResourceCabinetService extends BaseService<CmdbResourceCabinetEntity,CmdbResourceCabinetDao> {

    @Resource(name = "com.asura.resource.dao.CmdbResourceCabinetDao")
    private CmdbResourceCabinetDao dao;

    /**
     * 查询所有
     *
     * @param searchMap
     * @param pageBounds
     *
     * @return
     */
    public PagingResult<CmdbResourceCabinetEntity> findAll(SearchMap searchMap, PageBounds pageBounds) {
        return dao.findAll(searchMap, pageBounds);
    }

    /**
     * 查询公用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbResourceCabinetEntity> selectCabinetName(SearchMap searchMap, String sqlId) {
        return dao.selectCabinetName(searchMap, sqlId);

    }
}