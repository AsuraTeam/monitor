package com.asura.agent.thread;

import com.asura.agent.entity.PushEntity;
import com.asura.agent.monitor.AgentMonitor;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import java.util.ArrayList;

import static com.asura.agent.monitor.AgentMonitor.getRemoteMBConn;
import static com.asura.agent.monitor.AgentMonitor.getThreadNumber;

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

public class AgentMonitorThread extends Thread {

    private ArrayList<PushEntity> list;
    private String ip;
    private JMXConnector jmxc;

    /**
     *
     * @param list
     * @param ip
     * @param jmxc
     */
    public AgentMonitorThread(ArrayList<PushEntity> list, String ip, JMXConnector jmxc) {
        this.list = list;
        this.ip = ip;
        this.jmxc = jmxc;
    }

    public void run(){
        MBeanServerConnection local = getRemoteMBConn("127.0.0.1", "8887");
        if (local != null){
            getThreadNumber(list, ip, local, "java");
            AgentMonitor.getMemroyUsed(list, ip, local, "java");
            try {
                jmxc.close();
            }catch (Exception e){
            }
        }
    }
}
