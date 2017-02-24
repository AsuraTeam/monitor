package com.asura.monitor.graph.service;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.entity.MonitorImagesCollectionEntity;
import com.asura.monitor.graph.dao.MonitorImagesCollectionDao;
import org.springframework.stereotype.Service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
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
 * @date 2017-02-14 14:58:43
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.MonitorImagesCollectionService")
public class MonitorImagesCollectionService extends BaseService<MonitorImagesCollectionEntity,MonitorImagesCollectionDao>{

    @Resource(name="com.asura.monitor.graph.dao.MonitorImagesCollectionDao")
    private MonitorImagesCollectionDao dao;
    /**
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<MonitorImagesCollectionEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }
}