package com.zfoo.web.river.event;

import com.zfoo.event.model.event.IEvent;

public class HelloEvent implements IEvent {

    private String msg;

    public static HelloEvent valueOf(String msg) {
        HelloEvent event = new HelloEvent();
        event.msg = msg;
        return event;
    }

    @Override
    public int threadId() {
        return 0;
    }

    public String getMsg() {
        return msg;
    }
}
