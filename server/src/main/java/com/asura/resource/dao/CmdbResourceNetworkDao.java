package com.asura.resource.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.resource.entity.CmdbResourceNetworkEntity;
import org.springframework.stereotype.Repository;

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
@Repository("com.asura.resource.dao.CmdbResourceNetworkDao")
public class CmdbResourceNetworkDao extends BaseDao<CmdbResourceNetworkEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext dao;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbResourceNetworkEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return dao.findForPage(this.getClass().getName()+"."+sqlId,CmdbResourceNetworkEntity.class,searchMap,pageBounds);
     }

    /**
     * 通用
     *
     * @param searchMap
     * @param sqlId
     *
     * @return
     */
    public List<CmdbResourceNetworkEntity> getDataList(SearchMap searchMap, String sqlId) {
        return dao.findAll(this.getClass().getName()+"."+sqlId,CmdbResourceNetworkEntity.class,searchMap);
    }
}