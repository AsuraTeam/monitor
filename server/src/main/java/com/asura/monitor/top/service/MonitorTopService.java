package com.asura.monitor.top.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.top.entity.MonitorTopEntity;
import com.asura.monitor.top.dao.MonitorTopDao;
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
 * @date 2017-08-18 08:57:38
 * @since 1.0
 */
@Service("com.asura.monitor.top.service.MonitorTopService")
public class MonitorTopService extends BaseService<MonitorTopEntity,MonitorTopDao>{

    @Resource(name="com.asura.monitor.top.dao.MonitorTopDao")
    private MonitorTopDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorTopEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

         /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorTopEntity> getListData(SearchMap searchMap, String sqlId){
        return dao.getListData(searchMap, sqlId);
    }}