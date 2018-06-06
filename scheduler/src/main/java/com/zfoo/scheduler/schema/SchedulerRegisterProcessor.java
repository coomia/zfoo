package com.zfoo.scheduler.schema;

import com.zfoo.scheduler.SchedulerContext;
import com.zfoo.scheduler.manager.SchedulerManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 11:14
 */
public class SchedulerRegisterProcessor extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        SchedulerManager schedulerManager = (SchedulerManager) SchedulerContext.getSchedulerManager();
        schedulerManager.registerScheduler(bean);
        return bean;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SchedulerManager schedulerManager = (SchedulerManager) SchedulerContext.getSchedulerManager();
        schedulerManager.init();
    }

}
