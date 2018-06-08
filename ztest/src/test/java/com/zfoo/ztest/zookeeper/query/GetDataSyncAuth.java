package com.zfoo.ztest.zookeeper.query;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class GetDataSyncAuth implements Watcher {

    private static String IP = "192.168.238.128:2181";

    private static ZooKeeper zooKeeper;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(IP, 5000, new GetDataSyncAuth());

        System.out.println(zooKeeper.getState().toString());

        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(ZooKeeper zookeeper) {
        zooKeeper.addAuthInfo("digest", "jike:1234".getBytes());
        try {
            // 同步方法，会阻塞
            byte[] bytes=zooKeeper.getData("/node_4", true, stat);
            System.out.println(new String(bytes));

            System.out.println("会阻塞");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomething(zooKeeper);
            } else {
                if (event.getType() == EventType.NodeDataChanged) {
                    try {
                        System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                        System.out.println("stat:" + stat);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
