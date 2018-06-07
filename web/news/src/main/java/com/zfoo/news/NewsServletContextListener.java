package com.zfoo.news;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-07 10:37
 */
public class NewsServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println(this.getClass().getSimpleName() + "-->contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println(this.getClass().getSimpleName() + "-->contextDestroyed()");

        ServletContext ctx = event.getServletContext();


        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        while (drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                ctx.log(String.format("Driver %s deregistered", driver));
            } catch (SQLException ex) {
                ctx.log(String.format("Error deregistering driver %s", driver), ex);
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            ctx.log("SEVERE problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }


    }

}
