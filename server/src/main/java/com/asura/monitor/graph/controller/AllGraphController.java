package com.asura.monitor.graph.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.entity.BindImageEntity;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.graph.entity.StatusEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.util.DateUtil;
import com.asura.util.HttpUtil;
import com.asura.util.RedisUtil;
import com.asura.util.RequestClientIpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.graph.util.FileRender.getDirFiles;
import static com.asura.monitor.graph.util.FileRender.getSubDir;
import static com.asura.monitor.graph.util.FileRender.readHistory;
import static com.asura.monitor.graph.util.FileRender.separator;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 所有图像接口，包括数据上传接口，数据展示接口
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/11 07:49:11
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/graph/all/")
public class AllGraphController {

    private static Map<String, Map> hostIdMap;
    private static Map<String,String> indexMap;

    /**
     * @param ip
     * @param select
     * @param width
     * @param model
     *
     * @return
     */
    @RequestMapping("index")
    public String index(String ip, String select, String width, String type, Model model) {
        model.addAttribute("ip", ip);
        model.addAttribute("width", width);
        model.addAttribute("select", select);
        if (type != null && type.equals("bind")) {
            model.addAttribute("bind", "&type=bind");
        }
        return "/monitor/graph/all/index";
    }


    /**
     * 所有图像入口
     *
     * @param ip
     * @param select
     * @param startT
     * @param endT
     * @param type
     * @param width
     * @param model
     *
     * @return
     */
    @RequestMapping("sub")
    public String sub(String ip, String select, String startT, String endT, String type, String width, Model model) {
        ArrayList dir = new ArrayList();
        // 获取默认数据
        if (ip == null || ip.length() < 1) {
            return "/monitor/graph/all/sub";
        }
        dir = getSubDir(ip);

        // 获取所有的类型
        Map map = FileRender.getGraphName(dir, ip);
        Map tempMap = new HashMap();
        if (select != null && select.length() > 5) {
            model.addAttribute("select", select);
            String[] selectList = select.split(",");
            String[] types = new String[2];
            for (String s : selectList) {
                ArrayList<String> tempArr = new ArrayList();
                types = s.split("\\|");
                ArrayList<String> t = (ArrayList) map.get(types[0]);
                if (t==null){continue;}
                for (String tname : t) {
                    if (tname.equals(types[1].trim())) {
                        tempArr.add(tname);
                    }
                }

                if (tempMap.get(types[0]) != null) {
                    ArrayList newTemp = (ArrayList) tempMap.get(types[0]);
                    for (String ns : tempArr) {
                        newTemp.add(ns);
                    }
                    tempMap.put(types[0], newTemp);
                } else {
                    tempMap.put(types[0], tempArr);

                }
            }
        }

        model.addAttribute("names", dir);
        if (tempMap.size() > 0) {
            model.addAttribute("types", tempMap);
        } else {
            model.addAttribute("types", map);
        }
        if (width != null && width.length() > 0) {
            model.addAttribute("width", width);
        }
        model.addAttribute("startT", startT);
        model.addAttribute("endT", endT);
        model.addAttribute("ip", ip);
        if (type != null && type.equals("bind")) {
            return "/monitor/graph/all/subbind";
        }
        if (type == null) {
            return "/monitor/graph/all/sub";
        } else {
            return "/monitor/graph/all/selectSub";
        }
    }

    /**
     * 获取图像名称
     *
     * @param ip
     * @param model
     *
     * @return
     */
    @RequestMapping("selectImg")
    public String selectImg(String ip, Model model) {
        ArrayList dir = getSubDir(ip);
        // 获取所有的类型
        Map map = FileRender.getGraphName(dir, ip);
        model.addAttribute("names", dir);
        model.addAttribute("types", map);
        return "/monitor/graph/all/selectImg";
    }

    /**
     * 数据写入
     *
     * @param entity
     * @param request
     */
    public void pushData(PushEntity entity, HttpServletRequest request) {
        String ipAddr;
        String name = FileRender.replace(entity.getName());
        String groups = FileRender.replace(entity.getGroups());
        String value = entity.getValue();
        // 获取客户端IP
        if (entity.getIp() == null || entity.getIp().length() < 10) {
            ipAddr = RequestClientIpUtil.getIpAddr(request);
        } else {
            ipAddr = FileRender.replace(entity.getIp());
        }

        // 只将数据写入到文件
        if (name != null && value != null && value.length() > 0) {
            FileWriter.writeHistory(groups, ipAddr, name, value);
        }

        entity.setTime(DateUtil.getTimeStamp() + "");
        entity.setServer(request.getLocalAddr());
    }

    /**
     * 监控数据上报接口
     * 单个上传,打包上传
     * 方法:post,get
     *
     * @return
     */
    @RequestMapping("push")
    @ResponseBody
    public ResponseVo push(PushEntity entity, String lentity, HttpServletRequest request) {

        if (lentity != null) {
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(lentity, type);
            for (PushEntity entity1 : list) {
                if (entity1 == null) {
                    continue;
                }
                pushData(entity1, request);
            }

        } else {
            pushData(entity, request);
        }
        entity.setServer(request.getLocalAddr());
        return ResponseVo.responseOk(entity);
    }

    /**
     * 服务器状态展示
     *
     * @return
     */
    @RequestMapping("status")
    public String status(String ip, Model model) {
        model.addAttribute("ip", ip);
        return "/monitor/graph/all/status";
    }

