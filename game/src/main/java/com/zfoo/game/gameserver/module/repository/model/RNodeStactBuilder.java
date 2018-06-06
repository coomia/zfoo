package com.zfoo.game.gameserver.module.repository.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 18:25
 */
public final class RNodeStactBuilder {

    private RNodeStactBuilder() {
    }

    // ------------------------------------------------装备堆叠规则------------------------------------------------

    private static final EquipBuilder EQUIP_BUILDER = new EquipBuilder();

    public static EquipBuilder singleEquipBuilder() {
        return EQUIP_BUILDER;
    }


    private static final class EquipBuilder implements IRNodeStactBuilder {

        @Override
        public List<? extends RNode> build(List<? extends RNode> list) {

            return new ArrayList<>();
        }

        @Override
        public int maxStackNum(RNode node) {
            return Integer.MAX_VALUE;
        }
    }

    // ------------------------------------------------货币堆叠规则------------------------------------------------

    private static final CurrencyBuilder CURRENCY_BUILDER = new CurrencyBuilder();

    public static CurrencyBuilder singleCurrencyBuilder() {
        return CURRENCY_BUILDER;
    }

    private static final class CurrencyBuilder implements IRNodeStactBuilder {

        @Override
        public List<? extends RNode> build(List<? extends RNode> list) {
            return new ArrayList<>();
        }

        @Override
        public int maxStackNum(RNode node) {
            return Integer.MAX_VALUE;
        }
    }
}
