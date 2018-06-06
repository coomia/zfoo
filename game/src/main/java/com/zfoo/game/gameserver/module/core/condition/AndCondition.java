package com.zfoo.game.gameserver.module.core.condition;

import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.resource.ConditionDef;
import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.util.collection.CollectionUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 14:09
 */
public class AndCondition {

    // map中的list初始化大小
    private static final int DEFAULT_CAPACITY = 1;

    private Map<ConditionEnum, List<AbstractCondition>> map = new EnumMap<>(ConditionEnum.class);


    public boolean verify(Player player) {
        if (map.isEmpty()) {
            return true;
        }

        VerifResult verifResult = new VerifResult();
        for (Map.Entry<ConditionEnum, List<AbstractCondition>> entry : map.entrySet()) {
            for (AbstractCondition condition : entry.getValue()) {
                condition.verify(player, verifResult);

                if (!verifResult.isSuccess()) {
                    return false;
                }
            }
        }

        return true;
    }


    public void addCondition(AbstractCondition condition) {
        if (!map.containsKey(condition.getConditionEnum())) {
            map.put(condition.getConditionEnum(), new ArrayList<>(DEFAULT_CAPACITY));
        }

        List<AbstractCondition> list = map.get(condition.getConditionEnum());

        list.add(condition);
    }

    public void addAndCondition(AndCondition andCondition) {
        for (Map.Entry<ConditionEnum, List<AbstractCondition>> entry : map.entrySet()) {
            for (AbstractCondition condition : entry.getValue()) {
                addCondition(condition);
            }
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }


    public static AndCondition valueOf(ConditionDef[] conditionDefs) {
        AndCondition andCondition = new AndCondition();
        if (!CollectionUtils.isEmpty(conditionDefs)) {
            for (ConditionDef conditionDef : conditionDefs) {
                ConditionEnum conditionEnum = conditionDef.getType();
                AbstractCondition condition = conditionEnum.createCondition();
                condition.parse(conditionDef.getValue());
                andCondition.addCondition(condition);
            }
        }
        return andCondition;
    }

}
