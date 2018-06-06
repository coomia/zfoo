package com.zfoo.game.gameserver.module.core.resource;

import com.zfoo.game.gameserver.module.core.condition.ConditionEnum;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 09:58
 */
public class ConditionDef {

    private ConditionEnum type;

    private String value;

    public ConditionEnum getType() {
        return type;
    }

    public void setType(ConditionEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
