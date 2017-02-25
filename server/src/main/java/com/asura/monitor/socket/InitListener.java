package com.asura.monitor.socket;

import com.asura.monitor.socket.server.UDPServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 初始化接收数据的端口
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent context) {
    }


    @Override
    public void contextInitialized(ServletContextEvent context) {
        // 上下文初始化执行
        UDPServer.startPort();
    }
}