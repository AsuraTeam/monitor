package com.asura.monitor.top.controller;

import com.google.gson.Gson;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.graph.controller.CommentController;
import com.asura.monitor.graph.util.FileRender;
import com.asura.util.CheckUtil;
import com.asura.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.asura.monitor.graph.util.FileWriter.dataDir;
import static com.asura.monitor.graph.util.FileWriter.separator;

/**
 * 获取top图需要的监控信息数据
 * Created by zhaoyun on 2017/8/21.
 */
@RequestMapping("/monitor/top/data/")
@Controller
public class TopDataController {

    private static Map<String, String> ScriptMap;

    private Logger logger = LoggerFactory.getLogger(TopDataController.class);

    @Autowired
    private CommentController commentController;


    /**
     * 获取IP地址对应的ID
     *
     * @param ip
     * @param redisUtil
     * @return
     */
    String getServerId(String ip, RedisUtil redisUtil) {
        return redisUtil.get(MonitorCacheConfig.hostsIdKey.concat(ip));
    }

    /**
     * @param name
     * @return
     */
    String getScriptId(String name) {
        if (null == ScriptMap){
            ScriptMap = new HashMap<>();
        }
        if (!ScriptMap.containsKey(name)) {
            name = FileRender.replace(name);
            String file = dataDir + separator + "graph" + separator + "index" + separator + name +separator+ "id";
            String data = FileRender.readLastLine(file);
            if (CheckUtil.checkString(data)) {
                ScriptMap.put(name, data);
            }
        }
        return ScriptMap.get(name).trim();
    }

    /**
     * @param fileDanger
     * @return
     */
    String testStatus(String fileDanger, String returnValue) {
        String data;
        String[] datas;

        data = FileRender.readLastLine(fileDanger);
        if (CheckUtil.checkString(data)) {
            datas = data.split(" ");
            Integer t = Integer.valueOf(datas[0]);
            logger.info("now t" + t +" " +data);
            if (System.currentTimeMillis() / 1000 - t < 1800) {
                return returnValue;
            }
        }
        return "1";
    }

    /**
     *
     * @param ip
     * @param name
     * @return
     */
    @RequestMapping("status")
    @ResponseBody
    public String getStatus(String ip, String name) {
        name = FileRender.replace(name);
        RedisUtil redisUtil = new RedisUtil();
        String id = getServerId(ip, redisUtil);
        String scriptId = getScriptId(name.replace("|", "."));
        name = name.replace("|", "_");
        String fileDanger = dataDir + separator + "monitor" +separator+ id + separator + scriptId + "_2_"  + name;
        String fileWarning = dataDir + separator + "monitor" +separator+ id + separator + scriptId + "_3_"  + name;
        logger.info("fileDanger" + fileDanger) ;
        logger.info("fileWarning" + fileWarning) ;



        File testFileDanger = new File(fileDanger);
        File testFileWarning = new File(fileWarning);
        if (!testFileDanger.exists() && !testFileWarning.exists()) {
            // 成功
            return "1";
        }
        if (testFileDanger.exists()) {
            return testStatus(fileDanger, "2");
        }
        if (testFileWarning.exists()) {
            return testStatus(fileWarning, "3");
        }
        return "1";
    }

    /**
     * 批量获取数据
     * @param ips
     * @param name
     * @return
     */
    @RequestMapping(value = "allData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getMetricData(String ips, String name){
        name = FileRender.replace(name);
        Gson gson = new Gson();
        Map resultMap = new HashMap();
        Map map = new HashMap();
        Map statusMap = new HashMap();
        String[] ipDatas = ips.split(",");
        String[] names = name.split(",");
        String[] splitName;
        String result;
        for (String ip:ipDatas) {
            for (String nameData:names) {
                splitName = nameData.split("\\|");
                result = commentController.historyData(ip, splitName[1], "", "", null, splitName[0], null, "1");
                if (CheckUtil.checkString(result)) {
                    map.put(ip + nameData, ((ArrayList) gson.fromJson(result, ArrayList.class).get(0)).get(1));
                } else {
                    map.put(ip + nameData, 0);
                }
                statusMap.put(ip + nameData, getStatus(ip, nameData));
            }
        }
        resultMap.put("status", statusMap);
        resultMap.put("metric", map);
        return gson.toJson(resultMap);
    }
}