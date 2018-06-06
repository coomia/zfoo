package com.zfoo.game.gameserver.module.core.resource;

import com.zfoo.game.gameserver.module.core.consume.ConsumeEnum;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 09:59
 */
public class ConsumeDef {

    private ConsumeEnum type;

    private String value;

    public ConsumeEnum getType() {
        return type;
    }

    public void setType(ConsumeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
