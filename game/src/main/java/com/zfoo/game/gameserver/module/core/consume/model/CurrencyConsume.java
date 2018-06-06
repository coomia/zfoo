package com.zfoo.game.gameserver.module.core.consume.model;

import com.zfoo.game.gameserver.module.core.ReasonEnum;
import com.zfoo.game.gameserver.module.core.VerifResult;
import com.zfoo.game.gameserver.module.core.consume.AbstractConsume;
import com.zfoo.game.gameserver.module.core.consume.ConsumeEnum;
import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.util.StringUtils;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:41
 */
public class CurrencyConsume extends AbstractConsume {

    private static final ConsumeEnum CONSUME_ENUM = ConsumeEnum.CURRENCY;

    private String type;
    private long num;

    @Override
    public void parse(String value) {
        String[] split = value.split(StringUtils.COLON_REGEX);
        if (split.length != 2) {
            throw new IllegalArgumentException("CurrencyConsume配置错误：" + value);
        }
        this.type = split[0];
        this.num = Long.valueOf(split[1]);
    }

    @Override
    public ConsumeEnum getConsumeEnum() {
        return CONSUME_ENUM;
    }


    @Override
    public boolean merge(AbstractConsume consume) {
        if (this.getConsumeEnum() != consume.getConsumeEnum()) {
            return false;
        }

        CurrencyConsume currencyConsume = ((CurrencyConsume) consume);
        if (!this.getType().equals(currencyConsume.getType())) {
            return false;
        }

        this.num += currencyConsume.getNum();
        return true;
    }

    public String getType() {
        return type;
    }

    public long getNum() {
        return num;
    }

    @Override
    public boolean verify(Player player, VerifResult result) {
        return false;
    }

    @Override
    public void consume(Player player, ReasonEnum reason) {

    }
}
