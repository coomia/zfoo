package com.zfoo.game.gameserver.module.core.condition;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:03
 */
public abstract class AbstractCondition implements ICondition {

    public abstract ConditionEnum getConditionEnum();

    public abstract void parse(String value);

}
