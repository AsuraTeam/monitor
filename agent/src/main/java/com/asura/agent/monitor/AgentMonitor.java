package com.asura.agent.monitor;

import com.google.gson.Gson;
import com.asura.agent.entity.PushEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 2017-03-07
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class AgentMonitor {

    private static final DecimalFormat DF = new DecimalFormat("######0.00");

    /**
     * 获取线程数
     * @return
     */
    public static void getThreadNumber(List<PushEntity> list, String ip, MBeanServerConnection rm, String groups)  {
        try {
            ObjectName name = new ObjectName("java.lang:type=Threading");
            list.add(getPushEntity(ip, "thread.count", String.valueOf(rm.getAttribute(name, "ThreadCount")), groups));
            list.add(getPushEntity(ip, "daemon.thread.count", String.valueOf(rm.getAttribute(name, "DaemonThreadCount")), groups));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置监控数据
     * @return
     */
    public static ArrayList<PushEntity> setPushEntitys(String ip) {
        ArrayList<PushEntity> list = new ArrayList<>();
        MBeanServerConnection rm = ManagementFactory.getPlatformMBeanServer();
        getThreadNumber(list, ip, rm, "agent");
        getMemroyUsed(list, ip, rm, "agent");
        return list;
    }



    /**
     * 设置监控数据
     * @param ip
     * @param name
     * @param value
     * @return
     */
   static PushEntity getPushEntity(String ip, String name, String value, String groups){
        PushEntity pushEntity  = new PushEntity();
        pushEntity.setGroups(groups);
        pushEntity.setName(name);
        pushEntity.setValue(value);
        pushEntity.setStatus("1");
        pushEntity.setIp(ip);
        pushEntity.setScriptId("0");
        return pushEntity;
    }

    /**
     *
     * @param list
     * @param ip
     * @param name
     * @param used
     * @param committed
     */
     static void setMemroryUsed(List<PushEntity> list, String ip, String name, long used, long committed, String groups){
        list.add(getPushEntity(ip, name.concat(".committed"), DF.format(committed / 1024.0 /1024), groups));
        list.add(getPushEntity(ip, name.concat(".used"), DF.format(used / 1024.0 / 1024 ), groups));
        list.add(getPushEntity(ip, name.concat(".percent"), DF.format((double) used / committed * 100 ), groups));
    }

    /**
     * @param list
     * @param ip
     */
    public static void getMemroyUsed(List<PushEntity> list, String ip, MBeanServerConnection rm, String groups) {
        try {
            String[] keys = new String[]{"PS Perm Gen", "PS Old Gen", "PS Eden Space", "PS Survivor Space"};
            ObjectName objectName;
            MemoryUsage memoryUsage;
            for (String key : keys) {
                try {
                    objectName = new ObjectName("java.lang:type=MemoryPool,name=".concat(key));
                    memoryUsage = MemoryUsage.from((CompositeDataSupport) rm.getAttribute(objectName, "Usage"));
                    setMemroryUsed(list, ip, key.replace(" ", "."), memoryUsage.getUsed(), memoryUsage.getCommitted(), groups);
                }catch (Exception e){
                }
            }
        }catch (Exception e){

        }
    }

}
