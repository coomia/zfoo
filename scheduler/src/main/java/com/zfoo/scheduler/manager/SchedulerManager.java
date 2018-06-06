package com.zfoo.scheduler.manager;

import com.zfoo.scheduler.SchedulerContext;
import com.zfoo.scheduler.model.anno.Scheduler;
import com.zfoo.scheduler.model.vo.SchedulerDef;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 11:31
 */
public class SchedulerManager implements ISchedulerManager {

    private String schedulerId;
    private String schedulerPackage;

    private Map<String, SchedulerDef> schedulerDefMap = new HashMap<>();


    public SchedulerManager(String schedulerId, String schedulerPackage) {
        this.schedulerId = schedulerId;
        this.schedulerPackage = schedulerPackage;
    }

    public void registerScheduler(Object bean) {
        Method[] methods = ReflectionUtils.getMethodsByAnnoInPOJOClass(bean.getClass(), Scheduler.class);
        for (Method method : methods) {
            SchedulerDef schedulerDef = SchedulerDef.valueOf(bean, method);
            String scheduelrName = schedulerDef.getSchedulerName();
            if (schedulerDefMap.containsKey(scheduelrName)) {
                FormattingTuple message = MessageFormatter.format("类[class:{}]重复的调度任务[scheduler{}]", bean.getClass(), method.getName());
                throw new IllegalArgumentException(message.getMessage());
            }
            schedulerDefMap.put(schedulerDef.getSchedulerName(), schedulerDef);
        }
    }

    public void init() {
        for (SchedulerDef schedulerDef : schedulerDefMap.values()) {
            SchedulerContext.getSchedulerService().schedule(schedulerDef.getRunnable(), schedulerDef.getCronExpression());
        }
    }

}
