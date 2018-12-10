package com.zfoo.ztest.bigdata.zookeeper.base.delete;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DeleteNodeSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(Constant.IP, 5000, new DeleteNodeSync());
        System.out.println(zooKeeper.getState().toString());

        connectedSemaphore.await();

        // 如果一个节点存在至少一个子节点的话，那么该节点将无法被直接删除，必须先删除掉其它所有子节点
        zooKeeper.delete("/node_1", -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }

}
