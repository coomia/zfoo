package com.zfoo.ztest.zookeeper.base.create;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;


public class CreateNodeSyncAuth implements Watcher {

    private static String IP = "192.168.238.128:2181";

    private static ZooKeeper zookeeper;
    private static boolean somethingDone = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        zookeeper = new ZooKeeper(IP, 5000, new CreateNodeSyncAuth());
        System.out.println(zookeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);
    }

    /*
     * 权限模式(scheme): ip, digest
     * 授权对象(ID)
     * 		ip权限模式:  具体的ip地址
     * 		digest权限模式: username:Base64(SHA-1(username:password))
     * 权限(permission): create(C), DELETE(D),READ(R), WRITE(W), ADMIN(A)
     * 		注：单个权限，完全权限，复合权限
     *
     * 权限组合: scheme + ID + permission
     *
     *
     *
     * */

    private void doSomething() {
        try {
            // ACL access control list
            ACL aclIp = new ACL(Perms.READ, new Id("ip", "192.168.1.105"));

            ACL aclDigest = new ACL(Perms.READ | Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("jike:123456")));// 用户名+密码

            ArrayList<ACL> acls = new ArrayList<>();
            acls.add(aclDigest);
            acls.add(aclIp);
            // 下面两句设置ACL的哪些author可以访问
            // zookeeper.addAuthInfo("digest", "jike:123456".getBytes());
            // String path = zookeeper.create("/node_4", "123".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            String path = zookeeper.create("/node_4", "123".getBytes(), acls, CreateMode.PERSISTENT);
            System.out.println("return path:" + path);

            somethingDone = true;

        } catch (KeeperException | InterruptedException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件：" + event);
        if (event.getState() == KeeperState.SyncConnected) {
            if (!somethingDone && event.getType() == EventType.None && null == event.getPath()) {
                doSomething();
            }
        }
    }

}
