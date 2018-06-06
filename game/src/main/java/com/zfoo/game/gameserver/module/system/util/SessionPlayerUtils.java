package com.zfoo.game.gameserver.module.system.util;

import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;
import com.zfoo.net.server.model.Session;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 17:34
 */
public abstract class SessionPlayerUtils {

    public static final String PLAYER = "player";

    public static PlayerEntity getPlayer(Session session) {
        return (PlayerEntity) session.getAttributes().get(PLAYER);
    }

    public static void addPlayerToSession(PlayerEntity playerEntity, Session session) {
        session.getAttributes().put(PLAYER, playerEntity);
    }

}
