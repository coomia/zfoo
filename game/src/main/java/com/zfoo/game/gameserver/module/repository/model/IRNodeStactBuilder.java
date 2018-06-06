package com.zfoo.game.gameserver.module.repository.model;

import java.util.List;

/**
 * 节点堆叠规则，建造者模式
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 18:16
 */
public interface IRNodeStactBuilder {

    /**
     * 将list的节点堆叠成一个新的list，必须保证两个list的Rnode类型一样和build出来的对象需要深克隆
     *
     * @param list 需要堆叠的节点
     * @return 堆叠后的节点
     */
    List<? extends RNode> build(List<? extends RNode> list);

    /**
     * 返回一个节点的最大堆叠上限
     *
     * @param node 节点
     * @return 堆叠上限
     */
    int maxStackNum(RNode node);

}
