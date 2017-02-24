package com.asura.monitor.configure.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
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
 * @date 2016-08-20 10:37:54
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorScriptsDao")
public class MonitorScriptsDao extends BaseDao<MonitorScriptsEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorScriptsEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorScriptsEntity.class,searchMap,pageBounds);
     }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorScriptsEntity> getDataList(SearchMap searchMap, String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,MonitorScriptsEntity.class,searchMap);
    }
}