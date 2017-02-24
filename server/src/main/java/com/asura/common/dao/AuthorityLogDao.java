package com.asura.common.dao;
import com.asura.common.dao.BaseDao;
import com.asura.common.entity.AuthorityLogEntity;
import com.asura.common.dao.AuthorityLogDao;
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
 * @date 2017-01-12 10:20:08
 * @since 1.0
 */
@Repository("com.asura.common.dao.AuthorityLogDao")
public class AuthorityLogDao extends BaseDao<AuthorityLogEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<AuthorityLogEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,AuthorityLogEntity.class,searchMap,pageBounds);
     }
}