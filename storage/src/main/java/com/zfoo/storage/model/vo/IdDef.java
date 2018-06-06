package com.zfoo.storage.model.vo;

import com.zfoo.storage.model.anno.Id;
import com.zfoo.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 14:29
 */
public class IdDef {

    private Field field;

    public static IdDef valueOf(Class<?> clazz) {
        Field[] fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(clazz, Id.class);
        if (fields.length <= 0) {
            throw new RuntimeException("类[" + clazz.getName() + "]没有主键Id注解");
        }
        if (fields.length > 1) {
            throw new RuntimeException("类[" + clazz.getName() + "]的主键Id注解重复");
        }
        if (fields[ 0 ] == null) {
            throw new IllegalArgumentException("不合法的Id资源映射对象：" + clazz.getName());
        }
        Field idField = fields[ 0 ];
        ReflectionUtils.makeAccessible(idField);
        IdDef idDef = new IdDef();
        idDef.setField(idField);
        return idDef;
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
