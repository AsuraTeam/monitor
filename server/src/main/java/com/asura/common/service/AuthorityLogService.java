package com.asura.common.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.AuthorityLogDao;
import com.asura.common.entity.AuthorityLogEntity;
import org.springframework.stereotype.Service;

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
@Service("com.asura.common.service.AuthorityLogService")
public class AuthorityLogService extends BaseService<AuthorityLogEntity,AuthorityLogDao>{





    @Resource(name="com.asura.common.dao.AuthorityLogDao")
    private AuthorityLogDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<AuthorityLogEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }


}