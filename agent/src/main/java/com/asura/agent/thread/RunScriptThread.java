package com.asura.agent.thread;

import com.google.gson.Gson;
import com.asura.agent.configure.Configure;
import com.asura.agent.controller.MonitorController;
import com.asura.agent.entity.PushEntity;
import com.asura.agent.util.MonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zhaoyun on 2017/4/26.
 */
public class RunScriptThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(RunScriptThread.class);
    private static final String SUCCESS_API_URL = "/monitor/api/success";
    private static final String FAILD_API_URL = "/monitor/api/faild";
    private boolean isDebug;

    private HashSet<String> SCRIPT_TIME;

    public RunScriptThread(boolean isDebug, HashSet<String> SCRIPT_TIME) {
        this.isDebug = isDebug;
        this.SCRIPT_TIME = SCRIPT_TIME;
    }


    public void run() {
        Gson gson = new Gson();
        ArrayList<PushEntity> success = new ArrayList<>();
        ArrayList<PushEntity> faild = new ArrayList<>();
        MonitorUtil.info(isDebug ? "SCRIPT_TIME " + gson.toJson(SCRIPT_TIME) : null);
        for (String time : SCRIPT_TIME) {
            MonitorUtil.info(isDebug ? "String time " + time : null);
            MonitorController.runScript(time, success, faild);
        }
        if (faild.size() > 0) {
            logger.info("获取到失败状态".concat(gson.toJson(faild)).concat(FAILD_API_URL));
            MonitorController.pushMonitor(faild, FAILD_API_URL, false);
        }
        if (success.size() > 0) {
            MonitorController.pushMonitor(success, SUCCESS_API_URL, true);
        }
    }
}
