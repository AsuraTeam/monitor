package com.asura.monitor.grafana.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.grafana.entity.DataSourceEntity;
import com.asura.monitor.grafana.dao.DataSourceDao;
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
 * @date 2017-07-08 17:32:01
 * @since 1.0
 */
@Service("com.asura.monitor.grafana.service.DataSourceService")
public class DataSourceService extends BaseService<DataSourceEntity,DataSourceDao>{

    @Resource(name="com.asura.monitor.grafana.dao.DataSourceDao")
    private DataSourceDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<DataSourceEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

         /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<DataSourceEntity> getListData(SearchMap searchMap, String sqlId){
        return dao.getListData(searchMap, sqlId);
    }}