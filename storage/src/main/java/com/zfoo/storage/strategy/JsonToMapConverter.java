package com.zfoo.storage.strategy;

import com.zfoo.util.JsonUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.20 10:32
 */
public class JsonToMapConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() != String.class ? false : Map.class.isAssignableFrom(targetType.getType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Map.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String content = (String) source;
        return JsonUtils.string2Object(content,targetType.getType());
        // return JsonUtil.string2Map(content, targetType.getMapKeyTypeDescriptor().getType()
        //         , targetType.getMapValueTypeDescriptor().getType());
    }
}
