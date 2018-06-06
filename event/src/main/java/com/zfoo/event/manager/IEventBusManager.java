package com.zfoo.event.manager;

import com.zfoo.event.model.event.IEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 10:43
 */
public interface IEventBusManager {

    void syncSubmit(IEvent event);

    // 异步提交事件，事件不在同一个线程中处理
    void asyncSubmit(IEvent event);

}
