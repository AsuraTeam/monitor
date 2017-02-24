package com.asura.monitor.graph.service;
import com.asura.framework.base.paging.SearchMap;
import com.asura.common.service.BaseService;
import com.asura.monitor.graph.dao.CmdbGraphQuartzDao;
import com.asura.monitor.graph.entity.CmdbGraphQuartzEntity;
import org.springframework.stereotype.Service;

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
 * @date 2016-08-08 08:52:15
 * @since 1.0
 */
@Service("com.asura.monitor.graph.service.CmdbGraphQuartzService")
public class CmdbGraphQuartzService extends BaseService<CmdbGraphQuartzEntity,CmdbGraphQuartzDao>{

    @Resource(name="com.asura.monitor.graph.dao.CmdbGraphQuartzDao")
    private CmdbGraphQuartzDao dao = new CmdbGraphQuartzDao();

    /**
     * 公用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbGraphQuartzEntity> getData(SearchMap searchMap, String sqlId){
        return dao.getData(searchMap,sqlId);
    }
}