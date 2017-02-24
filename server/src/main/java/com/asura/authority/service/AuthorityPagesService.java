package com.asura.authority.service;
import com.asura.common.service.BaseService;
import com.asura.authority.entity.AuthorityPagesEntity;
import com.asura.authority.dao.AuthorityPagesDao;
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
 * @date 2017-01-09 14:22:59
 * @since 1.0
 */
@Service("com.asura.authority.service.AuthorityPagesService")
public class AuthorityPagesService extends BaseService<AuthorityPagesEntity,AuthorityPagesDao> {

    @Resource(name="com.asura.authority.dao.AuthorityPagesDao")
    private AuthorityPagesDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<AuthorityPagesEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}