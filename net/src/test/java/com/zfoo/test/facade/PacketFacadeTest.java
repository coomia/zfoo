package com.zfoo.test.facade;

import com.zfoo.net.dispatcher.model.anno.PacketReceiver;
import com.zfoo.net.server.model.Session;
import com.zfoo.test.CM_Int;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 14:42
 */
@Component
public class PacketFacadeTest {

    @PacketReceiver
    public void testCMInt(Session session, CM_Int cm) {
        System.out.println("hello the whole world!");
        System.out.println(cm.toString());
    }

}
