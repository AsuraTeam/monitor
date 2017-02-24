package com.asura.resource.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.resource.entity.CmdbResourceNetworkAddressEntity;
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
 * @date 2016-08-15 18:28:17
 * @since 1.0
 */
@Repository("com.asura.resource.dao.CmdbResourceNetworkAddressDao")
public class CmdbResourceNetworkAddressDao extends BaseDao<CmdbResourceNetworkAddressEntity> {

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext dao;

     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbResourceNetworkAddressEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return dao.findForPage(this.getClass().getName()+"."+sqlId,CmdbResourceNetworkAddressEntity.class,searchMap,pageBounds);
     }


}