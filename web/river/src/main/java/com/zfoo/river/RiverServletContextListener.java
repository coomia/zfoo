package com.zfoo.river;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.zfoo.event.EventContext;
import com.zfoo.orm.OrmContext;
import com.zfoo.scheduler.SchedulerContext;

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
 * @since 2018/10/20
 */
public class RiverServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println(this.getClass().getSimpleName() + "-->contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println(this.getClass().getSimpleName() + "-->contextDestroyed()");

        ServletContext ctx = servletContext.getServletContext();


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
            ctx.log("Mysql problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            EventContext.shutdown();
        } catch (Exception e) {
            ctx.log("Event problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            OrmContext.shutdown();
        } catch (Exception e) {
            ctx.log("Orm problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            SchedulerContext.shutdown();
        } catch (Exception e) {
            ctx.log("Scheduler problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
