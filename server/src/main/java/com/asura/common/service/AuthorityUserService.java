package com.asura.common.service;
import com.asura.common.service.BaseService;
import com.asura.common.entity.AuthorityUserEntity;
import com.asura.common.dao.AuthorityUserDao;
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
 * @date 2017-01-31 08:14:01
 * @since 1.0
 */
@Service("com.asura.common.service.AuthorityUserService")
public class AuthorityUserService extends BaseService<AuthorityUserEntity,AuthorityUserDao>{

    @Resource(name="com.asura.common.dao.AuthorityUserDao")
    private AuthorityUserDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<AuthorityUserEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}