package com.zfoo.ztest.bigdata.zookeeper.base.create;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;


public class CreateNodeASync implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);


    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zookeeper = new ZooKeeper(Constant.IP, 5000, new CreateNodeASync());

        System.out.println(zookeeper.getState());

        connectedSemaphore.await();

        String content = "Hello Zookeeper!!!";

        // 异步创建，会回调processResult()
        zookeeper.create("/node_1", content.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new StringCallbackImp(), "hello");

        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("接受事件" + event);
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }

    static class StringCallbackImp implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path=" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            sb.append("name=" + name);
            System.out.println(sb.toString());

        }


    }

}
