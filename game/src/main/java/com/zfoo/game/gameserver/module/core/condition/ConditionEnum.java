package com.zfoo.game.gameserver.module.core.condition;

import com.zfoo.game.gameserver.module.core.condition.model.PlayerLevelCondition;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:00
 */
public enum ConditionEnum {

    PLAYER_LEVEL(PlayerLevelCondition.class);

    private Class<? extends AbstractCondition> clazz;

    ConditionEnum(Class<? extends AbstractCondition> clazz) {
        this.clazz = clazz;
    }

    public <T extends AbstractCondition> T createCondition() {
        AbstractCondition condition = null;

        try {
            condition = clazz.newInstance();

            if (!condition.getConditionEnum().equals(this)) {
                FormattingTuple message = MessageFormatter.format("创建的[condition:{}]不符合ConditionEnum的类型[conditionEnum:{}]", condition, this);
                throw new IllegalArgumentException(message.getMessage());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) condition;
    }

}
