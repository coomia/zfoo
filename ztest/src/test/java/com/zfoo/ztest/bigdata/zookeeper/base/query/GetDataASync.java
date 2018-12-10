package com.zfoo.ztest.bigdata.zookeeper.base.query;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

// ZooKeeper API 获取节点数据内容，使用异步(async)接口。
public class GetDataASync implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zk = new ZooKeeper(Constant.IP, 5000, new GetDataASync());
        System.out.println(zk.getState().toString());

        String path = "/zk-book";
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zk.getData(path, true, new IDataCallback(), null);

        zk.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeDataChanged) {
                try {
                    zk.getData(event.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {
                }
            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback {
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println(rc + ", " + path + ", " + new String(data));
        System.out.println(stat.getCzxid() + "," +
                stat.getMzxid() + "," +
                stat.getVersion());
    }
}
