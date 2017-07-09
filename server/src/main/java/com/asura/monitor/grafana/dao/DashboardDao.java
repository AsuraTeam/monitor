package com.asura.monitor.grafana.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.grafana.entity.DashboardEntity;
import com.asura.monitor.grafana.dao.DashboardDao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import java.util.List;
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
 * @date 2017-07-08 17:33:29
 * @since 1.0
 */
@Repository("com.asura.monitor.grafana.dao.DashboardDao")
public class DashboardDao extends BaseDao<DashboardEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;

     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<DashboardEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,DashboardEntity.class,searchMap,pageBounds);
     }

     /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<DashboardEntity> getListData(SearchMap searchMap,  String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,DashboardEntity.class,searchMap);
    }}