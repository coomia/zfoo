package com.zfoo.ztest.zookeeper.curator;

import com.zfoo.ztest.zookeeper.constant.AbstractConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

public class CreateNode {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(AbstractConstant.IP)
                    .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
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
