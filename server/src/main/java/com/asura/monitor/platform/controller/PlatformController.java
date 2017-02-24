package com.asura.monitor.platform.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.entity.SysinfoDataEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.platform.entity.MonitorPlatformServerEntity;
import com.asura.monitor.platform.entity.ProcessEntity;
import com.asura.monitor.platform.service.MonitorPlatformServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.graph.util.FileWriter.getItemData;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * 监控平台数据
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/monitor/platform/")
public class PlatformController {

    private static long toDbTime;

    @Autowired
    private MonitorPlatformServerService serverService;

    /**
     * @return
     */
    @RequestMapping("server/list")
    public String list() {
        return "/monitor/platform/server/list";
    }

    /**
     * @return
     */
    @RequestMapping("server/graph")
    public String graph(Model model, String ip, String hostname) {
        model.addAttribute("ip", ip);
        model.addAttribute("hostname", hostname);
        return "/monitor/platform/server/graph";
    }

    /**
     * @return
     */
    ArrayList getServerList() {
        File file = new File(FileWriter.getSysInfoDir());
        ArrayList arrayList = new ArrayList();
        File[] list = file.listFiles();
        if (list == null) {
            return arrayList;
        }
        for (File file1 : list) {
            arrayList.add(file1.getName());
        }

        Gson gson = new Gson();
        System.out.println(gson.toJson(arrayList));
        return arrayList;
    }

    /**
     * @param ip
     * @param dir
     *
     * @return
     */
    MonitorPlatformServerEntity getServerEntity(String ip, String dir) {
        Gson gson = new Gson();
        ArrayList infos = new ArrayList();
        MonitorPlatformServerEntity serverEntity = new MonitorPlatformServerEntity();
        serverEntity.setIp(ip);
        String data = FileRender.readFile(dir + ip);
        try {
            Type type = new TypeToken<ArrayList<SysinfoDataEntity>>() {
            }.getType();
            List<SysinfoDataEntity> sysinfoDataEntities = new Gson().fromJson(data, type);
            for (SysinfoDataEntity entity : sysinfoDataEntities) {
                if (entity.getName().equals("time")) {
                    serverEntity.setLastModifyTime(entity.getResult().toString());
                }
                if (entity.getName().equals("hostname")) {
                    serverEntity.setHostname(entity.getResult().toString());
                }
                infos.add(entity.getName());
            }
            serverEntity.setShowKey(gson.toJson(infos));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverEntity;
    }

    /**
     * 将server信息写入到数据库
     */
    void writeServerToDb(boolean update) {
        Gson gson = new Gson();
        ArrayList<String> ips = getServerList();
        ArrayList list = new ArrayList();
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000000, 1);
        PagingResult<MonitorPlatformServerEntity> entityPagingResult = serverService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorPlatformServerEntity entity : entityPagingResult.getRows()) {
            list.add(entity.getIp());
        }
        String dir = FileWriter.getSysInfoDir();

