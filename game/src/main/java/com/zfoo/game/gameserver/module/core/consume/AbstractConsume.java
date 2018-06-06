package com.zfoo.game.gameserver.module.core.consume;

import com.zfoo.game.gameserver.module.core.reward.AbstractReward;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:42
 */
public abstract class AbstractConsume implements IConsume {

    public abstract void parse(String value);

    public abstract ConsumeEnum getConsumeEnum();

    public abstract boolean merge(AbstractConsume consume);

}
