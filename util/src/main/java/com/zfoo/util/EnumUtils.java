package com.zfoo.util;

import java.util.Arrays;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-16 18:08
 */
public abstract class EnumUtils {

    public static <E extends Enum> boolean isInEnums(String targetEnumName, E[] sourceEnums) {
        AssertionUtils.notNull(targetEnumName, sourceEnums);
        return Arrays.stream(sourceEnums).anyMatch(sourceEnum -> sourceEnum.name().equals(targetEnumName));
    }

}
