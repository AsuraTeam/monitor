package com.asura.authority.dao;
import com.asura.common.dao.BaseDao;
import com.asura.authority.entity.AuthorityPermissionEntity;
import com.asura.authority.dao.AuthorityPermissionDao;
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
 * @date 2017-01-09 14:22:24
 * @since 1.0
 */
@Repository("com.asura.authority.dao.AuthorityPermissionDao")
public class AuthorityPermissionDao extends BaseDao<AuthorityPermissionEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<AuthorityPermissionEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,AuthorityPermissionEntity.class,searchMap,pageBounds);
     }
}