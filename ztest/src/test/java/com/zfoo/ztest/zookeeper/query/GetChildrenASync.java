package com.zfoo.ztest.zookeeper.query;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

public class GetChildrenASync implements Watcher {

    private static String IP = "192.168.238.128:2181";

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(IP, 5000, new GetChildrenASync());

        System.out.println(zooKeeper.getState().toString());

        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(ZooKeeper zookeeper) {
        try {
            // 异步方法，不会阻塞
            zooKeeper.getChildren("/", true, new IChildren2Callback(), null);
            System.out.println("不会阻塞");
        } catch (Exception e) {
        }


    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomething(zooKeeper);
            } else {
                if (event.getType() == EventType.NodeChildrenChanged) {
                    zooKeeper.getChildren(event.getPath(), true, new IChildren2Callback(), null);
                }
            }
        }
    }

    static class IChildren2Callback implements AsyncCallback.Children2Callback {

        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path=" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            sb.append("children=" + children).append("\n");
            sb.append("stat=" + stat).append("\n");
            System.out.println(sb.toString());

        }


    }

}
