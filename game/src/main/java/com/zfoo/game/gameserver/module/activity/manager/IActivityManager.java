package com.zfoo.game.gameserver.module.activity.manager;

import com.zfoo.game.gameserver.module.activity.resource.ActivityResource;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 13:36
 */
public interface IActivityManager {

    ActivityResource getActivityResource(int id);

}
