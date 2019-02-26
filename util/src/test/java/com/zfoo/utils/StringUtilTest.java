package com.zfoo.utils;

import com.zfoo.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 17:13
 */
public class StringUtilTest {

    @Test
    public void isEmpty() {
        Assert.assertEquals(StringUtils.isEmpty("  "), false);
    }

    @Test
    public void capitalize() {
        String str = "hello world!";
        Assert.assertEquals(StringUtils.capitalize(str), "Hello world!");
    }

    @Test
    public void unCapitalize() {
        String str = "Hello world!";
        Assert.assertEquals(StringUtils.uncapitalize(str), "hello world!");
    }

}
