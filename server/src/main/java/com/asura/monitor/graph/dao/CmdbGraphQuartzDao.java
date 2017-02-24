package com.asura.monitor.graph.dao;

import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.common.dao.BaseDao;
import com.asura.monitor.graph.entity.CmdbGraphQuartzEntity;
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
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016-08-08 08:52:15
 * @since 1.0
 */
@Repository("com.asura.monitor.graph.dao.CmdbGraphQuartzDao")
public class CmdbGraphQuartzDao extends BaseDao<CmdbGraphQuartzEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;



    /**
     * 公用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbGraphQuartzEntity> getData(SearchMap searchMap, String sqlId){
        if(mybatisDaoContext==null){
           mybatisDaoContext = CommentDao.setDao(mybatisDaoContext);
        }
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbGraphQuartzEntity.class,searchMap);
    }

}