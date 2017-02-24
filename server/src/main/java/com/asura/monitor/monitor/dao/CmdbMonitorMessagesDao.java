package com.asura.monitor.monitor.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.monitor.entity.CmdbMonitorMessagesEntity;
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
 *  报警信息管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/24 09:40
 * @since 1.0
 */
@Repository("com.asura.monitor.monitor.dao.CmdbMonitorMessagesDao")
public class CmdbMonitorMessagesDao extends BaseDao<CmdbMonitorMessagesEntity>{

    @Resource(name="monitor.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbMonitorMessagesEntity> findAll(SearchMap searchMap, PageBounds pageBounds){
        return mybatisDaoContext.findForPage(this.getClass().getName()+".selectByAll",CmdbMonitorMessagesEntity.class,searchMap,pageBounds);
    }


    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbMonitorMessagesEntity>  getMessageList(SearchMap searchMap,String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbMonitorMessagesEntity.class,searchMap);
    }


}


