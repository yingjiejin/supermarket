package com.supermarket.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jConfigListener implements ServletContextListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        String path = System.getProperty("log.config");
        if (StringUtils.isNoneBlank(path)) {
            try {
                PropertyConfigurator.configureAndWatch(path, 100);
            } catch (Exception e) {
            }
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
