package com.zfoo.orm.model.vo;

import com.zfoo.orm.model.anno.UniqueIndex;
import com.zfoo.util.AssertionUtils;
import com.zfoo.util.ReflectionUtils;
import com.zfoo.util.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-04 16:59
 */
public class UniqueIndexDef {

    private Field field;

    private String namedQuery;

    public UniqueIndexDef(Field field, UniqueIndex uniqueIndex) {
        AssertionUtils.notNull(field);

        // 是否被private修饰
        if (!Modifier.isPrivate(field.getModifiers())) {
            FormattingTuple message = MessageFormatter.format("[{}]没有被private修饰", field.getName());
            throw new IllegalArgumentException(message.getMessage());
        }

        // 唯一索引不能有set方法，为了避免客户端改变javabean中的属性
        Class<?> clazz = field.getDeclaringClass();
        AssertionUtils.notNull(clazz);
        String setMethodName = "set" + StringUtils.capitalize(field.getName());
        Method[] setMethods = ReflectionUtils.getMethodsByNameInPOJOClass(clazz, setMethodName);
        if (setMethods.length > 0) {
            FormattingTuple message = MessageFormatter.arrayFormat("类[class:{}]的成员变量[field:{}]不能有set方法[set:{}]"
                    , new Object[]{clazz.getName(), field.getName(), setMethodName});
            throw new IllegalArgumentException(message.getMessage());
        }
        this.field = field;
        this.namedQuery = uniqueIndex.namedQuery();
        ReflectionUtils.makeAccessible(field);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getNamedQuery() {
        return namedQuery;
    }

    public void setNamedQuery(String namedQuery) {
        this.namedQuery = namedQuery;
    }
}
