package com.zfoo.game.gameserver.module.core;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 10:26
 */
public enum ErrorCodeEnum {

    // 出现异常时的错误码
    SYS_ERROR(Integer.MAX_VALUE),

    // 非法请求，所有不合理的请求都返回这个错误码
    INVALID_PARA(9999),

    PLAYER_LEVEL_IS_TOO_LOW(1),

    PLAYER_LEVEL_IS_TOO_HIGH(2);

    private int errorCode;

    ErrorCodeEnum(int errorCode) {
        this.errorCode = errorCode;
    }
}