    /**
     * 获取服务器状态展示的数据
     *
     * @param ip
     *
     * @return
     */
    @RequestMapping("statusData")
    @ResponseBody
    public String statusData(String ip) throws IOException {
        Gson gson = new Gson();
        ArrayList<String> dirs = FileRender.getSubDir(ip);
        System.out.println(gson.toJson(dirs));
        ArrayList<StatusEntity> statusEntities = new ArrayList<>();

        //  大目录设置
        StatusEntity ipStatus = new StatusEntity();
        ipStatus.setName(ip);
        ipStatus.setKey(0);
        statusEntities.add(ipStatus);
        int key = 1;
        for (String dir : dirs) {
            StatusEntity statusEntity = new StatusEntity();
            statusEntity.setName(dir);
            statusEntity.setKey(key);
            ArrayList<String> dirList = new ArrayList<>();
            statusEntity.setDirs(dirList);
            key += 1;
            statusEntities.add(statusEntity);
        }

        ArrayList arrayList = new ArrayList();
        for (StatusEntity entity : statusEntities) {
            arrayList.add(entity);
            key = 1000;
            // 获取所有的类型
            String statusDir = FileRender.getStatusDir(ip, entity.getName());
            File[] fileList = getDirFiles(statusDir);
            if (fileList == null) {
                continue;
            }
            for (File f : fileList) {
                StatusEntity statusEntity = new StatusEntity();
                statusEntity.setKey(Integer.valueOf(entity.getKey() + "" + key));
                String last = "0";
                if (f.isDirectory()) {
                    File[] subFile = getDirFiles(statusDir + f.getName());
                    for (File subF : subFile) {
                        statusEntity.setName(f.getName().replace("SLASH", "/") + " " + subF.getName());
                        System.out.println(statusDir + separator + f.getName() + separator + subF.getName());
                        last = FileRender.readLastLine(statusDir + separator + f.getName() + separator + subF.getName());
                        String[] result = last.split(" ");
                        statusEntity.setTitle(result[1]);
                        statusEntity.setBoss(entity.getKey());
                        arrayList.add(statusEntity);
                        continue;
                    }
                    break;
                } else {
                    statusEntity.setName((f.getName().replace("SLASH", "/")));
                    last = FileRender.readLastLine(statusDir + separator + f.getName());
                }
                if (last == null) {
                    continue;
                }
                String[] result = last.split(" ");
                statusEntity.setTitle(result[1]);
                statusEntity.setBoss(entity.getKey());
                key += 1;
                arrayList.add(statusEntity);

            }

        }
        return new Gson().toJson(arrayList);
    }

    /**
     * 多图绑定
     *
     * @return
     */
    @RequestMapping("bind")
    public String bind() {
        return "/monitor/graph/all/subbind";
    }

    /**
     * 实时获取数据
     */
    @RequestMapping(value = "realtime", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getRealtimeData(String server, String groups, String  name) throws Exception {
        String serverId = "";
        String port = "";
        RedisUtil redisUtil = new RedisUtil();
        Gson gson = new Gson();
        if (hostIdMap == null) {
            hostIdMap = new HashMap();
        }
        if (indexMap==null){
            indexMap = new HashMap<>();
        }
        if (hostIdMap.containsKey(server)) {
            serverId = (String) hostIdMap.get(server).get("serverId");
            port = (String) hostIdMap.get(server).get("port");
        } else {
            serverId = redisUtil.get(MonitorCacheConfig.hostsIdKey + server);
            String portData = redisUtil.get(MonitorCacheConfig.cacheAgentServerInfo + serverId);
            if (portData.length() > 0) {
                Map serverMap = gson.fromJson(portData, HashMap.class);
                Map map = new HashMap();
                map.put("serverId", serverId);
                map.put("port", serverMap.get("port"));
                hostIdMap.put(server, map);
            }
        }
        String  key = groups + "." + name;
        String scriptId;
        if (!indexMap.containsKey(key)) {
            scriptId = redisUtil.get(MonitorCacheConfig.cacheIndexScript + key);
            indexMap.put(key, scriptId);
        }else{
            scriptId = indexMap.get(key);
        }
        String url = "http://" + server + ":" + port + "/api/realtime?scriptId=" + scriptId;
        System.out.println(url);
        return HttpUtil.sendPost(url, "scriptId=12");
    }

    /**
     * @param ip
     *
     * @return
     */
    @RequestMapping(value = "bindData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String bindData(String ip) {
        Gson gson = new Gson();
        ArrayList dir = getSubDir(ip);
        String startT = DateUtil.getDay();
        String endT = DateUtil.getDay();
        Map<String, ArrayList> maps = new HashMap<>();
        ArrayList timeList = new ArrayList();
//        ArrayList result =  readHistory(ip,type,name,startT,endT,totle);
        // 获取所有的类型
        boolean isTime = false;
        Map<String, ArrayList> map = FileRender.getGraphName(dir, ip);
        ArrayList<BindImageEntity> list = new ArrayList<>();
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {
            ArrayList<String> values = entry.getValue();
            for (String name : values) {
                BindImageEntity bindImageEntity = new BindImageEntity();
                bindImageEntity.setName(name.replace("---", " ").replace("SLASH", "/"));
                bindImageEntity.setType("spline");
                bindImageEntity.setUnit("");
                ArrayList<ArrayList> data = readHistory(ip, entry.getKey(), name, startT, endT, null);
                ArrayList<Double> bindData = new ArrayList<>();
                for (ArrayList<Double> d : data) {
                    bindData.add(d.get(1));
                    if (!isTime) {
                        timeList.add(d.get(0));
                    }
                }
                isTime = true;
                bindImageEntity.setData(bindData);
                bindImageEntity.setValueDecimals(2);
                list.add(bindImageEntity);
            }
        }
        maps.put("data", list);
        maps.put("xdata", timeList);
        return new Gson().toJson(maps);
    }
}