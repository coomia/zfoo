package com.zfoo.game.gameserver.module.core.reward.model;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.reward.AbstractReward;
import com.zfoo.game.gameserver.module.core.reward.RewardEnum;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 18:14
 */
public class EquipReward extends AbstractReward {

    public static final RewardEnum REWARD_ENUM = RewardEnum.EQUIP;

    @Override
    public void parse(String value) {

    }

    @Override
    public boolean merge(AbstractReward reward) {
        return false;
    }

    @Override
    public RewardEnum getRewardEnum() {
        return REWARD_ENUM;
    }

    @Override
    public boolean verify(Player player, VerifResult result) {
        return false;
    }

    @Override
    public void reward(Player player, ReasonEnum reason) {

    }
}
