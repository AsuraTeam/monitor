package com.asura.monitor.graph.quartz;

import com.asura.monitor.configure.controller.CacheController;
import com.asura.monitor.configure.service.MonitorConfigureService;
import com.asura.monitor.configure.service.MonitorContactGroupService;
import com.asura.monitor.configure.service.MonitorItemService;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.monitor.socket.server.UDPServer;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.resource.service.CmdbResourceServerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
public class PushServerQuartz {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PushServerQuartz.class);

    private CacheController cacheController = new CacheController();

    @Autowired
    private MonitorConfigureService configureService = new MonitorConfigureService();

    @Autowired
    private CmdbResourceServerService service = new CmdbResourceServerService();

    @Autowired
    private MonitorContactGroupService contactGroupService = new MonitorContactGroupService();

    @Autowired
    private CmdbResourceGroupsService resourceGroupsService = new CmdbResourceGroupsService();

    @Autowired
    private MonitorScriptsService scriptsService = new MonitorScriptsService();

    @Autowired
    private  MonitorItemService itemService = new MonitorItemService();

    @Autowired
    private MonitorMessageChannelService channelService = new MonitorMessageChannelService();

    public void start(){
        try {
            LOGGER.info("执行设置push服务任务计划开始");
            UDPServer.setPushServer();
            LOGGER.info("执行设置push服务任务计划完成");
        }catch (Exception e){
            LOGGER.error("设置pushServer失败", e);
        }
        setCache();
    }

    /**
     * 生成缓存数据
     */
    void setCache(){
        String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")+"cacheLock";
        if (FileRender.readFile(tempPath).length() < 5) {
            FileWriter.writeFile(tempPath, System.currentTimeMillis() / 1000 + "", false);
            cacheController.makeAllHostKey(configureService);
            cacheController.setItemCache(itemService);
            cacheController.setMessagesCache(channelService);
            cacheController.setServerCache(service);
            cacheController.setScriptCache(scriptsService);
            cacheController.cacheGroups(resourceGroupsService, service);
            cacheController.setContactGroupCache(contactGroupService);

        }
    }
}
