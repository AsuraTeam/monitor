package com.asura.monitor.graph.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.graph.entity.CmdbGraphNameEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


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
 * @date 2016-08-05 21:27:24
 * @since 1.0
 */
@Repository("com.asura.release.dao.CmdbGraphNameDao")
public class CmdbGraphNameDao extends BaseDao<CmdbGraphNameEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbGraphNameEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return mybatisDaoContext.findForPage(this.getClass().getName()+".selectByAll",CmdbGraphNameEntity.class,searchMap,pageBounds);
     }

    /**
     * 公用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbGraphNameEntity> getData(SearchMap searchMap, String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbGraphNameEntity.class,searchMap);
    }
}