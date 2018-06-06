package com.zfoo.game.gameserver.module.repository.model;

import com.zfoo.game.gameserver.module.core.reward.RewardEnum;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:05
 */
public enum RNodeEnum {


    EQUIP(RewardEnum.EQUIP, 0, RNodeStactBuilder.singleEquipBuilder(), RNodeComparatorFactory.singleEquipComparator()),

    CURRENCY(RewardEnum.CURRENCY, 1, RNodeStactBuilder.singleCurrencyBuilder(), RNodeComparatorFactory.singleCurrencyComparator());

    // 奖励
    private RewardEnum rewardEnum;
    // 节点类型
    private int nodeType;
    // 节点堆叠规则
    private IRNodeStactBuilder builder;
    // 排序比较器
    private Comparator<? extends RNode> comparator;

    private static Map<Integer, RNodeEnum> nodeEnumMap = new HashMap<>();

    RNodeEnum(RewardEnum rewardEnum, int nodeType, IRNodeStactBuilder builder, Comparator<? extends RNode> comparator) {
        this.rewardEnum = rewardEnum;
        this.nodeType = nodeType;
        this.builder = builder;
        this.comparator = comparator;
    }

    static {
        for (RNodeEnum nodeEnum : RNodeEnum.values()) {
            if (nodeEnumMap.containsKey(nodeEnum.getNodeType())) {
                throw new IllegalArgumentException("仓库的节点类型不能重复：" + nodeEnum);
            }
            nodeEnumMap.put(nodeEnum.getNodeType(), nodeEnum);
        }
    }

    public static RNodeEnum getRnodeEnum(int nodeType) {
        RNodeEnum nodeEnum = nodeEnumMap.get(nodeType);
        if (nodeEnum == null) {
            throw new IllegalArgumentException("没有节点类型nodeType：" + nodeEnum);
        }
        return nodeEnum;
    }

    public RewardEnum rewardEnum() {
        return rewardEnum;
    }

    public int getNodeType() {
        return nodeType;
    }

    public IRNodeStactBuilder builder() {
        return builder;
    }

    public Comparator<? extends RNode> comparator() {
        return comparator;
    }

    @Override
    public String toString() {
        return "RNodeEnum{" +
                "rewardEnum=" + rewardEnum +
                ", nodeType=" + nodeType +
                ", builder=" + builder +
                ", comparator=" + comparator +
                '}';
    }
}
