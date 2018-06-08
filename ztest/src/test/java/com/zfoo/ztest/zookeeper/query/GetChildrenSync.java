package com.zfoo.ztest.zookeeper.query;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


public class GetChildrenSync implements Watcher{

	private static String IP = "192.168.238.128:2181";
	
    private static ZooKeeper zooKeeper;

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zooKeeper = new ZooKeeper(IP,5000,new GetChildrenSync());

		System.out.println(zooKeeper.getState().toString());
			
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(ZooKeeper zooKeeper){
		try {
			List<String> children =  zooKeeper.getChildren("/", true);
			System.out.println(children);
		} catch (Exception e) {
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){// 连接刚刚建立
				doSomething(zooKeeper);
			}else{
				if (event.getType()==EventType.NodeChildrenChanged){
					try {
						System.out.println(zooKeeper.getChildren(event.getPath(), true));
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
