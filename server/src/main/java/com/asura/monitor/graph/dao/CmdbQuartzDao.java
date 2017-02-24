package com.asura.monitor.graph.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.graph.entity.CmdbQuartzEntity;
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
 * @date 2016-08-10 11:50:49
 * @since 1.0
 */
@Repository("com.asura.monitor.graph.dao.CmdbQuartzDao")
public class CmdbQuartzDao extends BaseDao<CmdbQuartzEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;

    private CmdbGraphQuartzDao cmdbGraphQuartzDao = new CmdbGraphQuartzDao();

     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbQuartzEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
         if(mybatisDaoContext==null){
             mybatisDaoContext = CommentDao.setDao(mybatisDaoContext);
         }
         return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,CmdbQuartzEntity.class,searchMap,pageBounds);
     }
}