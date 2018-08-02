package com.zfoo.ztest.zookeeper.curator;

import com.zfoo.ztest.zookeeper.Constant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;


/**
 * recipe [rec·i·pe || 'resɪpɪ] n.  食谱; 处方; 烹饪法; 制作法
 */
public class NodeListener {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(Constant.IP)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        // Cache是Curator中对事件监听的包装，其对事件的监听其实可以看做本地缓存视图和远程Zookeeper视图的对比过程。
        // 同时Recipes能够反复注册监听，从而大大简化了原生API开发的繁琐程度。
        final NodeCache cache = new NodeCache(client, "/jike");
        cache.start();

        // 节点创建，数据改变都能检测到；如果节点被删除则不能检测。
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
