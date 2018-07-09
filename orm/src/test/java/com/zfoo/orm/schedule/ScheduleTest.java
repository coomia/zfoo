package com.zfoo.orm.schedule;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-07 17:47
 */
@Ignore
public class ScheduleTest {

    private final static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Test
    public void test() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    int[] s = new int[1];
                    System.out.println("OK");
                    System.out.println(s[1]); // 数组越界
                } catch (Throwable t) {
                    System.out.println("Error");
                }

            }
        }, 0, 2, TimeUnit.SECONDS);
        while (true) {

        }
    }

}
