package com.zfoo.game.gameserver.module.ai;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 14:04
 */
public interface IAiAction {

    void onEnter(Ai ai);

    void onClear(Ai ai);

}
