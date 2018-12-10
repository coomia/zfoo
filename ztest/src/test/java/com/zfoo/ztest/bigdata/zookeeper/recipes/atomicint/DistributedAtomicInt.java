package com.zfoo.ztest.bigdata.zookeeper.recipes.atomicint;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * 使用Curator实现分布式计数器
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 16:09
 */
public class DistributedAtomicInt {


    static String distatomicint_path = "/curator_recipes_distatomicint_path";
    static CuratorFramework client = CuratorFrameworkFactory
            .builder()
            .connectString(Constant.IP)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, distatomicint_path,
                        new RetryNTimes(3, 1000));

        AtomicValue<Integer> rc = atomicInteger.add(8);

        System.out.println("Result: " + rc.succeeded());
    }

}
