package com.zfoo.game.gameserver;

import com.zfoo.net.NetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 17:42
 */
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("game.xml");
        NetContext.getServer().connect();
        logger.info("message:info info ............................");
        logger.debug("message:debug debug ............................");
    }

}
