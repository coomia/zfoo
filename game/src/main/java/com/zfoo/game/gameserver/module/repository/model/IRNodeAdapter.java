package com.zfoo.game.gameserver.module.repository.model;

import com.zfoo.game.gameserver.module.core.reward.AndReward;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 10:03
 */
public interface IRNodeAdapter {

    List<? extends RNode> andReward2Nodes(AndReward andReward);

    AndReward nodes2AndReward(List<? extends RNode> nodes);

}
