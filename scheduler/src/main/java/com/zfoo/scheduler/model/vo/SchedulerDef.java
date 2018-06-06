package com.zfoo.scheduler.model.vo;

import com.zfoo.scheduler.model.anno.Scheduler;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Method;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 14:25
 */
public class SchedulerDef {

    private String schedulerName;

    private String cronExpression;

    private Object bean;

    private Method method;

    private Runnable runnable;

    private SchedulerDef() {
    }

    public static SchedulerDef valueOf(Object bean, Method method) {
        SchedulerDef schedulerDef = new SchedulerDef();
        Scheduler scheduler = method.getAnnotation(Scheduler.class);
        if (scheduler == null) {
            FormattingTuple message = MessageFormatter.format("类[class:{}]任务调度方法[method:{}]须用Scheduler注解", bean.getClass(), method.getName());
            throw new IllegalArgumentException(message.getMessage());
        }

        Class<?>[] clazzs = method.getParameterTypes();
        if (clazzs.length >= 1) {
            FormattingTuple message = MessageFormatter.format("类[class:{}]任务调度方法[method:{}]不能有方法参数", bean.getClass(), method.getName());
            throw new IllegalArgumentException(message.getMessage());
        }
        ReflectionUtils.makeAccessible(method);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ReflectionUtils.invokeMethod(bean, method);
            }
        };

        schedulerDef.setSchedulerName(scheduler.value());
        schedulerDef.setCronExpression(scheduler.cronExpression());
        schedulerDef.setBean(bean);
        schedulerDef.setMethod(method);
        schedulerDef.setRunnable(runnable);
        return schedulerDef;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
