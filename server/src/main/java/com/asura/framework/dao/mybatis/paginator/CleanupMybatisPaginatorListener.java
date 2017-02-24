package com.asura.framework.dao.mybatis.paginator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 */
public class CleanupMybatisPaginatorListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        OffsetLimitInterceptor.Pool.shutdownNow();
    }
}
