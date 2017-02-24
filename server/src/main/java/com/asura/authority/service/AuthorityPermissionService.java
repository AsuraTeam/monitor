package com.asura.authority.service;
import com.asura.common.service.BaseService;
import com.asura.authority.entity.AuthorityPermissionEntity;
import com.asura.authority.dao.AuthorityPermissionDao;
import org.springframework.stereotype.Service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

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
@Service("com.asura.authority.service.AuthorityPermissionService")
public class AuthorityPermissionService extends BaseService<AuthorityPermissionEntity,AuthorityPermissionDao>{

    @Resource(name="com.asura.authority.dao.AuthorityPermissionDao")
    private AuthorityPermissionDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<AuthorityPermissionEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}