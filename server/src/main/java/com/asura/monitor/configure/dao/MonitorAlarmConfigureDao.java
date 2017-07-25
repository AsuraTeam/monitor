package com.asura.monitor.configure.dao;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.configure.entity.MonitorAlarmConfigureEntity;
import com.asura.monitor.configure.dao.MonitorAlarmConfigureDao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import java.util.List;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import org.springframework.stereotype.Repository;

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
@Repository("com.asura.monitor.configure.dao.MonitorAlarmConfigureDao")
public class MonitorAlarmConfigureDao extends BaseDao<MonitorAlarmConfigureEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;

     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<MonitorAlarmConfigureEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,MonitorAlarmConfigureEntity.class,searchMap,pageBounds);
     }

     /**
     * 通用数据查询
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<MonitorAlarmConfigureEntity> getListData(SearchMap searchMap,  String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,MonitorAlarmConfigureEntity.class,searchMap);
    }}