package com.asura.monitor.configure.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.configure.entity.MonitorAlarmConfigureEntity;
import com.asura.monitor.configure.dao.MonitorAlarmConfigureDao;
import org.springframework.stereotype.Service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;import java.util.List;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;

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
 * @date 2017-07-15 08:49:22
 * @since 1.0
 */
@Service("com.asura.monitor.configure.service.MonitorAlarmConfigureService")
public class MonitorAlarmConfigureService extends BaseService<MonitorAlarmConfigureEntity,MonitorAlarmConfigureDao>{

    @Resource(name="com.asura.monitor.configure.dao.MonitorAlarmConfigureDao")
    private MonitorAlarmConfigureDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorAlarmConfigureEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

         /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorAlarmConfigureEntity> getListData(SearchMap searchMap, String sqlId){
        return dao.getListData(searchMap, sqlId);
    }}