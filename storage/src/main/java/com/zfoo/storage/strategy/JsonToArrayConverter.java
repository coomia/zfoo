package com.zfoo.storage.strategy;

import com.zfoo.util.JsonUtils;
import com.zfoo.util.StringUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.20 10:06
 */
public class JsonToArrayConverter implements ConditionalGenericConverter {


    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() != String.class ? false : targetType.getType().isArray();
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Object[].class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        // String content = (String) source;
        // return targetType.isPrimitive() ? JsonUtil.string2Object(content, targetType.getObjectType())
        //         : JsonUtil.string2Array(content, targetType.getType());
        Class<?> clazz = null;

        String content = (String) source;

        String targetClazzName = targetType.getObjectType().getName();
        if (targetClazzName.contains(StringUtils.LEFT_SQUARE_BRACKET) || targetClazzName.contains(StringUtils.SEMICOLON)) {
            String clazzPath = targetClazzName.substring(2, targetClazzName.length() - 1);
            try {
                clazz = Class.forName(clazzPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            clazz = targetType.getObjectType();
        }

        return JsonUtils.string2Array(content, clazz);
    }
}
