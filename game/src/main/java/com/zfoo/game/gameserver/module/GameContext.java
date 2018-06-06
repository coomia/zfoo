package com.zfoo.game.gameserver.module;

import com.zfoo.game.gameserver.module.activity.manager.IActivityManager;
import com.zfoo.game.gameserver.module.activity.service.IActivityService;
import com.zfoo.game.gameserver.module.player.manager.IPlayerManager;
import com.zfoo.game.gameserver.module.player.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 13:38
 */
@Component
public class GameContext {

    @Autowired
    private IActivityManager activityManager;

    public static IActivityManager getActivityManager() {
        return instance.activityManager;
    }

    @Autowired
    private IActivityService activityService;

    public static IActivityService getActivityService() {
        return instance.activityService;
    }

    @Autowired
    private IPlayerManager playerManager;

    public static IPlayerManager getPlayerManager() {
        return instance.playerManager;
    }

    @Autowired
    private IPlayerService playerService;

    public static IPlayerService getPlayerService() {
        return instance.playerService;
    }

    // ----------------------------------------------------------------------------------------------------
    private static GameContext instance;

    @PostConstruct
    private void init() {
        GameContext.instance = this;
    }

    public static GameContext getInstance() {
        return instance;
    }
}
