package com.zfoo.ztest.bigdata.zookeeper.recipes.distributedbarrier;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用Curator实现分布式Barrier
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 16:16
 */

public class DistributedBarrierTest {

    static String barrier_path = "/curator_recipes_barrier_path";
    static org.apache.curator.framework.recipes.barriers.DistributedBarrier barrier;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {

                        CuratorFramework client = CuratorFrameworkFactory
                                .builder()
                                .connectString(Constant.IP)
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                .build();
                        client.start();
                        barrier = new DistributedBarrier(client, barrier_path);
                        System.out.println(Thread.currentThread().getName() + "号barrier设置");
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.err.println("启动...");

                    } catch (Exception e) {
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }

}
