package com.zfoo.game.gameserver.module.core.consume;

import com.zfoo.game.gameserver.module.core.consume.model.CurrencyConsume;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:42
 */
public enum ConsumeEnum {

    CURRENCY(CurrencyConsume.class);

    private Class<? extends AbstractConsume> clazz;

    ConsumeEnum(Class<? extends AbstractConsume> clazz) {
        this.clazz = clazz;
    }

    public <T extends AbstractConsume> T createConsume() {
        AbstractConsume consume = null;
        try {
            consume = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) consume;
    }
}
