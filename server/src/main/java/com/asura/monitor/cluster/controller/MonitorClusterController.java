package com.asura.monitor.cluster.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.cluster.entity.MonitorClusterConfigureEntity;
import com.asura.monitor.cluster.service.MonitorClusterConfigureService;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.controller.AllGraphController;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.resource.controller.ServerController;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 集群监控数据处理
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/monitor/cluster/")
public class MonitorClusterController {

    private final Gson GSON = new Gson();

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private MonitorClusterConfigureService clusterConfigureService;

    @Autowired
    private IndexController indexController;

    @Autowired
    private ServerController serverController;

    @Autowired
    private AllGraphController graphController;

    /**
     * 模板列表
     *
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model) {
        model = serverController.getData(model);
        if (id > 0) {
            MonitorClusterConfigureEntity result = clusterConfigureService.findById(id, MonitorClusterConfigureEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/cluster/add";
    }

    /**
     * 入口页面
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/monitor/cluster/list";
    }

    /**
     * 获取数据
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String contactsListData(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)) {
            searchMap.put("key", search);
        }
        PagingResult<MonitorClusterConfigureEntity> result = clusterConfigureService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * @param groupsName
     * @param clusterName
     *
     * @return
     */
    public PagingResult<MonitorClusterConfigureEntity> getClusterServer(String groupsName, String clusterName) {
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        SearchMap searchMap = new SearchMap();
        searchMap.put("groupsName", groupsName);
        searchMap.put("clusterName", clusterName);
        PagingResult<MonitorClusterConfigureEntity> result = clusterConfigureService.findAll(searchMap, pageBounds, "selectByAll");
        return result;
    }

    /**
     * @param key
     * @param group
     * @param name
     * @param ip
     *
     * @return
     */
    long getData(long key, String group, String name, String ip) {
        String data = FileWriter.getIndexData(ip, group, name, 1);
        if (CheckUtil.checkString(data)) {
            key += Double.valueOf(data.trim());
        }
        return key;
    }

    /**
     * 集群图像展示入口页面
     *
     * @param model
     * @param select
     * @param groupsName
     *
     * @return
     */
    @RequestMapping("cluster")
    public String graphCluster(Model model, String select, String groupsName, String clusterName) {
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        model.addAttribute("select", select);
        model.addAttribute("groupsName", groupsName);
        model.addAttribute("clusterName", clusterName);
        PagingResult<MonitorClusterConfigureEntity> result = getClusterServer(groupsName, clusterName);
        String[] servers = result.getRows().get(0).getClusterHosts().split(",");
        model.addAttribute("ip", jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIdToIp + servers[0]));
        String ip = "";
        String agentIp = "";
        for (String ser : servers) {
            agentIp = jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIdToIp + ser);
            if (CheckUtil.checkString(agentIp)) {
                ip += agentIp + ",";
            }
        }
        graphController.getImagesDir(model, agentIp, null, true);
        model.addAttribute("hosts", ip.substring(0, ip.length() - 1));
        return "/monitor/cluster/cluster";
    }


    /**
     * 集群图像展示入口页面
     *
     * @param model
     * @param ips
     * @param select
     * @param groupsName
     *
     * @return
     */
    @RequestMapping("graph")
    public String graph(Model model, String ips, String select, String groupsName, String clusterName) {
        model.addAttribute("ips", ips);
        model.addAttribute("select", select);
        model.addAttribute("groupsName", groupsName);
        model.addAttribute("clusterName", clusterName);
        PagingResult<MonitorClusterConfigureEntity> result = getClusterServer(groupsName, clusterName);
        int size = result.getRows().get(0).getClusterHosts().split(",").length;
        model.addAttribute("clusterHost", size);
        String[] servers = result.getRows().get(0).getClusterHosts().split(",");
        String ip = "";
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();

        int cpus = 0;
        String cpuString;
        String agentIp = "";
        String isUp;
        String downHosts = "";
        long load1 = 0;
        long load5 = 0;
        long load15 = 0;
        long intTraffic = 0;
        long outTraffic = 0;
        long memUsed = 0;
        int up = 0;
        long memSize = 0;
        int down = 0;
        long time = System.currentTimeMillis() / 1000;
        for (String ser : servers) {
            agentIp = jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIdToIp + ser);
            if (CheckUtil.checkString(agentIp)) {
                cpuString = jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheAgentCpu.concat(agentIp));
                if (CheckUtil.checkString(cpuString)) {
                    cpus += Integer.valueOf(cpuString);
                } else {
                    cpus += 1;
                }
                // 获取集群内存总数
                memSize = getData(memSize, "memory", "system.mem.totle", agentIp);
                memUsed = getData(memUsed, "memory", "system.mem.used", agentIp);
                intTraffic = getData(intTraffic, "traffic", "system.net.bytes.rcvd.eth0", agentIp);
                outTraffic = getData(outTraffic, "traffic", "system.net.bytes.send.eth0", agentIp);
                load1 = getData(load1, "loadavg", "system.load.1", agentIp);
                load5 = getData(load5, "loadavg", "system.load.5", agentIp);
                load15 = getData(load15, "loadavg", "system.load.15", agentIp);
                isUp = jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIsUpdate + ser);
                if (CheckUtil.checkString(isUp)) {
                    if (time - Long.valueOf(isUp) > 30) {
                        down += 1;
                        downHosts += agentIp;
                    } else {
                        up += 1;
                    }
                } else {
                    down += 1;
                    downHosts += agentIp;
                }
                ip += agentIp + ",";
            } else {
                down += 1;
                downHosts += agentIp;
            }
        }
        graphController.getImagesDir(model, agentIp, null, true);
        DecimalFormat df = new DecimalFormat("#######0.0000");
        model.addAttribute("ip", jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIdToIp + servers[0]));
        model.addAttribute("hosts", ip.substring(0, ip.length() - 1));
        model.addAttribute("cpu", cpus);
        model.addAttribute("up", up);
        model.addAttribute("memSize", memSize / 1024 / 1024);
        model.addAttribute("memUsed", memUsed / 1024);
        model.addAttribute("down", down);
        model.addAttribute("downHosts", downHosts);
        model.addAttribute("send", outTraffic / 1024 / 1024);
        model.addAttribute("input", intTraffic / 1024 / 1024);
        double length = Double.valueOf(servers.length);
        model.addAttribute("load1", df.format(load1 / length));
        model.addAttribute("load5", df.format(load5 / length));
        model.addAttribute("load15", df.format(load15 / length));
        return "/monitor/cluster/graph";
    }

    /**
     * 消息通道保存
     *
     * @param entity
     * @param request
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(MonitorClusterConfigureEntity entity, HttpServletRequest request) {
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getDate(DateUtil.TIME_FORMAT));
        if (entity.getClusterId() != null) {
            clusterConfigureService.update(entity);
        } else {
            clusterConfigureService.save(entity);
        }
        indexController.logSave(request, "添加监控集群配置" + GSON.toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     * @param id
     * @param request
     *
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseVo delete(int id, HttpServletRequest request) {
        MonitorClusterConfigureEntity result = clusterConfigureService.findById(id, MonitorClusterConfigureEntity.class);
        clusterConfigureService.delete(result);
        indexController.logSave(request, "删除集群信息".concat(GSON.toJson(result)));
        return ResponseVo.responseOk(null);
    }
}