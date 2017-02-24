package com.asura.common.dao;
import com.asura.common.dao.BaseDao;
import com.asura.common.entity.AuthorityUserEntity;
import com.asura.common.dao.AuthorityUserDao;
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
 * @date 2017-01-31 08:14:01
 * @since 1.0
 */
@Repository("com.asura.common.dao.AuthorityUserDao")
public class AuthorityUserDao extends BaseDao<AuthorityUserEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<AuthorityUserEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,AuthorityUserEntity.class,searchMap,pageBounds);
     }
}