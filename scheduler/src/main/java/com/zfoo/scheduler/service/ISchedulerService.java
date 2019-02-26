package com.zfoo.scheduler.service;

import java.util.concurrent.ScheduledFuture;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 16:07
 */
public interface ISchedulerService {

    /*
     用cron表达式执行的任务
     */
    ScheduledFuture<?> schedule(Runnable runnable, String cronExpression);

    /*
     固定延迟执行的任务
     */
    ScheduledFuture<?> schedule(Runnable runnable, long delay);


    /**
     * 固定周期执行的任务
     *
     * @param runnable 任务
     * @param period   默认毫秒
     * @return 完成是的future
     */
    ScheduledFuture<?> scheduleAtFixRate(Runnable runnable, long period);

}
