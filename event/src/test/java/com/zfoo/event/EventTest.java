package com.zfoo.event;

import com.zfoo.event.test.NoticeEvent;
import com.zfoo.event.test.TestEvent;
import com.zfoo.util.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 17:52
 */
public class EventTest {

    @Test
    public void test() throws InterruptedException {
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
        System.out.println("Event测试：");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_event.xml");
        Thread.sleep(1000);
        EventContext.getEventBusManager().syncSubmit(NoticeEvent.valueOf("同步事件"));
        EventContext.getEventBusManager().asyncSubmit(NoticeEvent.valueOf("异步事件"));

        Thread.sleep(1000);
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
    }

}
