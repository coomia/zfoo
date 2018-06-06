package com.zfoo.game.gameserver.module.core.reward;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:08
 */
public interface IReward {

    boolean verify(Player player,VerifResult result);

    void reward(Player player, ReasonEnum reason);

}
