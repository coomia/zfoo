package com.zfoo.game.gameserver.module.core.reward;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:22
 */
public abstract class AbstractReward implements IReward {


    public abstract void parse(String value);

    public abstract boolean merge(AbstractReward reward);

    public abstract RewardEnum getRewardEnum();

    //    @Override
//    public void verifyThrow(Player player) {
//        VerifResult result = new VerifResult();
//        verify(player);
//        if (!result.isSuccess()) {
//            throw new IllegalArgumentException("sds");
//        }
//    }
}
