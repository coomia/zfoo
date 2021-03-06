package com.zfoo.event;

import com.zfoo.event.manager.EventBusManager;
import com.zfoo.event.manager.IEventBusManager;
import com.zfoo.util.ReflectionUtils;
import com.zfoo.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 09:54
 */
public class EventContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static EventContext instance;

    private ApplicationContext applicationContext;

    private IEventBusManager eventBusManager;

    public static IEventBusManager getEventBusManager() {
        return instance.eventBusManager;
    }

    public static void shutdown() throws NoSuchFieldException, InterruptedException {
        IEventBusManager event = instance.eventBusManager;
        if (event == null) {
            return;
        }
        Field field = EventBusManager.class.getDeclaredField("executors");
        ReflectionUtils.makeAccessible(field);
        ExecutorService[] executors = (ExecutorService[]) ReflectionUtils.getField(field, event);
        if (executors != null) {
            for (ExecutorService executor : executors) {
                executor.shutdown();
            }
            for (ExecutorService executor : executors) {
                while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    // System.out.println("线程池没有关闭");
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (EventContext.instance != null) {
            return;
        }

        // 初始化上下文
        EventContext.instance = this;
        instance.applicationContext = applicationContext;
        instance.eventBusManager = (IEventBusManager) applicationContext.getBean(StringUtils.uncapitalize(EventBusManager.class.getName()));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
