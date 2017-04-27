package com.asura.agent.util;

import com.sun.management.OperatingSystemMXBean;
import com.asura.agent.thread.RunCmdThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static org.aspectj.bridge.MessageUtil.info;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class CommandUtil {

    public static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);
    private static final Runtime runtime = Runtime.getRuntime();

    /**
     * 上报cpu个数
     * @return
     */
    public static String getCpuNumber(){
        return  String.valueOf(runtime.availableProcessors());
    }

    /**
     * 获取内存大小
     */
    public static String getMemorySize(){
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return "" + osmb.getTotalPhysicalMemorySize() /  1024 /1024 / 1024;
    }

    /**
     * 获取硬盘总大小
     * @return
     */
    public static String getDiskSize(){
        File[] roots = File.listRoots();
        long disk = 0L;
        // 获取磁盘分区列表
        for (File file : roots) {
            disk += file.getTotalSpace();
        }
        return disk / 1024 /1024 /1024 +"";
    }

    /**
     *
     * @param command
     * @return
     */
    public static String execScript(String command){
        String result = "";
        String line = "";
        try {
            MonitorUtil.info("run 获取到脚本 " + command);
            Process process = runtime.exec(command);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                info(line);
                result += line;
            }
            is.close();
            isr.close();
            br.close();
        } catch (Exception e) {
            logger.error("脚本执行出错", e);
        }
        return result;
    }

    /**
     * @param command
     *
     * @return
     */
    public static String runScript(String command, int timeOut, ExecutorService executor) {
        List<String> list = new ArrayList();
        RunCmdThread thread = new RunCmdThread(command, list);
        thread.start();
        long start = System.currentTimeMillis() / 1000;
        while (1 == 1) {
            if (list.size() > 0) {
                if (list.get(0).equals("00")) {
                    list.remove(0);
                }
                break;
            }
            if (!thread.isAlive()) {
                break;
            }
            if (System.currentTimeMillis() / 1000 - start > timeOut) {
                logger.error("线程超时:" + command + "超时时间 "+ timeOut);
                if (thread.isAlive()) {
                    try {
                        thread.interrupt();
                    } catch (Exception e) {
                    }
                }
                list.add("time out ");
                break;
            }
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return "";
        }
    }
}