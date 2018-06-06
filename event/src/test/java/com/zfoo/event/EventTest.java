package com.zfoo.event;

import com.zfoo.event.test.NoticeEvent;
import com.zfoo.event.test.TestEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 17:52
 */
public class EventTest {

    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_event.xml");
        Thread.sleep(1000);
        EventContext.getEventBusManager().syncSubmit(NoticeEvent.valueOf("同步事件"));

        System.out.println("******************************************************");

        EventContext.getEventBusManager().asyncSubmit(NoticeEvent.valueOf("异步事件"));
    }

}
