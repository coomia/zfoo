package com.zfoo.game.gameserver.module.activity.event;

import com.zfoo.event.model.event.IEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 17:09
 */
public class SyncActivityRewardEvent implements IEvent {

    private String message;

    public static SyncActivityRewardEvent valueOf(String message) {
        SyncActivityRewardEvent event = new SyncActivityRewardEvent();
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
