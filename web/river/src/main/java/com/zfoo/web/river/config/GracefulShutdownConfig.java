package com.zfoo.web.river.config;

import com.zfoo.event.EventContext;
import com.zfoo.scheduler.SchedulerContext;
import com.zfoo.util.exception.ExceptionUtils;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * kill -2 和 kill -15 才能触发这个回调方法
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2019-03-10 19:43
 */
@Component
public class GracefulShutdownConfig implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private final Logger log = LoggerFactory.getLogger(GracefulShutdownConfig.class);
    private final int waitTime = 30;
    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                    log.warn("Tomcat thread pool did not shut down gracefully within " + waitTime + " seconds. Proceeding with forceful shutdown");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        // 先关闭任务调度
        try {
            SchedulerContext.shutdown();
            log.info("Scheduler shutdown gracefully!");
        } catch (Exception e) {
            log.error("Scheduler problem cleaning up: " + ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        // 再关闭事件派发
        try {
            EventContext.shutdown();
            log.info("Event shutdown gracefully!");
        } catch (Exception e) {
            log.error("Event problem cleaning up: " + ExceptionUtils.getStackTrace(e));
        }
    }
}