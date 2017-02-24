package com.asura.monitor.monitor.service;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.monitor.monitor.dao.MonitorInformationDao;
import com.asura.monitor.monitor.entity.CmdbMonitorInformationEntity;
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
 *  监控数据管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/23 18:40
 * @since 1.0
 */
@Service("com.asura.monitor.monitor.service.MonitorInformationService")
public class MonitorInformationService extends BaseService<CmdbMonitorInformationEntity,MonitorInformationDao>{

    @Resource(name="com.asura.monitor.monitor.dao.MonitorInformationDao")
    private MonitorInformationDao monitorInformationDao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbMonitorInformationEntity> findAll(SearchMap searchMap, PageBounds pageBounds) {
        return monitorInformationDao.findAll(searchMap, pageBounds);
    }

    /**
     * 统计状态
     * @return
     */
    public java.util.List<CmdbMonitorInformationEntity> selectAlarmCount(SearchMap map){
        return monitorInformationDao.selectAlarmCount(map);
    }

    /**
     * 统计各监控命令数量
     * @param map
     * @return
     */
    public java.util.List<CmdbMonitorInformationEntity> selectCheckCommandCount(SearchMap map){
        return monitorInformationDao.selectCheckCommandCount(map);
    }

    /**
     * 获取每个组的指定
     * @param map
     * @return
     */
    public java.util.List<CmdbMonitorInformationEntity> selectGroupsMonitorInfo(SearchMap map,String sqlId){
        return monitorInformationDao.selectGroupsMonitorInfo(map,sqlId);
    }
}
