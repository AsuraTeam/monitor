package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.SearchMap;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.quartz.MergerDataQuartz;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.List;

import static com.asura.monitor.graph.util.FileWriter.dataDir;
import static com.asura.monitor.graph.util.FileWriter.separator;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 20170204
 *
 * @author zhaozq
 * @version 1.0
 */

@Controller
@RequestMapping("/monitor/api/data/")
public class MergeController {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MergeController.class);

    @Autowired
    private CmdbResourceServerService serverService;

    // 防止重复调用方法
    private final static String MERGER_LOCK_FILE = dataDir + separator + "graph" + separator + "merger_lock";


    /**
     * 检查锁文件，防止重复执行
     * 每次间隔至少不少于20小时
     * @return
     */
    boolean checkLock(){
        File file = new File(MERGER_LOCK_FILE);
        if (file.exists()) {
            if (System.currentTimeMillis() / 1000 - file.lastModified() / 1000 > 60*60*20) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @RequestMapping("test")
    @ResponseBody
    public String  start(){
        MergerDataQuartz m = new MergerDataQuartz();
        m.start();
        return "ok";
    }

    /**
     * 数据合并接口
     *
     * @return
     */
    @RequestMapping("merger")
    @ResponseBody
    public String merger(String ip) {
        if (checkLock()){
            FileWriter.writeFile(MERGER_LOCK_FILE, System.currentTimeMillis()/1000+"", false);
        }else{
            LOGGER.error("merger ERROR: 程序不能重复执行");
            return "error";
        }

        SearchMap searchMap = new SearchMap();
        if (ip != null && ip.length() > 6) {
            searchMap.put("ipAddress", ip);
        }
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        List<CmdbResourceServerEntity> ips = serverService.getDataList(searchMap, "selectAllIp");
        for (CmdbResourceServerEntity entity:ips){
            LOGGER.info("添加merger到队列"+entity.getIpAddress());
            jedis.lpush(RedisUtil.app + "_"+ MonitorCacheConfig.mergerDataQueue, entity.getIpAddress());
        }
        return "ok";
    }
}
