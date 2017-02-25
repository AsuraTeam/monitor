package com.asura.monitor.graph.quartz;

import com.asura.monitor.socket.server.UDPServer;
import org.slf4j.Logger;

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

    public void start(){
        try {
            LOGGER.info("执行设置push服务任务计划开始");
            UDPServer.setPushServer();
            LOGGER.info("执行设置push服务任务计划完成");
        }catch (Exception e){
            LOGGER.error("设置pushServer失败", e);
        }
    }
}
