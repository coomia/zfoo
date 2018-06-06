package com.zfoo.scheduler.service;

import java.util.concurrent.ScheduledFuture;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 16:07
 */
public interface ISchedulerService {

    /**
     * 用cron表达式执行的任务
     *
     * @param runnable
     * @param cronExpression
     * @return
     */
    ScheduledFuture<?> schedule(Runnable runnable, String cronExpression);

    /**
     * 固定延迟执行的任务
     *
     * @param runnable
     * @param delay
     * @return
     */
    ScheduledFuture<?> schedule(Runnable runnable, long delay);


    /**
     * 固定周期执行的任务
     *
     * @param runnable
     * @param period   默认毫秒
     * @return
     */
    ScheduledFuture<?> scheduleAtFixRate(Runnable runnable, long period);

}
