package com.zfoo.ztest.bigdata.zookeeper.curator.query;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Checkexists {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(5);

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(Constant.IP)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        client.checkExists().inBackground(new BackgroundCallback() {

            @Override
            public void processResult(CuratorFramework arg0, CuratorEvent arg1) throws Exception {
                Stat stat = arg1.getStat();
                System.out.println(stat);
                System.out.println(arg1.getContext());
            }
        }, "123", es).forPath("/jike");

        Thread.sleep(Integer.MAX_VALUE);
    }

}
