package com.asura.agent.thread;

import com.asura.agent.controller.MonitorController;

/**
 * Created by zhaoyun on 2017/4/26.
 */
public class AgentPingThread extends Thread {

    public void run() {
        try {
            MonitorController.checkMonitorUpdate();
            this.interrupt();
        }catch (Exception e){

        }
    }
}
