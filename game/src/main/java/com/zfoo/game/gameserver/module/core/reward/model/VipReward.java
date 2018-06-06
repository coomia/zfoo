package com.zfoo.game.gameserver.module.core.reward.model;

import com.zfoo.game.gameserver.module.core.ErrorCodeEnum;
import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.reward.AbstractReward;
import com.zfoo.game.gameserver.module.core.reward.RewardEnum;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:27
 */
public class VipReward extends AbstractReward {

    public static final RewardEnum REWARD_ENUM = RewardEnum.VIP;

    private int vipLevel;

    @Override
    public void parse(String value) {
        this.vipLevel = Integer.valueOf(value);
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
        if (vipLevel <= 0) {
            result.fail();
            result.setErrorCode(ErrorCodeEnum.PLAYER_LEVEL_IS_TOO_LOW);
            return false;
        }

        if (vipLevel > 15) {
            result.fail();
            result.setErrorCode(ErrorCodeEnum.PLAYER_LEVEL_IS_TOO_HIGH);
            return false;
        }

        return true;
    }

    @Override
    public void reward(Player player, ReasonEnum reason) {
        // 开始奖励
        System.out.println("vipReward");
    }
}
