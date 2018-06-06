package com.zfoo.game.gameserver.module.core.reward;

import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.resource.RewardDef;
import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.util.collection.CollectionUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 10:04
 */
public class AndReward {

    // map中的list初始化大小
    private static final int DEFAULT_CAPACITY = 1;

    private Map<RewardEnum, List<AbstractReward>> map = new EnumMap<>(RewardEnum.class);


    public boolean verify(Player player) {
        if (map.isEmpty()) {
            return true;
        }

        VerifResult verifResult = new VerifResult();
        for (Map.Entry<RewardEnum, List<AbstractReward>> entry : map.entrySet()) {
            for (AbstractReward reward : entry.getValue()) {
                reward.verify(player, verifResult);

                if (!verifResult.isSuccess()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void reward(Player player, ReasonEnum reason) {
        if (map.isEmpty()) {
            return;
        }

        for (Map.Entry<RewardEnum, List<AbstractReward>> entry : map.entrySet()) {
            for (AbstractReward reward : entry.getValue()) {
                reward.reward(player, reason);
            }
        }
    }

    public void addReward(AbstractReward reward) {
        if (!map.containsKey(reward.getRewardEnum())) {
            map.put(reward.getRewardEnum(), new ArrayList<>(DEFAULT_CAPACITY));
        }

        List<AbstractReward> list = map.get(reward.getRewardEnum());

        boolean mergeSuccessful = false;
        for (AbstractReward abstractReward : list) {
            mergeSuccessful = abstractReward.merge(reward);
            if (mergeSuccessful) {
                break;
            }
        }

        if (!mergeSuccessful) {
            list.add(reward);
        }
    }

    public void addAndReward(AndReward andReward) {
        for (Map.Entry<RewardEnum, List<AbstractReward>> entry : map.entrySet()) {
            for (AbstractReward reward : entry.getValue()) {
                addReward(reward);
            }
        }
    }

    public static AndReward valueOf(RewardDef[] rewardDefs) {
        AndReward andReward = new AndReward();
        if (!CollectionUtils.isEmpty(rewardDefs)) {
            for (RewardDef rewardDef : rewardDefs) {
                RewardEnum rewardEnum = rewardDef.getType();
                AbstractReward reward = rewardEnum.createReward();
                reward.parse(rewardDef.getValue());
                andReward.addReward(reward);
            }
        }
        return andReward;
    }
}
