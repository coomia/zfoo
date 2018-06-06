package com.zfoo.storage.strategy;

import com.zfoo.util.JsonUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.20 11:32
 */
public class StringToMapConverter implements Converter<String, Map<String, Object>> {
    @Override
    public Map<String, Object> convert(String source) {
        return JsonUtils.string2Map(source, String.class, Object.class);
    }
}
