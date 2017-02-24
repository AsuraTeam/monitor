package com.asura.monitor.graph.quartz;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.monitor.graph.entity.CmdbGraphQuartzEntity;
import com.asura.monitor.graph.entity.CmdbQuartzEntity;
import com.asura.monitor.graph.service.CmdbGraphQuartzService;
import com.asura.monitor.graph.service.CmdbQuartzService;
import com.asura.monitor.graph.thread.CommentThread;
import com.asura.util.DateUtil;
import com.asura.util.HttpUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.List;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 任务计划入口
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/07 11:50:11
 * @since 1.0
 */
public class MakeDataQuartz {

    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MakeDataQuartz.class);


    @Autowired
    private CmdbGraphQuartzService graphQuartzService = new CmdbGraphQuartzService();

    @Autowired
    private CmdbQuartzService quartzService = new CmdbQuartzService();

    /**
     * 保存任务计划
     * @param name
     * @throws Exception
     */
    public void saveQuartz(String name)throws Exception{

        CmdbGraphQuartzEntity cmdbGraphQuartzEntity = new CmdbGraphQuartzEntity();
        cmdbGraphQuartzEntity.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        cmdbGraphQuartzEntity.setCreateTime(BigInteger.valueOf(DateUtil.getDateStampInteter()));
        cmdbGraphQuartzEntity.setName(name);
        graphQuartzService.save(cmdbGraphQuartzEntity);
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean getQuartz(String name){
        SearchMap searchMap = new SearchMap();
        searchMap.put("name",name);
        List<CmdbGraphQuartzEntity> data = graphQuartzService.getData(searchMap,"selectByAll");
        if(data.size()>0){
            int now = DateUtil.getDateStampInteter();
            if((now - Integer.valueOf(data.get(0).getCreateTime()+"")>3000000)){
                LOGGER.warn("删除过期任务计划");
                return true;
            }
            LOGGER.warn("任务计划已经被执行:"+data.get(0).getIpAddress()+" "+data.get(0).getCreateTime());
            return false;
        }
        return true;
    }

    /**
     *执行完成后删除
     * @param name
     */
    public void deleteQuartz(String name){
        CmdbGraphQuartzEntity cmdbGraphQuartzEntity = new CmdbGraphQuartzEntity();
        cmdbGraphQuartzEntity.setName(name);
        graphQuartzService.delete(cmdbGraphQuartzEntity);
    }

    /**
     * 执行任务计划
     * @param name
     * @throws Exception
     */
    public void execQuartz(String name, String url)throws Exception{

        CommentThread.sleep();

        if(getQuartz(name)){
            deleteQuartz(name);
            saveQuartz(name);
            try {
                HttpUtil.post(url, "", "UTF-8");
            }catch (Exception e){
                deleteQuartz(name);
            }
        }
        CommentThread.sleep();
        deleteQuartz(name);
    }

    /**
     * 主程序开始执行
     * @throws Exception
     */
    public void start() throws Exception{

        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000,1);
        PagingResult<CmdbQuartzEntity> entity = quartzService.findAll(searchMap,pageBounds,"selectByAll");
        for(CmdbQuartzEntity m:entity.getRows()) {
            LOGGER.info("开始执行任务计划"+m.getQuartzName());
            CommentThread.sleep();
            CommentThread.sleep();
            execQuartz(m.getQuartzName(), m.getQuartzUrl());
            CommentThread.sleep();
            CommentThread.sleep();
            LOGGER.info("结束执行任务计划");
        }
    }
}
