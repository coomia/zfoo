package com.zfoo.game.gameserver.module.core.consume;

import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.player.model.Player;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:42
 */
public interface IConsume {

    boolean verify(Player player, VerifResult result);

    void consume(Player player, ReasonEnum reason);

}
