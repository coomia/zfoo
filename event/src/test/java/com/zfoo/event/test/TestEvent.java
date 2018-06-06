package com.zfoo.event.test;

import com.zfoo.event.model.anno.EventReceiver;
import org.springframework.stereotype.Component;


/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 17:48
 */
@Component
public class TestEvent {


    @EventReceiver
    public void testEvent(NoticeEvent event) {
        System.out.println("thread:" + Thread.currentThread().getName() + ",message:" + event.getMessage());
    }

}
