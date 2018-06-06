package com.zfoo.game.gameserver.module.repository.model;

import java.util.Comparator;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 18:29
 */
public final class RNodeComparatorFactory {

    // ------------------------------------------------装备排序规则------------------------------------------------
    private static final EquipComparator EQUIP_COMPARATOR = new EquipComparator();

    public static Comparator<? extends RNode> singleEquipComparator() {
        return EQUIP_COMPARATOR;
    }

    private static final class EquipComparator implements Comparator<RNode> {

        @Override
        public int compare(RNode a, RNode b) {
            if (a.getType() != b.getType()) {
                throw new IllegalArgumentException("不是同一种类型的RNode不能排序");
            }

            if (a == b) {
                return 0;
            }

            return 0;
        }
    }

    // ------------------------------------------------装备排序规则------------------------------------------------
    private static final CurrencyComparator CURRENCY_COMPARATOR = new CurrencyComparator();

    public static Comparator<? extends RNode> singleCurrencyComparator() {
        return CURRENCY_COMPARATOR;
    }

    private static final class CurrencyComparator implements Comparator<RNode> {
        @Override
        public int compare(RNode a, RNode b) {
            return 0;
        }
    }

}
