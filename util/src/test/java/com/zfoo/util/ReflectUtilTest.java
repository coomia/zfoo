package com.zfoo.util;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 12:29
 */

@interface Id {
}


@Ignore
public class ReflectUtilTest {

    @Test
    public void testGetFieldsByAnnotation() {
        Field[] fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(User.class, Id.class);
        System.out.println(fields.length);
    }

    @Test
    public void testFilterFieldsInClass() {
        ReflectionUtils.filterFieldsInClass(User.class, new ReflectionUtils.FieldFilter() {
            @Override
            public boolean matches(Field field) {
                return field != null;
            }
        }, new ReflectionUtils.FieldCallback() {
            @Override
            public void callBack(Field field) throws IllegalArgumentException, IllegalAccessException {
                System.out.println(field.getName());
            }
        });
    }

}
