package com.zfoo.ztest.bigdata.zookeeper.curator.create;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class CreateSession {


    public static void main(String[] args) throws InterruptedException {
        //RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3); // /ekspəʊ'nenʃl/ adj.  指数的; 幂数的
        //RetryPolicy retryPolicy = new RetryNTimes(5, 1000); // 最多重试5次，每次一秒

        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000); // elapsed /ɪ'læps/ v.  过去; 消逝

//		CuratorFramework client = CuratorFrameworkFactory.newClient(IP,5000,5000, retryPolicy);
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(Constant.IP)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        Thread.sleep(Integer.MAX_VALUE);


    }

}
