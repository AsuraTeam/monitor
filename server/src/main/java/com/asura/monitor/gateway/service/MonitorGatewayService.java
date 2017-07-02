package com.asura.monitor.gateway.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.gateway.entity.MonitorGatewayEntity;
import com.asura.monitor.gateway.dao.MonitorGatewayDao;
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
 * @date 2017-07-02 09:49:13
 * @since 1.0
 */
@Service("com.asura.monitor.gateway.service.MonitorGatewayService")
public class MonitorGatewayService extends BaseService<MonitorGatewayEntity,MonitorGatewayDao>{

    @Resource(name="com.asura.monitor.gateway.dao.MonitorGatewayDao")
    private MonitorGatewayDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorGatewayEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

         /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorGatewayEntity> getListData(SearchMap searchMap, String sqlId){
        return dao.getListData(searchMap, sqlId);
    }}