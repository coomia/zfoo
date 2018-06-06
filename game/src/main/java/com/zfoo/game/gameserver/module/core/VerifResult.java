package com.zfoo.game.gameserver.module.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:16
 */
public class VerifResult {

    private boolean success = true;

    private ErrorCodeEnum errorCode;

    private Map<Object, Object> map;

    public void success() {
        success = true;
    }

    public void fail() {
        success = false;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public Map<Object, Object> getMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    public void setMap(Map<Object, Object> map) {
        this.map = map;
    }
}
