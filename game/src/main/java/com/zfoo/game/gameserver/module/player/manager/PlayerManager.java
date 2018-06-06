package com.zfoo.game.gameserver.module.player.manager;

import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;
import com.zfoo.orm.model.anno.OrmInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 15:37
 */
@Component
public class PlayerManager implements IPlayerManager {

    @OrmInjection
    private IEntityCaches<Long, PlayerEntity> entityCaches;


    @Override
    public PlayerEntity getPlayerEntityById(long id) {
        return entityCaches.load(id);
    }

    @Override
    public PlayerEntity createPlayerEntity(long id) {
        PlayerEntity player = new PlayerEntity(id);
        player.setAge(50);
        player.setLoginNum((short) 0);
        player.setMan(true);
        player.setName("jaysunxiao");
        return player;
    }

    @Override
    public PlayerEntity insertPlayerEntity(PlayerEntity player) {
        return entityCaches.insert(player);
    }


}
