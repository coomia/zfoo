package com.zfoo.event;

import com.zfoo.event.manager.EventBusManager;
import com.zfoo.event.manager.IEventBusManager;
import com.zfoo.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 09:54
 */
public class EventContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static EventContext instance;

    private ApplicationContext applicationContext;

    private IEventBusManager eventBusManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (EventContext.instance != null) {
            return;
        }

        // 初始化上下文
        EventContext.instance = this;
        instance.applicationContext = applicationContext;
        instance.eventBusManager = (IEventBusManager) applicationContext.getBean(StringUtils.uncapitalize(EventBusManager.class.getSimpleName()));
    }

    public static IEventBusManager getEventBusManager() {
        return instance.eventBusManager;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
