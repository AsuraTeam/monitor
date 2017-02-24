package com.asura.monitor.graph.thread;

import com.asura.framework.base.paging.SearchMap;
import com.asura.util.RedisUtil;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import org.snmp4j.Snmp;

import java.util.Random;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  一些线程用到的公共方法
 *
 * @author zhaoyun
 * @version 1.0
 * @date 2016/08/06 17:52:00
 * @since 1.0
 */
public class CommentThread {

    private  static RedisUtil redisUtil = new RedisUtil();

    public static int getServerId(CmdbResourceServerService serverService,String ip){
        String id = redisUtil.get("ipAddress"+ip);
        System.out.println(id);
        if(id!=null && id.length()>0){
            return Integer.valueOf(id);
        }
        SearchMap searchMap = new SearchMap();
        int serverId = 0;
        // 获取设备的id，从资源库获取
        searchMap.put("ipAddress",ip);
        CmdbResourceServerEntity server = serverService.selectServerid(searchMap);
        if(server!=null){
            serverId = server.getServerId();
            redisUtil.set("ipAddress"+ip,serverId+"");
        }
        return serverId;
    }



    /**
     * 关闭snmp
     * @param snmp
     */
    public  static  void closeSnmp(Snmp snmp){
        try{
            snmp.close();
        }catch (Exception e){

        }
    }

    /**
     * 停止的执行，避免高并发
     */
    public static  void sleep(){

        Random r = new Random();
        try {
            int sleep = r.nextInt(30000);
            Thread.sleep(sleep);
        }catch (Exception e){

        }
    }
}
