package com.zfoo.scheduler.test;

import com.zfoo.scheduler.model.anno.Scheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 16:52
 */
@Component
public class TestScheduler {

    @Scheduler(value = "testScheduler1", cronExpression = "0/5 * * * * ?")
    public void scheduler1() {
        System.out.println("testScheduler1:" + new Date());
    }

    @Scheduler(value = "testScheduler2", cronExpression = "0,10,20,40,55 * * * * ?")
    public void scheduler2() {
        System.out.println("testScheduler2:" + new Date());
    }
}
