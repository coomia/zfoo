package com.zfoo.game.gameserver.module.activity.facade;

import com.zfoo.event.EventContext;
import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.game.gameserver.module.activity.event.SyncActivityRewardEvent;
import com.zfoo.game.gameserver.module.activity.packet.CM_ActivityInfo;
import com.zfoo.net.dispatcher.model.anno.PacketReceiver;
import com.zfoo.net.server.model.Session;
import com.zfoo.scheduler.model.anno.Scheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 17:09
 */
@Component
public class ActivityFacade {

    @PacketReceiver
    public void reqActivityInfo(Session session, CM_ActivityInfo cm) {
        System.out.println("收到客户端请求：");
        System.out.println("收到客户端请求，打印客户端消息：" + cm.getA());

        EventContext.getEventBusManager().syncSubmit(SyncActivityRewardEvent.valueOf("同步事件"));
        EventContext.getEventBusManager().asyncSubmit(SyncActivityRewardEvent.valueOf("异步事件"));
    }


    @EventReceiver
    public void syncActivityReward(SyncActivityRewardEvent event) {
        System.out.println(event.getMessage());
    }

    @Scheduler(value = "testScheduler", cronExpression = "0,10,20,40,55 * * * * ?")
    public void scheduler() {
        System.out.println("testScheduler:" + new Date());
    }

}
