package com.asura.resource.service;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.service.BaseService;
import com.asura.resource.dao.CmdbResourceServerDao;
import com.asura.resource.entity.CmdbResourceServerEntity;
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
 * @date 2016-07-28 11:43:59
 * @since 1.0
 */
@Service("com.asura.resource.service.CmdbResourceServerService")
public class CmdbResourceServerService extends BaseService<CmdbResourceServerEntity,CmdbResourceServerDao> {

    @Resource(name = "com.asura.resource.dao.CmdbResourceServerDao")
    private CmdbResourceServerDao dao;

    /**
     * 批量更新ping
     * @param map
     */
    public void updatePing(SearchMap map) {
        dao.updatePing(map);
    }
    /**
     * 查询所有
     *
     * @param searchMap
     * @param pageBounds
     *
     * @return
     */
    public PagingResult<CmdbResourceServerEntity> findAll(SearchMap searchMap, PageBounds pageBounds,String sqlId) {
        return dao.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 查询宿主机
     *
     * @return
     */
    public List<CmdbResourceServerEntity> findHosts(SearchMap searchMap) {
        return dao.findHosts(searchMap);
    }

    /**
     * 查询总数
     *
     * @return
     */
    public CmdbResourceServerEntity countResourceTotle() {
        return dao.countResourceTotle();
    }

    /**
     * 获取关机的数量
     * @return
     */
    public CmdbResourceServerEntity countResourceIsOff(){
        return dao.countResourceIsOff();
    }
    /**
     * 查询最大ID
     */
    public CmdbResourceServerEntity selectMaxId() {
        return dao.selectMaxId();
    }

    /**
     * 获取服务器的ID
     * @return
     */
    public CmdbResourceServerEntity selectServerid(SearchMap map){
        return dao.selectServerid(map);
    }
    /**
     * 通用
     *
     * @param searchMap
     * @param sqlId
     *
     * @return
     */
    public List<CmdbResourceServerEntity> getDataList(SearchMap searchMap, String sqlId) {
        return dao.getDataList(searchMap, sqlId);
    }

    /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
    public PagingResult<CmdbResourceServerEntity> selectVmSomeHost(SearchMap searchMap, PageBounds pageBounds) {
        return  dao.selectVmSomeHost(searchMap,pageBounds);
    }
}