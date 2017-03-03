package com.asura.monitor.monitor.controller;

import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.controller.AllGraphController;
import com.asura.monitor.monitor.entity.AgentStatusEntity;
import com.asura.util.CheckUtil;
import com.asura.util.HttpUtil;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.monitor.controller.MonitorGlobaltController.getAgentOkMap;
import static com.asura.monitor.monitor.controller.MonitorGlobaltController.getGroupsMap;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * agent运行状态数据
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/agent/status")
public class AgentStatusController {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusController.class);
    private static final Gson gson = new Gson();
    private static final RedisUtil redisUtil = new RedisUtil();

    @Autowired
    private IndexController indexController;

    /**
     *
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/monitor/agent/status/list";
    }

    /**
     *
     * @param groupsId
     * @return
     */
    @RequestMapping(value = "faildHosts", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String faildHosts(String groupsId){
        Jedis jedis = redisUtil.getJedis();
        ArrayList<String> faildList = new ArrayList();
        HashSet<String> hosts = getHosts(groupsId);
        Map<String, String> okMap = getAgentOkMap(MonitorCacheConfig.cacheAgentIsOk, groupsId);
        Map<String, String> faildMap = getAgentOkMap(MonitorCacheConfig.cacheAgentUnreachable, groupsId);
        for (String hostId: hosts){
            if (okMap.containsKey(hostId) || faildMap.containsKey(hostId)){
                continue;
            }
            faildList.add(hostId);
        }
        String ip;
        ArrayList hostList = new ArrayList();
        for (String k: faildList){
            ip = jedis.get(RedisUtil.app+"_"+ MonitorCacheConfig.cacheHostIdToIp + k);
            if (CheckUtil.checkString(ip)){
                hostList.add(ip);
            }
        }
        return gson.toJson(hostList);
    }

    /**
     *
     * @return
     */
    @RequestMapping("initSave")
    @ResponseBody
    public ResponseVo initSave(HttpServletRequest request, String groupsId){
        indexController.logSave(request, "初始化监控agent组id:" + groupsId);
        HashSet<String> hosts = getHosts(groupsId);
        Map map;
        String url;
        String addr;
        for (String server: hosts) {
            addr = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp+server);
            if(CheckUtil.checkString(addr)) {
                map = AllGraphController.getHostInfo(addr, redisUtil, gson);
                url = "http://" + map.get("server") + ":" + map.get("port") + "/monitor/init";
                HttpUtil.sendGet(url);
            }else{
                logger.error("获取server信息失败 " + server);
            }
        }
        return ResponseVo.responseOk(null);
    }

    /**
     * 获取组
     * @param groupsId
     * @return
     */
    HashSet getHosts(String groupsId){
        String result = redisUtil.get(MonitorCacheConfig.cacheGroupsHosts + groupsId);
        if (CheckUtil.checkString(result)){
            HashSet list = gson.fromJson(result, HashSet.class);
            return list;
        }
        return new HashSet();
    }

    /**
     * 获取每个组应该有的机器组
     * @param groupsId
     * @return
     */
    int getGroupsTotle(String groupsId){
        HashSet list = getHosts(groupsId);
        return list.size();
    }

    /**
     * @return
     */
    @RequestMapping(value = "getAgentStatus", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getAgentStatus(){
        List<AgentStatusEntity> entityList = new ArrayList<>();
        AgentStatusEntity agentStatusEntity ;
        Map<String,String> groupMap = getGroupsMap();
        Map<String, String> faildMap;
        Map<String, String> okMap;
        for(Map.Entry<String,String> entry: groupMap.entrySet()) {
            agentStatusEntity = new AgentStatusEntity();
            okMap = getAgentOkMap(MonitorCacheConfig.cacheAgentIsOk, entry.getKey());
            faildMap = getAgentOkMap(MonitorCacheConfig.cacheAgentUnreachable, entry.getKey());
            agentStatusEntity.setGroupsName(entry.getKey());
            agentStatusEntity.setOk(okMap.size());
            agentStatusEntity.setFaild(faildMap.size());
            agentStatusEntity.setGroupsName(groupMap.get(entry.getKey()));
            agentStatusEntity.setTotle(getGroupsTotle(entry.getKey()));
            agentStatusEntity.setGroupsId(entry.getKey());
            entityList.add(agentStatusEntity);
        }
        return PageResponse.getList(entityList, 1);
    }
}