package com.zfoo.ztest.zookeeper.delete;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class DeleteNodeSync implements Watcher {

    private static String IP = "192.168.238.128:2181";

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(IP, 5000, new DeleteNodeSync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(ZooKeeper zooKeeper) {
        try {
            zooKeeper.delete("/node_1", -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomething(zooKeeper);
            }

        }
    }

}
