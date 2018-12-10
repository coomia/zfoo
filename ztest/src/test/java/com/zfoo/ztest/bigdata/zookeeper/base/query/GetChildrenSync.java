package com.zfoo.ztest.bigdata.zookeeper.base.query;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class GetChildrenSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zk = new ZooKeeper(Constant.IP, 5000, new GetChildrenSync());

        System.out.println(zk.getState().toString());

        connectedSemaphore.await();

        String path = "/zk-book";
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeChildrenChanged) {
                try {
                    System.out.println("ReGet Child:" + zk.getChildren(event.getPath(), true));
                } catch (Exception e) {
                }
            }
        }
    }
}
