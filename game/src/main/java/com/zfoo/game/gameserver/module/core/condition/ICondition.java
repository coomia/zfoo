package com.zfoo.game.gameserver.module.core.condition;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:02
 */
public interface ICondition {

    boolean verify(Player player, VerifResult result);

}
