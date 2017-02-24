package com.asura.monitor.monitor.controller;

import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MessagesEntity;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.monitor.entity.CheckCountEntity;
import com.asura.monitor.monitor.entity.CmdbMonitorInformationEntity;
import com.asura.monitor.monitor.entity.CmdbMonitorMessagesEntity;
import com.asura.monitor.monitor.service.MonitorInformationService;
import com.asura.monitor.monitor.service.MonitorMessagesService;
import com.asura.util.DateUtil;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.graph.util.FileRender.readLastLine;
import static com.asura.util.RedisUtil.app;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  全局监控图
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/26 17:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/global/")
public class MonitorGlobaltController {

    @Autowired
    private MonitorMessagesService messagesService;

    @Autowired
    private MonitorInformationService monitorService;

    private static final Gson gson = new Gson();

    private static RedisUtil redisUtil = new RedisUtil();

    private static Map<String, String> ID_TO_HOST;
    // 缓存每个组里的主机ID
    private static Map<String, String> GROUP_HOSTS;
    private static long CACHE_HOSTS_TIME ;
    private static final String separator = System.getProperty("file.separator");
    private static final String tempDir = System.getProperty("java.io.tmpdir") + separator + "monitor" + separator;

    /**
     * 监控入口
     *
     * @param searchMap
     *
     * @return
     */
    public ArrayList<CheckCountEntity> getIndexData(SearchMap searchMap) {

        //List<CmdbMonitorMessagesEntity> result = messagesService.getMessageList(searchMap,"selectGroups");
        //model.addAttribute("groups",result);

        List<CmdbMonitorMessagesEntity> groupsInfo = messagesService.getMessageList(searchMap, "selectGroupsMonitorInfo");
        ArrayList<String> groups = new ArrayList();
        for (CmdbMonitorMessagesEntity g : groupsInfo) {
            if (!groups.contains(g.getGroups())) {
                groups.add(g.getGroups());
            }
        }

        ArrayList<CheckCountEntity> check = new ArrayList<>();
        for (String name : groups) {
            CheckCountEntity temp = new CheckCountEntity();
            temp.setName(name);
            for (CmdbMonitorMessagesEntity c : groupsInfo) {
                if (name.equals(c.getGroups())) {
                    if (c.getStatus().equals("正常")) {
                        temp.setOk(c.getCnt());
                    }
                    if (c.getStatus().equals("危险")) {
                        temp.setDanger(c.getCnt());
                    }

                    if (c.getStatus().equals("未知")) {
                        temp.setUnknown(c.getCnt());
                    }
                    if (c.getStatus().equals("警告")) {
                        temp.setWarning(c.getCnt());
                    }
                    if (!check.contains(temp)) {
                        temp.setId(c.getId());
                        check.add(temp);
                    }

                }
            }
        }
        return check;
    }

    /**
     * 多个页面自动循环播放
     *
     * @return
     */
    @RequestMapping("auto")
    public String auto() {
        return "/monitor/global/autoload";
    }

    /**
     * @param model
     *
     * @return
     */
    @RequestMapping("index")
    public String content(Model model) {
//        SearchMap searchMap = new SearchMap();
//        model.addAttribute("check",getIndexData(searchMap));
        return "/monitor/global/index";
    }

    /**
     * 监控系统数据默认入口
     *
     * @return
     */
    @RequestMapping("Index")
    public String newIndex() {

        return "/monitor/global/newIndex";
    }

