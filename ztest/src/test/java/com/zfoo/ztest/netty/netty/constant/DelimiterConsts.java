package com.zfoo.ztest.netty.netty.constant;

/**
 * 分隔符
 *
 * @author SunInsanity
 * @version 1.0
 * @since 2017 05.22 18:23
 */
public enum DelimiterConsts {

    SEPARATOR("\n"),

    DOLLAR("$"),

    UNDERLINE("_");

    private String value;

    DelimiterConsts(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
