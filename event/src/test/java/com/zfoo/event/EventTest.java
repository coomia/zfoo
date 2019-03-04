package com.zfoo.event;

import org.junit.Ignore;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 17:52
 */
@Ignore
public class EventTest {

    // Event测试
    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_event.xml");
        Thread.sleep(1000);

        EventContext.getEventBusManager().syncSubmit(NoticeEvent.valueOf("同步事件"));
        EventContext.getEventBusManager().asyncSubmit(NoticeEvent.valueOf("异步事件"));

        Thread.sleep(1000);
    }

}
