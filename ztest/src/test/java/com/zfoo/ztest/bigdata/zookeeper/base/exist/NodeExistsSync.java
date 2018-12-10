package com.zfoo.ztest.bigdata.zookeeper.base.exist;

import com.zfoo.ztest.bigdata.zookeeper.Constant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class NodeExistsSync implements Watcher {


    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(Constant.IP, 5000, new NodeExistsSync());
        System.out.println(zooKeeper.getState().toString());

        Thread.sleep(Integer.MAX_VALUE);


    }

    private void doSomething(ZooKeeper zooKeeper) {


        try {
            Stat stat = zooKeeper.exists("/node_1", true);
            System.out.println(stat);

        } catch (Exception e) {


        }

    }

    @Override
    public void process(WatchedEvent event) {

        if (event.getState() == KeeperState.SyncConnected) {
            if (event.getType() == EventType.None && null == event.getPath()) {
                doSomething(zooKeeper);
            } else {
                try {
                    if (event.getType() == EventType.NodeCreated) {
                        System.out.println(event.getPath() + " created");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                    } else if (event.getType() == EventType.NodeDataChanged) {
                        System.out.println(event.getPath() + " updated");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                    } else if (event.getType() == EventType.NodeDeleted) {
                        System.out.println(event.getPath() + " deleted");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                    }

                } catch (Exception e) {
                }

            }

        }
    }

}
