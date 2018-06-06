package com.zfoo.net.server;

import com.zfoo.net.NetContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 16:49
 */
public class ServerTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        NetContext.getServer().connect();
    }

}
