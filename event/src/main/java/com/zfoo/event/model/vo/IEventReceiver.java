package com.zfoo.event.model.vo;

import com.zfoo.event.model.event.IEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.18 11:03
 */
public interface IEventReceiver {

    void invoke(IEvent event);

}
