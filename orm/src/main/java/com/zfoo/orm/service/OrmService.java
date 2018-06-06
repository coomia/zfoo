package com.zfoo.orm.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 17:38
 */
public class OrmService implements IOrmService {

    // 所有对数据库的插入，更新，删除都在这一个线程池中
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void scheduleWithNoDelay(Runnable runnable) {
        schedule(runnable, 0);
    }

    @Override
    public void schedule(Runnable runnable, long delay) {
        executor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void scheduleAtFixedRate(Runnable runnable, long period) {
        executor.scheduleAtFixedRate(runnable, 0, period, TimeUnit.MILLISECONDS);
    }
}
