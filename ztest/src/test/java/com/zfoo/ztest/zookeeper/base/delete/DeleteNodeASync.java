package com.zfoo.ztest.zookeeper.base.delete;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.zfoo.ztest.zookeeper.Constant;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class DeleteNodeASync implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(Constant.IP, 5000, new DeleteNodeASync());
        System.out.println(zooKeeper.getState().toString());

        connectedSemaphore.await();

        zooKeeper.delete("/node_1", -1, new IVoidCallback(), null);

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

    static class IVoidCallback implements AsyncCallback.VoidCallback {
        @Override
        public void processResult(int rc, String path, Object ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            System.out.println(sb.toString());

        }

    }

}
