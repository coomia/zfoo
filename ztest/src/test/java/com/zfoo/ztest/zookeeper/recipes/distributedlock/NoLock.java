package com.zfoo.ztest.zookeeper.recipes.distributedlock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 15:46
 */
public class NoLock {

    public static void main(String[] args) throws Exception {
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是 : " + orderNo);
                }
            }).start();
        }
        down.countDown();
    }

}