    /**
     * 获取指定业务组的告警信息
     *
     * @param draw
     *
     * @return
     */
    @RequestMapping(value = "selectGroupsMonitorInfo", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String selectGroupsMonitorInfo(int draw, String groups) {
        SearchMap searchMap = new SearchMap();
        searchMap.put("groups", groups);

        List<CmdbMonitorInformationEntity> result;
        result = monitorService.selectGroupsMonitorInfo(searchMap, "selectGroupsMonitorInfo");
        if (result.size() < 1) {
            result = monitorService.selectGroupsMonitorInfo(searchMap, "selectGroupsMonitorInfoNoComm");
        }

        Map map = new HashMap();
        map.put("data", result);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", draw);
        return gson.toJson(map);

    }

    /**
     * 获取脚本名称
     *
     * @param jedis
     * @param scriptId
     *
     * @return
     */
    public String getScriptName(Jedis jedis, String scriptId) {
        String name = "";
        String result = jedis.get(app + "_" + MonitorCacheConfig.cacheScriptKey + scriptId);
        if (result != null) {
            MonitorScriptsEntity monitorScriptsEntity = gson.fromJson(result, MonitorScriptsEntity.class);
            name = monitorScriptsEntity.getMonitorName();
        }
        return name;
    }

    /**
     * @param strings
     *
     * @return
     */
    String getMessages(String[] strings) {
        int count = 0;
        String temp = "";
        for (String s : strings) {
            if (count > 0) {
                temp += " " + s;
            }
            count += 1;
        }
        return temp;
    }

    /**
     * @param status
     * @param length
     * @param start
     *
     * @return
     */
    Map getAgentMessages(String status, int length, int start) {
        Map<String, String> map = new HashMap<>();
        String messages = "监控Agent 5分钟未响应...";
        if (status.equals("2")) {
            map = getAgentOkMap(MonitorCacheConfig.cacheAgentUnreachable);
            messages = "监控Agent 5分钟未响应...";
        }
        if (status.equals("1")) {
            map = getAgentOkMap(MonitorCacheConfig.cacheAgentIsOk);
            messages = "监控Agent响应正常...";

        }
        ArrayList<MessagesEntity> messagesEntities = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 分页检查
            if (!PageResponse.checkPaging(start, length, count)) {
                count += 1;
                continue;
            }
            count += 1;
            MessagesEntity messagesEntity = new MessagesEntity();
            messagesEntity.setGroupsName("监控Agent状态");
            messagesEntity.setMessages(messages);
            messagesEntity.setStatus(status);
            messagesEntity.setName("agent");
            String ip = getMessagesIp(entry.getKey());
            messagesEntity.setServer(ip);
            try {
                messagesEntity.setTime(entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
                messagesEntity.setTime(DateUtil.getTimeStamp() + "");
            }
            messagesEntities.add(messagesEntity);
        }
        Map maps = getMessagesMap(messagesEntities, map);
        return maps;
    }


    /**
     * @param hostId
     *
     * @return
     */
    String getMessagesIp(String hostId) {
        String ip = "";
        if (ID_TO_HOST == null) {
            ID_TO_HOST = new HashMap<>();
        }

        if (ID_TO_HOST.containsKey(hostId)) {
            ip = ID_TO_HOST.get(hostId);
        } else {
            ip = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + hostId);
            ID_TO_HOST.put(hostId, ip);
        }
        if (ip == null || ip.length() < 5) {
            ip = hostId.replace("999", ".");
        }
        return ip;
    }

    /**
     * @param messagesEntities
     * @param dataMap
     *
     * @return
     */
    Map getMessagesMap(ArrayList<MessagesEntity> messagesEntities, Map dataMap) {
        Map map = new HashMap();
        int totle = 0;
        if (dataMap.size()==0){
            totle = messagesEntities.size();
        }else {
            totle = dataMap.size();
        }
        map.put("data", messagesEntities);
        map.put("recordsTotal", totle);
        map.put("recordsFiltered", totle);
        return map;
    }

