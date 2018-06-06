package com.zfoo.game.gameserver.module.core.reward.model;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.reward.AbstractReward;
import com.zfoo.game.gameserver.module.core.reward.RewardEnum;
import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.util.StringUtils;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 09:31
 */
public class CurrencyReward extends AbstractReward {

    public static final RewardEnum REWARD_ENUM = RewardEnum.CURRENCY;

    private String type;
    private long num;

    @Override
    public void parse(String value) {
        String[] split = value.split(StringUtils.COLON_REGEX);
        if (split.length != 2) {
            throw new IllegalArgumentException("CurrencyReward配置错误：" + value);
        }
        this.type = split[0];
        this.num = Long.valueOf(split[1]);
    }

    @Override
    public boolean merge(AbstractReward reward) {
        if (this.getRewardEnum() != reward.getRewardEnum()) {
            return false;
        }

        CurrencyReward currencyReward = ((CurrencyReward) reward);
        if (!this.getType().equals(currencyReward.getType())) {
            return false;
        }

        this.num += currencyReward.getNum();
        return true;
    }

    public String getType() {
        return type;
    }

    public long getNum() {
        return num;
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
