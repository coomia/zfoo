package com.zfoo.game.gameserver.module.core.reward;

import com.zfoo.game.gameserver.module.core.reward.model.CurrencyReward;
import com.zfoo.game.gameserver.module.core.reward.model.EquipReward;
import com.zfoo.game.gameserver.module.core.reward.model.VipReward;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:06
 */
public enum RewardEnum {

    EQUIP(EquipReward.class),

    CURRENCY(CurrencyReward.class),

    VIP(VipReward.class);

    private Class<? extends AbstractReward> clazz;

    RewardEnum(Class<? extends AbstractReward> clazz) {
        this.clazz = clazz;
    }

    public <T extends AbstractReward> T createReward() {
        AbstractReward reward = null;
        try {
            reward = clazz.newInstance();

            if (!reward.getRewardEnum().equals(this)) {
                FormattingTuple message = MessageFormatter.format("创建的[reward:{}]不符合RewardEnum的类型[rewardEnum:{}]", reward,this);
                throw new IllegalArgumentException(message.getMessage());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) reward;
    }

}
