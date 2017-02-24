package com.asura.resource.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.resource.entity.CmdbResourceCabinetEntity;
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
 * @date 2016-07-28 11:29:56
 * @since 1.0
 */
@Repository("com.asura.resource.dao.CmdbResourceCabinetDao")
public class CmdbResourceCabinetDao extends BaseDao<CmdbResourceCabinetEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;

    /**
     * 所有信息
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceCabinetEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return mybatisDaoContext.findForPage(this.getClass().getName()+".selectByAll",CmdbResourceCabinetEntity.class,searchMap,pageBounds);
    }

    /**
     * 公用查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbResourceCabinetEntity> selectCabinetName(SearchMap searchMap, String sqlId){
            return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbResourceCabinetEntity.class,searchMap);
    }
}
