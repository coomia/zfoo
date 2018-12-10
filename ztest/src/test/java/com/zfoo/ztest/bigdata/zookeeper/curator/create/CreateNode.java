package com.zfoo.ztest.bigdata.zookeeper.curator.create;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

public class CreateNode {

    public static void main(String[] args) throws Exception {
        // 重试的时间超过最大时间后，就不再重试
        // 参数说明:
        // maxElapsedTimeMs: 最大的重试时间
        // sleepMsBetweenRetries：每次重试的间隔时间
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(Constant.IP)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // .namespace(basePath); 指定namespace，该客户端的如何操作，都是基于该相对目录进行的。
                .build();

        client.start();

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/jike", "123".getBytes());

        System.out.println(path);

        Thread.sleep(Integer.MAX_VALUE);

    }
}
