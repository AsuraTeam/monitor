package com.asura.monitor.top.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.top.entity.MonitorTopImagesEntity;
import com.asura.monitor.top.dao.MonitorTopImagesDao;
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
 * @date 2017-08-01 18:10:41
 * @since 1.0
 */
@Service("com.asura.monitor.top.service.MonitorTopImagesService")
public class MonitorTopImagesService extends BaseService<MonitorTopImagesEntity,MonitorTopImagesDao>{

    @Resource(name="com.asura.monitor.top.dao.MonitorTopImagesDao")
    private MonitorTopImagesDao dao;

    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorTopImagesEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

         /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorTopImagesEntity> getListData(SearchMap searchMap, String sqlId){
        return dao.getListData(searchMap, sqlId);
    }}