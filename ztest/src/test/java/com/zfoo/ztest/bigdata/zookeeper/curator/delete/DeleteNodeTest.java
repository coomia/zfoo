package com.zfoo.ztest.bigdata.zookeeper.curator.delete;

import com.zfoo.ztest.bigdata.zookeeper.ZookeeperConstantTest;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DeleteNodeTest {

    @Test
    public void test() throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(ZookeeperConstantTest.URL)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        // guaranteed() 持续删除，直到成功
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/node_test");

        Thread.sleep(Integer.MAX_VALUE);
    }

}
