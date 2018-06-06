package com.zfoo.scheduler;

import com.zfoo.scheduler.manager.ISchedulerManager;
import com.zfoo.scheduler.manager.SchedulerManager;
import com.zfoo.scheduler.service.ISchedulerService;
import com.zfoo.scheduler.service.SchedulerService;
import com.zfoo.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-24 00:38
 */
public class SchedulerContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static SchedulerContext instance;

    private ApplicationContext applicationContext;

    private ISchedulerManager schedulerManager;

    private ISchedulerService schedulerService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (instance == null) {
            instance = this;
            instance.applicationContext = applicationContext;
            instance.schedulerManager = (ISchedulerManager) applicationContext.getBean(StringUtils.uncapitalize(SchedulerManager.class.getSimpleName()));
            instance.schedulerService = (ISchedulerService) applicationContext.getBean(StringUtils.uncapitalize(SchedulerService.class.getSimpleName()));
        }
    }

    public static ISchedulerManager getSchedulerManager() {
        return instance.schedulerManager;
    }

    public static ISchedulerService getSchedulerService() {
        return instance.schedulerService;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
