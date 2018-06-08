package com.zfoo.ztest.zookeeper.curator;

import com.zfoo.ztest.zookeeper.constant.AbstractConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class DelNode {


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

        // guaranteed() 持续删除，直到成功
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/jike20");

        Thread.sleep(Integer.MAX_VALUE);
    }

}
