package com.zfoo.client;

import com.zfoo.client.manager.ClientManager;
import com.zfoo.game.gameserver.module.activity.packet.CM_ActivityInfo;
import com.zfoo.net.NetContext;
import com.zfoo.net.server.model.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 09:51
 */
public class Client {

    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 9999;

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientManager.getInstance().connect(HOST_ADDRESS, PORT);
            }
        });
        thread.start();

        CM_ActivityInfo cm = new CM_ActivityInfo();
        cm.setA(100);


        boolean flag = false;
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(2000);
            for (Session session : NetContext.getSessionManager().getSessionMap().values()) {
                NetContext.getDispatcherManager().send(session, cm);
                flag = true;
                Thread.sleep(1000);
            }
        }
    }

}
