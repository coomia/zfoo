package com.zfoo.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.21 18:19
 */
@Ignore
public class ClassUtilTest {

    // ClassUtilTest
    @Test
    public void classLocation() throws Exception {
        String str = ClassUtils.classLocation(Integer.class);
        Assert.assertEquals("jrt:/java.base/java/lang/Integer.class", str);
    }

    @Test
    public void getAllClasses() throws Exception {
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
        System.out.println("某个包下的所有类查找测试：");
        Set<Class<?>> set = ClassUtils.getAllClasses("com.zfoo");
        for (Class<?> clazz : set) {
            System.out.println(clazz.getName());
        }
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
    }

}
