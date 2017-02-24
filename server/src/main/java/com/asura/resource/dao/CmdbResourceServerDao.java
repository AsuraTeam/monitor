package com.asura.resource.dao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.dao.BaseDao;
import com.asura.resource.entity.CmdbResourceServerEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("com.asura.resource.dao.CmdbResourceServerDao")
public class CmdbResourceServerDao extends BaseDao<CmdbResourceServerEntity> {

    @Resource(name="monitor.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 批量更新ping
     * @param map
     */
    public void updatePing(SearchMap map) {
         mybatisDaoContext.update(this.getClass().getName() + ".updatePing", map);
    }

    /**
     * 所有信息
     * @param searchMap
     * @param pageBounds
     * @retur
     */
    public PagingResult<CmdbResourceServerEntity> findAll(SearchMap searchMap, PageBounds pageBounds ,String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,CmdbResourceServerEntity.class,searchMap,pageBounds);
    }


    /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceServerEntity> selectVmSomeHost(SearchMap searchMap, PageBounds pageBounds){
        return mybatisDaoContext.findForPage(this.getClass().getName()+".selectVmSomeHost",CmdbResourceServerEntity.class,searchMap,pageBounds);
    }
    /**
     * 查找宿主机
     * @return
     */
    public List<CmdbResourceServerEntity> findHosts(SearchMap searchMap){
        return mybatisDaoContext.findAll(this.getClass().getName()+".findHosts",CmdbResourceServerEntity.class,searchMap);
    }

    /**
     * 查找总数
     * @return
     */
    public CmdbResourceServerEntity countResourceTotle(){
        return mybatisDaoContext.findOne(this.getClass().getName()+".selectTotle",CmdbResourceServerEntity.class);
    }

    /**
     * 获取关机数量
     * @return
     */
    public CmdbResourceServerEntity countResourceIsOff(){
        return mybatisDaoContext.findOne(this.getClass().getName()+".selectIsOff",CmdbResourceServerEntity.class);
    }

       /**
     * 获取最大ID
     * @return
     */
    public CmdbResourceServerEntity selectMaxId(){
        return mybatisDaoContext.findOne(this.getClass().getName()+".selectMaxId",CmdbResourceServerEntity.class);
    }

    /**
     * 获取服务器的ID
     * @return
     */
    public CmdbResourceServerEntity selectServerid(SearchMap map){
        return mybatisDaoContext.findOne(this.getClass().getName()+".selectServerid",CmdbResourceServerEntity.class,map);
    }


    /**
     * 通用
     * @param searchMap
     * @param sqlId
     * @return
     */
    public List<CmdbResourceServerEntity>  getDataList(SearchMap searchMap, String sqlId){
        return mybatisDaoContext.findAll(this.getClass().getName()+"."+sqlId,CmdbResourceServerEntity.class,searchMap);
    }
}