    /**
     * 获取监控异常的数据
     *
     * @param groupsId
     * @param status
     * @param groupsName
     * @param start
     * @param length
     *
     * @return
     */
    @RequestMapping(value = "messages", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String messages(String groupsId, String status, String groupsName, int start, int length, String server) throws Exception {
        if (groupsId.equals("999999999")) {
            Map map = getAgentMessages(status, length, start);
            return gson.toJson(map);
        }

        Jedis jedis = redisUtil.getJedis();
//        List<String> hosts = getHosts(groupsId, jedis);
//        List<Map<String, Map<String, String>>> hostMap = getHostStatus(hosts, jedis);
        String datas = FileRender.readLastLine(tempDir + "groupsMap_" + groupsId+"_"+status);
        Map<String, String> dataMap = gson.fromJson(datas, HashMap.class);

        String[] data;
        String hostId;
        String path;
        status = FileRender.replace(status);
        ArrayList<MessagesEntity> messagesEntities = new ArrayList<>();
        String messages;
        String[] messagesData;
        MessagesEntity messagesEntity;
        int count = 0;
        // 获取到该组的hostId和脚本Id，拼接出来日志路径
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            // 分页检查
            if (!PageResponse.checkPaging(start, length, count)) {
                count += 1;
                if(server==null||server.length()<1) {
                    continue;
                }
            }
            count += 1;
            data = entry.getKey().split("_");
            try {
                path = FileRender.replace(data[1] + "_" + status + "_" + data[3] + "_" + data[4]);
            } catch (Exception e) {
                continue;
            }

            hostId = FileRender.replace(data[2]);
            if (server != null && server.length() > 0 && !hostId.equals(server)){
                continue;
            }
            String file = FileWriter.getMonitorFile(hostId, path);
            System.out.println(file);
            messagesEntity = new MessagesEntity();
            messages = readLastLine(file);
            if (messages == null) {
                continue;
            }
            messagesData = messages.split(" ");
            messagesEntity.setGroupsName(groupsName);
            messagesEntity.setMessages(getMessages(messagesData));
            messagesEntity.setStatus(status);
            messagesEntity.setName(data[4]);
            messagesEntity.setGroups(data[3]);
            messagesEntity.setTime(messagesData[0]);
            String ip = getMessagesIp(hostId);
            messagesEntity.setServer(ip);
            messagesEntity.setServerId(hostId);
            messagesEntity.setCommand(getScriptName(jedis, data[1]));
            messagesEntities.add(messagesEntity);

        }
        jedis.close();
        if (server!=null&&server.length()>0){
            dataMap = new HashMap<>(messagesEntities.size());
        }
        Map map = getMessagesMap(messagesEntities, dataMap);
        return gson.toJson(map);
    }



    /**
     *
     * @param temp
     * @param oldMap
     * @return
     */
    CheckCountEntity getTempData(CheckCountEntity temp,Map<String,String> oldMap, Map<String,String> okMap,String type) {

    if(oldMap==null)
    {
        oldMap = new HashMap<>();
    }

    for(Map.Entry<String, String> map :okMap.entrySet())
    {
        oldMap.put(map.getKey(), map.getValue());
    }
    switch (type)
    {
        case "ok":
            temp.setOkMap(oldMap);
            break;
        case "warning":
            temp.setWarningMap(oldMap);
            break;
        case "faild":
            temp.setFaildMap(oldMap);
            break;
        case "unknown":
            temp.setUnknownMap(oldMap);
            break;
    }
        return temp;
}

    /**
     *
     * @param temp
     */
    CheckCountEntity setIndexDataMap(CheckCountEntity temp, String type, Map<String,String> okMap){
        if (okMap==null){
            okMap = new HashMap();
        }
        switch (type){
            case "ok":
                temp.setOk(temp.getOk() + okMap.size());
                temp = getTempData(temp,temp.getOkMap(),okMap,"ok");
                break;
            case "danger":
                temp.setDanger(temp.getDanger() + okMap.size());
                temp = getTempData(temp,temp.getFaildMap(),okMap,"faild");
                break;
            case "warning":
                temp.setWarning(temp.getWarning() + okMap.size());
                temp = getTempData(temp,temp.getWarningMap(),okMap,"warning");
                break;
            case "unknown":
                temp.setUnknown(temp.getUnknown() + okMap.size());
                temp = getTempData(temp,temp.getUnknownMap(),okMap,"unknown");
                break;
        }
        return temp;
    }

    /**
     *
     * @param exclude
     * @return
     */
    public ArrayList getExclude(String exclude){
        // 获取排除的数据
        if(exclude.length()>3) {
            ArrayList arr = new ArrayList();
            String[] excludeS = exclude.split(",");
            for(String s:excludeS){
                if(s.length()<1){continue;}
                if(!arr.contains(s)){
                    arr.add(s);
                }
            }
            return arr;
        }
        return new ArrayList();
    }

    /**
     * 获取组的数据
     * @return
     */
    static Map<String, String> getGroupsMap(){
        String groups = redisUtil.get(MonitorCacheConfig.cacheGroupName);
        Map<String, String> groupMap;
        if(groups.length()>0) {
            groupMap = gson.fromJson(groups, HashMap.class);
        }else {
            groupMap = new HashMap<>();
        }
        return groupMap;
    }



    /**
     * MonitorCacheConfig.cacheAgentUnreachable
     * MonitorCacheConfig.cacheAgentIsOk
     * @return
     */
    public static Map<String,String> getAgentOkMap(String key){
        Map<String,String> okMap = new HashMap<>();
        Map<String,String> groupMap = getGroupsMap();
        Jedis jedis = redisUtil.getJedis();
        for(Map.Entry<String,String> entry: groupMap.entrySet()) {
            String isOk = jedis.get(app+"_"+key + "_" + entry.getKey());
            if (isOk != null && isOk.length() > 0) {
                Map<String,String> map = gson.fromJson(isOk, HashMap.class);
                for(Map.Entry<String,String> ok: map.entrySet()) {
                    okMap.put(ok.getKey(),ok.getValue());
                }
            }
        }
        jedis.close();
        return  okMap;
    }

