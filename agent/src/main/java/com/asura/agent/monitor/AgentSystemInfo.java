package com.asura.agent.monitor;


import com.google.gson.Gson;
import com.sun.management.OperatingSystemMXBean;
import com.asura.agent.util.CommandUtil;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 2017-03-12 agent信息获取
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class AgentSystemInfo {

    static String getDiskSpace(long number){
        return number / 1024 / 1024 / 1024 + "G";
    }

    /**
     *
     * @param map
     */
    static void getMemroy(Map map){
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map memMap = new HashMap();
        memMap.put("系统物理内存总计", getDiskSpace(osmb.getTotalPhysicalMemorySize()));
        memMap.put("系统物理可用内存总计", getDiskSpace(osmb.getFreePhysicalMemorySize()));
        map.put("内存信息", memMap);
    }

    /**
     *
     * @param map
     */
    static void getDiskMap(Map map){
        Map diskMap = new HashMap();
        File[] roots = File.listRoots();
        // 获取磁盘分区列表\
        int count = 0;
        for (File file : roots) {
            diskMap.put("磁盘"+file.getPath() + "总大小:", getDiskSpace(file.getTotalSpace()));
            diskMap.put("磁盘"+ file.getPath()+"未使用:", getDiskSpace(file.getFreeSpace()));
            diskMap.put("磁盘"+file.getPath() + "已使用:", getDiskSpace(file.getUsableSpace()));
            count += file.getTotalSpace() / 1024 /1024 /1024 ;
        }
        diskMap.put("磁盘总大小", count);
        map.put("磁盘信息", diskMap);
    }

    /**
     *
     * @param map
     */
    static void getSystemInfo(Map map){
        Map systemMap = new HashMap();
        Properties props = System.getProperties();
        systemMap.put("操作系统名称", props.getProperty("os.name"));
        systemMap.put("操作系统架构", props.getProperty("os.arch"));
        systemMap.put("操作系统版本", props.getProperty("os.version"));
        try {
            InetAddress inetaddress = InetAddress.getLocalHost();
            systemMap.put("主机名", inetaddress.getHostName());
        }catch (Exception e){
        }
        systemMap.put("cpu核心数", CommandUtil.getCpuNumber());
        map.put("系统信息", systemMap);
    }

    public static String getSystemInfo(){
        Gson gson = new Gson();
        Map map = new HashMap<>();
        getMemroy(map);
        getDiskMap(map);
        getSystemInfo(map);
        return gson.toJson(map);
    }
}