        for (String ip : ips) {
            try {
                if (!list.contains(ip)) {
                    MonitorPlatformServerEntity serverEntity = getServerEntity(ip, dir);
                    serverService.save(serverEntity);
                } else if (update) {
                    MonitorPlatformServerEntity serverEntity = getServerEntity(ip, dir);
                    serverService.update(serverEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @return
     */
    @RequestMapping(value = "server/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String update() {
        writeServerToDb(true);
        return "ok";
    }

    /**
     * @return
     */
    @RequestMapping(value = "server/process", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String process(String ip) {
        String dir = FileWriter.getSysInfoDir();
        String data = FileRender.readFile(dir + ip);
        List<ProcessEntity> list = new ArrayList<>();
        try {
            Type type = new TypeToken<ArrayList<SysinfoDataEntity>>() {
            }.getType();
            List<SysinfoDataEntity> sysinfoDataEntities = new Gson().fromJson(data, type);

            for (SysinfoDataEntity entity : sysinfoDataEntities) {
                if (entity.getName().equals("process")) {
                    String[] datas = entity.getResult().toString().split("\n");
                    for (String pdatas : datas) {
                        ProcessEntity processEntity = new ProcessEntity();
                        String[] pdata = pdatas.split(" ");
                        processEntity.setUser(pdata[0]);
                        processEntity.setCpu(pdata[1]);
                        processEntity.setMemory(pdata[2]);
                        processEntity.setProcess(pdata[3]);
                        list.add(processEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", 0);
        map.put("recordsFiltered", 0);
        map.put("draw", 1);
        return new Gson().toJson(map);
    }

    /**
     * 查询已在监控的地址
     *
     * @param ip
     *
     * @return
     */
    @RequestMapping(value = "server/servers", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getServers(String ip) {
        PageBounds pageBounds = PageResponse.getPageBounds(100000, 1);
        SearchMap searchMap = new SearchMap();
        searchMap.put("ip", ip);
        PagingResult<MonitorPlatformServerEntity> result = serverService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, 1);
    }

    /**
     * @return
     */
    @RequestMapping(value = "server/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String groupsListData(int draw, int start, int length, HttpServletRequest request) {
        if (System.currentTimeMillis() / 1000 - toDbTime > 600) {
            writeServerToDb(false);
            toDbTime = System.currentTimeMillis() / 1000;
        }
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null) {
            searchMap.put("key", search);
        }
        List<MonitorPlatformServerEntity> list = new ArrayList<>();
        PagingResult<MonitorPlatformServerEntity> result = serverService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorPlatformServerEntity entity : result.getRows()) {
            try {
                String ip = entity.getIp();
                String cpu = FileWriter.getIndexData(ip, "cpu", "system.cpu.user", 1);
                entity.setCpu(cpu);
                String loadavg = FileWriter.getIndexData(ip, "loadavg", "system.load.15", 1);
                entity.setLoadavg(loadavg);
                String iowait = FileWriter.getIndexData(ip, "cpu", "system.cpu.iowait", 1);
                entity.setIowait(iowait);
                String iowaitTime = FileWriter.getIndexData(ip, "cpu", "system.cpu.iowait", 0);
                entity.setLastModifyTime(iowaitTime);
                if (System.currentTimeMillis() / 1000 - Long.valueOf(iowaitTime) / 1000 < 300) {
                    entity.setStatus("UP");
                } else {
                    entity.setStatus("DOWN");
                }
                list.add(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", result.getTotal());
        map.put("recordsFiltered", result.getTotal());
        map.put("draw", draw);
        return new Gson().toJson(map);
    }


    /**
     * @return
     */
    @RequestMapping(value = "server/serverData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getServerData(int draw, int start, int length, HttpServletRequest request, String datas, String groups) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null) {
            searchMap.put("key", search);
        }
        if (groups != null && groups.length() > 1) {
            searchMap.put("groups", groups);
        }
        List<MonitorPlatformServerEntity> list = new ArrayList<>();
        PagingResult<MonitorPlatformServerEntity> result = serverService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorPlatformServerEntity entity : result.getRows()) {
            entity.setLastModifyTime(null);
            String ip = entity.getIp();
            int count = 1;
            for (String data : datas.split(",")) {
                String data1 = getItemData(data, ip, 1);
                if (entity.getLastModifyTime() == null) {
                    String time = getItemData(data, ip, 0);
                    entity.setLastModifyTime(time);
                }
                switch (count) {
                    case 1:
                        entity.setData1(data1);
                        break;
                    case 2:
                        entity.setData2(data1);
                        break;
                    case 3:
                        entity.setData3(data1);
                        break;
                    case 4:
                        entity.setData4(data1);
                        break;
                    case 5:
                        entity.setData5(data1);
                        break;
                }
                count++;
            }
            list.add(entity);
        }
        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", result.getTotal());
        map.put("recordsFiltered", result.getTotal());
        map.put("draw", draw);
        return new Gson().toJson(map);
    }
}
