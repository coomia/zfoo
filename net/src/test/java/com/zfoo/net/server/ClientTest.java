package com.zfoo.net.server;

import com.zfoo.net.NetContext;
import com.zfoo.client.manager.ClientManager;
import com.zfoo.net.server.model.Session;
import com.zfoo.test.CM_Int;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 16:50
 */
public class ClientTest {

    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 9999;

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client_test_net.xml");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientManager.getInstance().connect(HOST_ADDRESS, PORT);
            }
        });
        thread.start();

        CM_Int cm = new CM_Int();
        cm.setFlag(false);
        cm.setA(Byte.MIN_VALUE);
        cm.setB(Short.MIN_VALUE);
        cm.setC(Integer.MIN_VALUE);
        cm.setD(Long.MIN_VALUE);
        cm.setE('e');
        cm.setF("Hello 孙来疯，this is the World!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


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
