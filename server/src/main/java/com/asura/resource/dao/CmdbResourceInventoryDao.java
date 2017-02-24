package com.asura.resource.dao;
import com.asura.common.dao.BaseDao;
import com.asura.resource.entity.CmdbResourceInventoryEntity;
import com.asura.resource.dao.CmdbResourceInventoryDao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import org.springframework.stereotype.Repository;

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
@Repository("com.asura.resource.dao.CmdbResourceInventoryDao")
public class CmdbResourceInventoryDao extends BaseDao<CmdbResourceInventoryEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbResourceInventoryEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,CmdbResourceInventoryEntity.class,searchMap,pageBounds);
     }
}