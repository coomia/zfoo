package com.zfoo.game.gameserver.module.activity;

import com.zfoo.game.gameserver.module.GameContext;
import com.zfoo.game.gameserver.module.activity.manager.IActivityManager;
import com.zfoo.game.gameserver.module.activity.resource.ActivityResource;
import com.zfoo.game.gameserver.module.core.condition.AndCondition;
import com.zfoo.game.gameserver.module.core.consume.AndConsume;
import com.zfoo.game.gameserver.module.core.reward.AndReward;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 17:43
 */
public class ActivityResourceTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("game.xml");
        IActivityManager activityManager = GameContext.getActivityManager();
        System.out.println(activityManager);

        ActivityResource resource = activityManager.getActivityResource(1);
        System.out.println(resource);

        AndCondition andCondition = resource.getAndCondition();
        AndConsume andConsume = resource.getAndConsume();
        AndReward andReward = resource.getAndReward();

        System.out.println(andCondition);
        System.out.println(andConsume);
        System.out.println(andReward);
    }

}
