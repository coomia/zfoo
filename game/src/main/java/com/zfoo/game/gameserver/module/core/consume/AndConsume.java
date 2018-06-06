package com.zfoo.game.gameserver.module.core.consume;

import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.resource.ConsumeDef;
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
public class AndConsume {


    // map中的list初始化大小
    private static final int DEFAULT_CAPACITY = 1;

    private Map<ConsumeEnum, List<AbstractConsume>> map = new EnumMap<>(ConsumeEnum.class);


    public boolean verify(Player player) {
        if (map.isEmpty()) {
            return true;
        }

        VerifResult verifResult = new VerifResult();
        for (Map.Entry<ConsumeEnum, List<AbstractConsume>> entry : map.entrySet()) {
            for (AbstractConsume consume : entry.getValue()) {
                consume.verify(player, verifResult);

                if (!verifResult.isSuccess()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void consume(Player player, ReasonEnum reason) {
        if (map.isEmpty()) {
            return;
        }

        for (Map.Entry<ConsumeEnum, List<AbstractConsume>> entry : map.entrySet()) {
            for (AbstractConsume consume : entry.getValue()) {
                consume.consume(player, reason);
            }
        }
    }

    public void addConsume(AbstractConsume consume) {
        if (!map.containsKey(consume.getConsumeEnum())) {
            map.put(consume.getConsumeEnum(), new ArrayList<>(DEFAULT_CAPACITY));
        }

        List<AbstractConsume> list = map.get(consume.getConsumeEnum());

        boolean mergeSuccessful = false;
        for (AbstractConsume abstractConsume : list) {
            mergeSuccessful = abstractConsume.merge(consume);
            if (mergeSuccessful) {
                break;
            }
        }

        if (!mergeSuccessful) {
            list.add(consume);
        }
    }

    public void addAndConsume(AndConsume andConsume) {
        for (Map.Entry<ConsumeEnum, List<AbstractConsume>> entry : map.entrySet()) {
            for (AbstractConsume consume : entry.getValue()) {
                addConsume(consume);
            }
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public static AndConsume valueOf(ConsumeDef[] consumeDefs) {
        AndConsume andConsume = new AndConsume();
        if (!CollectionUtils.isEmpty(consumeDefs)) {
            for (ConsumeDef consumeDef : consumeDefs) {
                ConsumeEnum consumeEnum = consumeDef.getType();
                AbstractConsume consume = consumeEnum.createConsume();
                consume.parse(consumeDef.getValue());
                andConsume.addConsume(consume);
            }
        }
        return andConsume;
    }
}
