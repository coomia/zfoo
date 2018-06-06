package com.zfoo.game.gameserver.module.core.condition.model;

import com.zfoo.game.gameserver.module.core.ErrorCodeEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.condition.AbstractCondition;
import com.zfoo.game.gameserver.module.core.condition.ConditionEnum;
import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.util.StringUtils;

/**
 * 包含关系，[minLevel,maxLevel]
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:01
 */
public class PlayerLevelCondition extends AbstractCondition {

    private static final ConditionEnum CONDITION_ENUM = ConditionEnum.PLAYER_LEVEL;

    private int minLevel;
    private int maxLevel;


    @Override
    public ConditionEnum getConditionEnum() {
        return CONDITION_ENUM;
    }

    @Override
    public void parse(String value) {
        String[] split = value.split(StringUtils.COLON_REGEX);
        if (split.length != 2) {
            throw new IllegalArgumentException("PlayerLevelCondition配置错误：" + value);
        }
        this.minLevel = Integer.valueOf(split[0]);
        this.maxLevel = Integer.valueOf(split[1]);
    }

    @Override
    public boolean verify(Player player, VerifResult result) {
//        if (player.getLevel() < minLevel) {
//            result.fail();
//            result.setErrorCode(ErrorCodeEnum.PLAYER_LEVEL_IS_TOO_LOW);
//            return false;
//        }
//
//        if (player.getLevel() > maxLevel) {
//            result.fail();
//            result.setErrorCode(ErrorCodeEnum.PLAYER_LEVEL_IS_TOO_HIGH);
//            return false;
//        }
        return true;
    }
}
