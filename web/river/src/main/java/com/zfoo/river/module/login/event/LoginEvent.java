package com.zfoo.river.module.login.event;

import com.zfoo.event.model.event.IEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/9
 */
public class LoginEvent implements IEvent {

    private String name;
    private String password;

    public static LoginEvent valueOf(String name, String password) {
        LoginEvent event = new LoginEvent();
        event.name = name;
        event.password = password;
        return event;
    }

    @Override
    public int threadId() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