    /**
     * 获取每个组里的主机
     * @param groupsId
     * @return
     */
    List<String> getHosts(String groupsId, Jedis jedis){
        List<String> hosts = new ArrayList<>();
        String hostsData = "";
        if (GROUP_HOSTS.containsKey(groupsId)){
            hostsData = GROUP_HOSTS.get(groupsId);
        }else {
            hostsData = jedis.get(app+"_"+MonitorCacheConfig.cacheGroupsHosts + groupsId);
            GROUP_HOSTS.put(groupsId, hostsData);
        }
        if (hostsData != null && hostsData.length()>0){
            hosts = gson.fromJson(hostsData, ArrayList.class);
        }
        return hosts;
    }

    /**
     * 获取agent不可达的数据
     * @return
     */
    CheckCountEntity getAgentUnreachable(){
        Map<String,String> map = getAgentOkMap(MonitorCacheConfig.cacheAgentUnreachable);
        Map<String,String> okMap = getAgentOkMap(MonitorCacheConfig.cacheAgentIsOk);
        CheckCountEntity temp = new CheckCountEntity();
            temp.setName("监控Agent状态");
            temp.setDanger(map.size());
            temp.setOk(okMap.size());
            temp.setUnknown(0);
            temp.setWarning(0);
            temp.setId(999999999);
        return  temp;
    }

    /**
     * 获取每个组里的所有host的数据状态
     * @param hosts
     * @return
     */
    List<Map<String, Map<String,String>>> getHostStatus(List<String> hosts, Jedis jedis){
        String[] key = new String[hosts.size()];
        int count = 0;
        for (String  host:hosts){
            key[count] = app + "_" + MonitorCacheConfig.cacheHostStatusMap+host;
            count += 1;
        }
        if (key.length < 1){
            return new ArrayList<>();
        }
        List<Map<String, Map<String,String>>> returnData = new ArrayList<>();
        List<String> hostStatus = jedis.mget(key);
        for (String result: hostStatus) {
            Map<String, Map<String,String>> data = new HashMap<>();
            if (result != null && result.length() > 0) {
                Map<String, String> redisData = gson.fromJson(result, HashMap.class);
                for (Map.Entry<String,String> map: redisData.entrySet()) {

                    data.put(map.getKey(), gson.fromJson(map.getValue(),HashMap.class));
                }
            }
            returnData.add(data);
        }
        jedis.close();
        return returnData;
    }

