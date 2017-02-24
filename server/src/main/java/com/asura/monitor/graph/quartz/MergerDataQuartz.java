package com.asura.monitor.graph.quartz;

import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.thread.MergerThread;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * 通过队列方式执行数据合并
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class MergerDataQuartz {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MergerDataQuartz.class);

    public void start() {
        // 判断是否没有lock标志，没有的话，就添加，每一天跑一次数据
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        String result = redisUtil.get(MonitorCacheConfig.mergerDataLock);
        if(result == null || ! result.contains("1") ){
            redisUtil.setex(MonitorCacheConfig.mergerDataLock, 86400, "1");
            File files = new File(dataDir+separator+"graph");
            File[] fileList = files.listFiles();
            for (File file: fileList){
                if (file.isDirectory()) {
                    LOGGER.info("添加 " + file.getName() + "到合并队列");
                    jedis.lpush(RedisUtil.app+"_"+MonitorCacheConfig.mergerDataQueue, file.getName());
                }
            }
        }else {
            startQuartz(redisUtil);
        }
    }


    /**
     * 获取需要合并的天数
     * @return
     */
    ArrayList getDays(){
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(3);
        arrayList.add(7);
        arrayList.add(15);
        arrayList.add(30);
        arrayList.add(60);
        arrayList.add(90);
        arrayList.add(120);
        arrayList.add(180);
        arrayList.add(240);
        arrayList.add(360);
        return arrayList;
    }

    void startQuartz(RedisUtil redisUtil){
        ArrayList<Integer> arrayList = getDays();
        List<CmdbResourceServerEntity> ips;
        CmdbResourceServerEntity cmdbResourceServerEntity;
        String ip;
        long size = redisUtil.llen(MonitorCacheConfig.mergerDataQueue);
        if (size > 0) {
            for (int qid = 0; qid < 1000; qid++) {
                ip = redisUtil.rpop(MonitorCacheConfig.mergerDataQueue);
                if (ip != null && ip.length() > 5) {
                    ips = new ArrayList<>();
                    cmdbResourceServerEntity = new CmdbResourceServerEntity();
                    cmdbResourceServerEntity.setIpAddress(ip);
                    ips.add(cmdbResourceServerEntity);
                    long start = System.currentTimeMillis() / 1000;
                    LOGGER.info("开始启动任务计划 " + ip + " start " + start);
                    ExecutorService executors = Executors.newFixedThreadPool(1);
                    MergerThread mergerThread = new MergerThread(ips, arrayList);
                    executors.execute(mergerThread);
                    executors.shutdown();
                    try {
                        while (!executors.isTerminated()) {
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                    }
                    LOGGER.info("开始启动任务计划 " + ip + " end " + (System.currentTimeMillis() / 1000 - start));
                }else{
                    break;
                }
            }
        }
    }


}
