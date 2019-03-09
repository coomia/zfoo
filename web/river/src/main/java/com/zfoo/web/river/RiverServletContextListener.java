package com.zfoo.web.river;

import com.zfoo.event.EventContext;
import com.zfoo.scheduler.SchedulerContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

        // 先关闭任务调度
        try {
            SchedulerContext.shutdown();
        } catch (Exception e) {
            ctx.log("Scheduler problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

        // 再关闭事件派发
        try {
            EventContext.shutdown();
        } catch (Exception e) {
            ctx.log("Event problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
