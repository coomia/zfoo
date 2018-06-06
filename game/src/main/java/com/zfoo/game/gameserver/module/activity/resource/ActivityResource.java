package com.zfoo.game.gameserver.module.activity.resource;

import com.zfoo.game.gameserver.module.core.condition.AndCondition;
import com.zfoo.game.gameserver.module.core.consume.AndConsume;
import com.zfoo.game.gameserver.module.core.resource.ConditionDef;
import com.zfoo.game.gameserver.module.core.resource.ConsumeDef;
import com.zfoo.game.gameserver.module.core.resource.RewardDef;
import com.zfoo.game.gameserver.module.core.reward.AndReward;
import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.model.anno.Resource;

import javax.persistence.Transient;


/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 09:43
 */
@Resource
public class ActivityResource {

    @Id
    private int id;

    private String[] times;// ["开始时间|结束时间"]，全部遵循cron表达式

    private ConditionDef[] conditionDefs;
    private ConsumeDef[] consumeDefs;
    private RewardDef[] rewardDefs;

    @Transient
    private AndCondition andCondition;
    @Transient
    private AndConsume andConsume;
    @Transient
    private AndReward andReward;

    public AndCondition getAndCondition() {
        if (andCondition == null) {
            andCondition = AndCondition.valueOf(conditionDefs);
        }
        return andCondition;
    }

    public AndConsume getAndConsume() {
        if (andConsume == null) {
            andConsume = AndConsume.valueOf(consumeDefs);
        }
        return andConsume;
    }

    public AndReward getAndReward() {
        if (andReward == null) {
            andReward = AndReward.valueOf(rewardDefs);
        }
        return andReward;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public ConditionDef[] getConditionDefs() {
        return conditionDefs;
    }

    public void setConditionDefs(ConditionDef[] conditionDefs) {
        this.conditionDefs = conditionDefs;
    }

    public ConsumeDef[] getConsumeDefs() {
        return consumeDefs;
    }

    public void setConsumeDefs(ConsumeDef[] consumeDefs) {
        this.consumeDefs = consumeDefs;
    }

    public RewardDef[] getRewardDefs() {
        return rewardDefs;
    }

    public void setRewardDefs(RewardDef[] rewardDefs) {
        this.rewardDefs = rewardDefs;
    }
}
