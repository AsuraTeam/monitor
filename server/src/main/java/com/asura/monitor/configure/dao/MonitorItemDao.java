package com.asura.monitor.configure.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorItemEntity;
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
 * @date 2016-08-20 16:45:10
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorItemDao")
public class MonitorItemDao extends BaseDao<MonitorItemEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorItemEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorItemEntity.class,searchMap,pageBounds);
     }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorItemEntity> getDataList(SearchMap searchMap, String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,MonitorItemEntity.class,searchMap);
    }
}