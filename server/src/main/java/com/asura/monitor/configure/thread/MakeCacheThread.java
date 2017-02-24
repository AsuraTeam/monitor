package com.asura.monitor.configure.thread;

import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.configure.controller.SaveController;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @Date 2017-02-08
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class MakeCacheThread extends Thread {

    private CacheController cacheController;
    private String[] hosts;

    public MakeCacheThread(CacheController cacheController, String[] hosts) {
        this.cacheController = cacheController;
        this.hosts = hosts;
    }

    @Override
    public void run() {
        cacheController.allCache();
        SaveController saveController = new SaveController();
        for (String hostId : hosts){
            saveController.initMonitor(hostId);
        }
    }
}
