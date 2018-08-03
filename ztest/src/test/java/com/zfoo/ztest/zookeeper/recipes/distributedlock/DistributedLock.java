package com.zfoo.ztest.zookeeper.recipes.distributedlock;

import com.zfoo.ztest.zookeeper.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 使用Curator实现分布式锁功能
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 15:47
 */
public class DistributedLock {

    static String lock_path = "/node";
    static CuratorFramework client = CuratorFrameworkFactory
            .builder()
            .connectString(Constant.IP)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                        lock.acquire();
                    } catch (Exception e) {
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号是 : " + orderNo);

                    try {
                        lock.release();
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
        down.countDown();
    }

}
