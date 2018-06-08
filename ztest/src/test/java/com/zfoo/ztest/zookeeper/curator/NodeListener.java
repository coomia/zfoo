package com.zfoo.ztest.zookeeper.curator;

import com.zfoo.ztest.zookeeper.constant.AbstractConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;

public class NodeListener {

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

        final NodeCache cache = new NodeCache(client, "/jike");
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                byte[] ret = cache.getCurrentData().getData();
                System.out.println("new data:" + new String(ret));
            }
        });

        Thread.sleep(Integer.MAX_VALUE);

    }

}
