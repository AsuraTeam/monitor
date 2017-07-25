package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.cluster.controller.MonitorClusterController;
import com.asura.monitor.cluster.entity.MonitorClusterConfigureEntity;
import com.asura.monitor.cluster.service.MonitorClusterConfigureService;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.util.FileRender;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.asura.monitor.graph.util.FileRender.readHistory;
import static com.asura.monitor.graph.util.FileWriter.dataDir;
import static com.asura.monitor.graph.util.FileWriter.separator;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 公用的数据
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/05 18:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/graph/")
public class CommentController {

    @Autowired
    private CmdbResourceGroupsService groupsService;

    @Autowired
    private MonitorClusterController clusterController;

    private Gson gson = new Gson();

    public static Model getGroups(Model model, CmdbResourceGroupsService groupsService) {
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000, 1);
        PagingResult<CmdbResourceGroupsEntity> groups = groupsService.findAll(searchMap, pageBounds);
        model.addAttribute("groups", groups.getRows());
        return model;
    }

    public static SearchMap getSearchMap(String groups, String ip, String name, HttpServletRequest request) {
        SearchMap searchMap = new SearchMap();
        if (CheckUtil.checkString(groups)) {
            searchMap.put("groupsName", groups);
        }

        // 模糊匹配
        String search = request.getParameter("search[value]");
        if (CheckUtil.checkString(search)) {
            searchMap.put("search", search);
        }
        // 匹配名称
        if (CheckUtil.checkString(name)) {
            searchMap.put("name", name);
        }

        // 匹配Ip
        if (ip != null && ip.length() > 10) {
            searchMap.put("ipAddress", ip);
        }
        return searchMap;
    }


    /**
     * @param ip
     * @param name
     * @param startT
     * @param endT
     * @param totle
     * @param type
     * @param dayNumber
     *
     * @return
     */
    @RequestMapping(value = "historyData", produces = {"application/json;charset=utf8"})
    @ResponseBody
    public String historyData(String ip, String name, String startT, String endT, String totle, String type, String dayNumber, String last) {
        if (startT == null || startT.length() < 6) {
            startT = DateUtil.getDay();
            endT = DateUtil.getDay();
        }
        if (ip == null || name == null) {
            return "参数不完整";
        }
        ArrayList result = new ArrayList();
        if (dayNumber == null || dayNumber.length() < 1) {
                result = readHistory(ip, type, name, startT, endT, totle, false, last);
        } else {
            DateUtil dateUtil = new DateUtil();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dataDir)
                    .append(separator)
                    .append("graph")
                    .append(separator)
                    .append(ip)
                    .append(separator)
                    .append(type)
                    .append(separator)
                    .append(dateUtil.getDate("yyyy"))
                    .append(separator)
                    .append("day")
                    .append(dayNumber)
                    .append(separator)
                    .append(name);
            String dir = stringBuilder.toString();

                result = FileRender.readTxtFile(dir, result, totle, false);

        }
        return gson.toJson(result);
    }

    /**
     * 集群状态数据获取
     * @param groupsName
     * @param clusterName
     * @param type
     * @param name
     * @return
     */
    @RequestMapping(value = "historyClusterData", produces = {"application/json;charset=utf8"})
    @ResponseBody
    public String getClusterData(String groupsName, String clusterName, String type, String name, String resultType){
        DecimalFormat df = new DecimalFormat("#######0.00");
        String server = "";
        PagingResult<MonitorClusterConfigureEntity> result = clusterController.getClusterServer(groupsName, clusterName);
        for (MonitorClusterConfigureEntity entity: result.getRows()){
                server = entity.getClusterHosts();
        }
        RedisUtil redisUtil = new RedisUtil();
        Jedis jedis = redisUtil.getJedis();
        String ip;
        List<ArrayList<ArrayList<Object>>> list = new ArrayList<>();
        if (CheckUtil.checkString(server)){
            String startT = DateUtil.getDay();
            String endT = DateUtil.getDay();
            String[] servers = server.split(",");
            for (String ser: servers) {
                ip = jedis.get(RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIdToIp + ser);
                list.add(readHistory(ip, type, name, startT, endT, null,false, null));
            }
        }
        ArrayList<Object> temp = new ArrayList<>();
        Gson gson = new Gson();
        ArrayList data ;
        Double sum ;
        ArrayList sumList = new ArrayList();
        for (int j=0; j<list.get(0).size(); j++) {
            sum = 0.0;
            data = new ArrayList();
            data.add(0);
            data.add(1);
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).size() > j) {
                    temp = list.get(i).get(j);
                    sum += Double.valueOf(temp.get(1).toString());
                }else{
                    sum += 0.0;
                }
            }
            data.set(0, Long.valueOf(temp.get(0).toString()));
            if (CheckUtil.checkString(resultType)){
                data.set(1, Double.valueOf(df.format(sum / list.size())));
            }else {
                data.set(1, Double.valueOf(df.format(sum)));
            }
            sumList.add(data);
        }
        return gson.toJson(sumList);
    }

    /**
     * 页面
     *
     * @returnh
     */
    @RequestMapping("cpu/list")
    public String list(Model model) {
        CommentController.getGroups(model, groupsService);
        return "monitor/graph/cpu/list";
    }

    /**
     * 页面
     *
     * @return
     */
    @RequestMapping("memory/list")
    public String memlist(Model model) {
        CommentController.getGroups(model, groupsService);
        return "monitor/graph/memory/list";
    }

    /**
     * 页面
     *
     * @return
     */
    @RequestMapping("swap/list")
    public String swaplist(Model model) {
        CommentController.getGroups(model, groupsService);
        return "monitor/graph/swap/list";
    }

    /**
     * 页面
     *
     * @returnh
     */
    @RequestMapping("loadavg/list")
    public String loadavglist(Model model) {
        CommentController.getGroups(model, groupsService);
        return "monitor/graph/loadavg/list";
    }


    /**
     * 页面
     *
     * @return
     */
    @RequestMapping("disk/list")
    public String diskUse(Model model) {
       CommentController.getGroups(model, groupsService);
        return "monitor/graph/disk/list";
    }

    /**
     * 页面
     *
     * @return
     */
    @RequestMapping("traffic/list")
    public String trafficUse(Model model) {
        CommentController.getGroups(model, groupsService);
        return "monitor/graph/traffic/list";
    }


    /**
     * 系统负载
     *
     * @param ip
     * @param model
     *
     * @return
     */
    @RequestMapping("uptime")
    public String uptime(String ip, Model model) {
        model.addAttribute("ip", ip);
        return "monitor/graph/loadavg/uptimeLine";
    }
}
