package com.asura.monitor.report.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.report.entity.MonitorReportDayEntity;
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
 * @date 2016-11-03 10:33:03
 * @since 1.0
 */
@Repository("com.asura.monitor.configure.dao.MonitorReportDayDao")
public class MonitorReportDayDao extends BaseDao<MonitorReportDayEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorReportDayEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorReportDayEntity.class,searchMap,pageBounds);
     }

    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorReportDayEntity> getData(SearchMap searchMap, String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,MonitorReportDayEntity.class,searchMap);
    }

    /**
     * 更新过期的自动恢复的数据
     * @return
     */
    public  long updateAutoRecover(){
        return mybatisDaoContext.update("updateAutoRecover",new SearchMap<Object>());
    }
}