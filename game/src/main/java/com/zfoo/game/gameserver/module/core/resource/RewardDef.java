package com.zfoo.game.gameserver.module.core.resource;

import com.zfoo.game.gameserver.module.core.reward.RewardEnum;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 09:59
 */
public class RewardDef {

    private RewardEnum type;

    private String value;

    public RewardEnum getType() {
        return type;
    }

    public void setType(RewardEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
