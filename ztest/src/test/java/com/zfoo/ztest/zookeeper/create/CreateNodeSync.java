package com.zfoo.ztest.zookeeper.create;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;


public class CreateNodeSync implements Watcher {

	private static String IP = "192.168.238.128:2181";

	private static ZooKeeper zookeeper;

	public static void main(String[] args) throws IOException, InterruptedException {
		// 这个创建zookeeper的链接是异步的
		zookeeper = new ZooKeeper(IP,5000,new CreateNodeSync());

		System.out.println(zookeeper.getState());
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	private void doSomething(){
		try {
			// 同步创建节点
			String path = zookeeper.create("/node_4", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("return path:"+path);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("do something");
	}


	@Override
	public void process(WatchedEvent event) {
		System.out.println("接受事件"+event);
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething();
			}
		}
	}
	
}
