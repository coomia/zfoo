package com.zfoo.scheduler.service;

import com.zfoo.util.TimeUtils;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 16:07
 */
public class SchedulerService implements ISchedulerService {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, String cronExpression) {
        Date currentDate = new Date();
        Date nextDate = TimeUtils.getNextDateByCronExpression(cronExpression, currentDate);
        Runnable cronRunnable = new Runnable() {
            @Override
            public void run() {
                runnable.run();
                schedule(runnable, cronExpression);
            }
        };
        return schedule(cronRunnable, nextDate.getTime() - currentDate.getTime());
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
        return executor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixRate(Runnable runnable, long period) {
        return executor.scheduleAtFixedRate(runnable, 0, period, TimeUnit.MILLISECONDS);
    }

}