    /**
     *
     * @param faildCheck
     * @param data
     * @return
     */
    ArrayList<CheckCountEntity> getCheckCount(ArrayList<CheckCountEntity>  faildCheck,  ArrayList<CheckCountEntity> data ){
        for (CheckCountEntity c:faildCheck){
            if (!data.contains(c)) {

                if (c.getOk()>0) {
                    FileWriter.writeFile(tempDir + "groupsMap_" + c.getId() +"_1", gson.toJson(c.getOkMap()), false);
                }
                if(c.getUnknown()>0){
                    FileWriter.writeFile(tempDir + "groupsMap_" + c.getId() + "_4", gson.toJson(c.getUnknownMap()), false);
                }
                if(c.getWarning()>0){
                    FileWriter.writeFile(tempDir + "groupsMap_" + c.getId() +"_3", gson.toJson(c.getWarningMap()), false);
                }
                if(c.getDanger()>0){
                    FileWriter.writeFile(tempDir + "groupsMap_" + c.getId() +"_2", gson.toJson(c.getFaildMap()), false);
                }
                c.setUnknownMap(null);
                c.setWarningMap(null);
                c.setFaildMap(null);
                c.setOkMap(null);
                data.add(c);
            }
        }
        return data;
    }
    /**
     * 从redis获取实时监控状态
     */
    @RequestMapping(value="IndexData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String IndexData(String exclude){
        if (GROUP_HOSTS==null){
            CACHE_HOSTS_TIME = System.currentTimeMillis()/1000;
            GROUP_HOSTS = new HashMap<>();
        }
        if (System.currentTimeMillis()/1000 -  CACHE_HOSTS_TIME > 600 ){
            GROUP_HOSTS = new HashMap<>();
            CACHE_HOSTS_TIME = System.currentTimeMillis()/1000;
        }
        ArrayList excludeGroups = getExclude(exclude);
        Map<String,String> groupMap = getGroupsMap();

        // 组名数据
        ArrayList<String> groupsArray = new ArrayList();
        for(Map.Entry<String,String> entry: groupMap.entrySet()){
            if(!excludeGroups.contains(entry.getValue())) {
                groupsArray.add(entry.getValue());
            }
        }

        Jedis jedis = redisUtil.getJedis();
        // 获取一个redis链接
        ArrayList<CheckCountEntity> check = new ArrayList<>();
        ArrayList<CheckCountEntity> faildCheck = new ArrayList<>();
        for(String  name:groupsArray) {
            CheckCountEntity temp = new CheckCountEntity();
            temp.setName(name);

            for (Map.Entry<String, String> entry : groupMap.entrySet()) {
                String groupsId = entry.getKey();
                if (entry.getValue().equals(name)) {
                    List<String> hosts = getHosts(groupsId, jedis);
                    if (hosts.size()==0) { continue; }
                    List<Map<String,Map<String,String>>> hostMap = getHostStatus(hosts, jedis);
                    for (Map<String,Map<String,String>> map :hostMap) {
                        if (map == null) {continue;}
                        temp = setIndexDataMap(temp, "danger", map.get("faild"));
                        if (map.get("faild")!=null && map.get("faild").size()>0){
                            for (Map.Entry<String, String> faild: map.get("faild").entrySet()){
                                if(System.currentTimeMillis()/1000 - Integer.valueOf(faild.getValue()) > 600){
                                    String id = faild.getKey().split("_")[2];
                                    String key = RedisUtil.app + "_" + MonitorCacheConfig.cacheHostStatusMap+id;
                                    jedis.del(key);
                                }
                            }
                        }
                        temp = setIndexDataMap(temp, "ok", map.get("ok"));
                        temp = setIndexDataMap(temp, "warning", map.get("warning"));
                        temp = setIndexDataMap(temp, "unknown", map.get("unknown"));
                        temp.setId(Integer.valueOf(entry.getKey()));
                    }
                }

                 if(temp.getDanger() > 0 || temp.getUnknown() > 0 || temp.getWarning() > 0 ){
                     if(!faildCheck.contains(temp)) {
                         faildCheck.add(temp);
                     }
                 }else {
                     if (!check.contains(temp)) {
                         check.add(temp);
                     }
                 }
            }
        }

        ArrayList<CheckCountEntity> data = new ArrayList<>();

        // agent不可达的数据
        CheckCountEntity temp = getAgentUnreachable();
        if (temp.getName() !=null){
          data.add(temp);
        }
        data = getCheckCount(faildCheck, data);
        data = getCheckCount(check, data);
        try {
            jedis.close();
        }catch (Exception e){
        }
        // 添加agent的数据
        Map map = new HashMap();
        map.put("data", data);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", 1);
//        clearExpireGroupData();
        return gson.toJson(map);
    }

    /**
     * 获取监控信息
     * @param draw
     * @return
     */
    @RequestMapping(value="indexData",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  String indexData(int draw, String exclude){
        SearchMap searchMap = new SearchMap();

        // 获取排除的数据
        if(exclude.length()>3) {
            ArrayList arr = new ArrayList();
            String[] excludeS = exclude.split(",");
            for(String s:excludeS){
                if(s.length()<1){continue;}
                if(!arr.contains(s)){
                    arr.add(s);
                }
            }
            searchMap.put("exclude", arr);
        }

       ArrayList<CheckCountEntity> result = getIndexData(searchMap);
        Map map = new HashMap();
        map.put("data", result);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", draw);
        return gson.toJson(map);
    }


    /**
     * 清除无效的服务器
     * @param server
     * @return
     */
    @RequestMapping("clearServer")
    @ResponseBody
    public String clearServer(String  server){
        String id = redisUtil.get(MonitorCacheConfig.hostsIdKey+server);
        if (id!=null){
            redisUtil.del(MonitorCacheConfig.cacheHostIsUpdate+id);
            redisUtil.del(MonitorCacheConfig.hostsIdKey+server);
        }
        return "ok";
    }
}
