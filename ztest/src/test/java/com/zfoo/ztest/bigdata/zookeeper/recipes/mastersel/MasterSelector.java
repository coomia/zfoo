package com.zfoo.ztest.bigdata.zookeeper.recipes.mastersel;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 15:32
 */
public class MasterSelector {

    // master选举的根节点，表面本次master选举都是在该节点下进行的
    private static String master_path = "/curator_recipes_master_path";

    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("domain1.book.zookeeper:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) throws Exception {
        client.start();

        // curator会在成功获取Master权利的时候回调该监听器
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println("成为Master角色");
                        Thread.sleep(3000);
                        System.out.println("完成Master操作，释放Master权利");
                    }
                });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
