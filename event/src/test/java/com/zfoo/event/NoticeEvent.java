package com.zfoo.event;

import com.zfoo.event.model.event.IEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 17:48
 */
public class NoticeEvent implements IEvent {

    private String message;

    public static NoticeEvent valueOf(String message) {
        NoticeEvent event = new NoticeEvent();
        event.setMessage(message);
        return event;
    }

    @Override
    public int threadId() {
        return 9999;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
