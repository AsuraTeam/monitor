package com.asura.monitor.monitor.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.monitor.entity.CmdbMonitorInformationEntity;
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
 *  监控数据管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/23 18:40
 * @since 1.0
 */
@Repository("com.asura.monitor.monitor.dao.MonitorInformationDao")
public class MonitorInformationDao extends BaseDao<CmdbMonitorInformationEntity>{

    @Resource(name="monitor.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbMonitorInformationEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return mybatisDaoContext.findForPage(this.getClass().getName()+".selectByAll",CmdbMonitorInformationEntity.class,searchMap,pageBounds);
    }


    /**
     * 查询报警状态统计
     * @return
     */
    public java.util.List<CmdbMonitorInformationEntity> selectAlarmCount(SearchMap map){
        return mybatisDaoContext.findAll(this.getClass().getName()+".selectAlarmCount",CmdbMonitorInformationEntity.class,map);
    }

    /**
     * 查询检查命令统计
     * @param map
     * @return
     */
    public List<CmdbMonitorInformationEntity> selectCheckCommandCount(SearchMap map){
        return mybatisDaoContext.findAll(this.getClass().getName()+".selectCheckCommandCount",CmdbMonitorInformationEntity.class,map);
    }

    /**
     * 获取每个组的指定
     * @param map
     * @return
     */
    public List<CmdbMonitorInformationEntity> selectGroupsMonitorInfo(SearchMap map,String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbMonitorInformationEntity.class,map);
    }

}


