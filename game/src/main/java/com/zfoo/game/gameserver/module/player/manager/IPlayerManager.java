package com.zfoo.game.gameserver.module.player.manager;

import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 15:37
 */
public interface IPlayerManager {

    PlayerEntity getPlayerEntityById(long id);

    PlayerEntity createPlayerEntity(long id);

    PlayerEntity insertPlayerEntity(PlayerEntity player);

}
