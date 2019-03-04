package com.zfoo.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-16 20:01
 */

enum Num {
    ONE,
    TWO,
    THREE;
}

public class EnumUtilsTest {

    @Test
    public void isInEnumsTest() {
        Assert.assertTrue(EnumUtils.isInEnums("ONE", new Num[]{Num.ONE, Num.TWO, Num.THREE}));
    }

}
