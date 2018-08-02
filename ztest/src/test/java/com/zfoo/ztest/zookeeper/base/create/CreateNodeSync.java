package com.zfoo.ztest.zookeeper.base.create;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.zfoo.ztest.zookeeper.Constant;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;


public class CreateNodeSync implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		// 这个创建zookeeper的链接是异步的
		ZooKeeper zookeeper = new ZooKeeper(Constant.IP,5000,new CreateNodeSync());

		System.out.println(zookeeper.getState());

		connectedSemaphore.await();

		// 同步创建节点
		// 无论是同步还是异步，Zookeeper都不支持递归创建，既无法在父节点不存在的情况下创建一个子节点。如果创建的节点存在，则抛出异常。
		String path = zookeeper.create("/node_4", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("return path:"+path);
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	


	@Override
	public void process(WatchedEvent event) {
		System.out.println("接受事件"+event);
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				connectedSemaphore.countDown();
			}
		}
	}
	
}
