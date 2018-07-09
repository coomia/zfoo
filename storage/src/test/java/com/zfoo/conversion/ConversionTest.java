package com.zfoo.conversion;

import com.zfoo.storage.strategy.JsonToArrayConverter;
import com.zfoo.storage.strategy.JsonToMapConverter;
import com.zfoo.storage.strategy.StringToClassConverter;
import com.zfoo.storage.strategy.StringToDateConverter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.18 17:26
 */
public class ConversionTest {
    private static final ConversionServiceFactoryBean csfb = new ConversionServiceFactoryBean();
    private static final Set<Object> converters = new HashSet<>();

    private static final StringToDateConverter std = new StringToDateConverter();
    private static final StringToClassConverter stcc = new StringToClassConverter();
    private static final JsonToMapConverter jtmc = new JsonToMapConverter();
    private static final JsonToArrayConverter jtac = new JsonToArrayConverter();

    static {
        converters.add(std);
        converters.add(stcc);
        converters.add(jtmc);
        converters.add(jtac);
        csfb.setConverters(converters);
        csfb.afterPropertiesSet();
    }


    @Test
    public void string2Integer() {
        ConversionService conversionService = csfb.getObject();
        Integer result = conversionService.convert("123", Integer.class);
        Assert.assertEquals(123, result.intValue());
    }

    @Test
    public void string2Class() {
        ConversionService conversionService = csfb.getObject();
        // String to Class
        Class<?> clazz = (Class<?>) conversionService.convert("com.zfoo.storage.model.vo.Storage", TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Class.class));

        Assert.assertEquals("com.zfoo.storage.model.vo.Storage", clazz.getName());
    }

    @Test
    public void string2Map() {
        ConversionService conversionService = csfb.getObject();
        //Json to Map
        String str = "{\"1\":1,\"2\":2,\"3\":3}";

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map = (Map<Integer, Integer>) conversionService.convert(str, TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(map.getClass()));

        Assert.assertEquals(3, map.size());
    }

    @Test
    public void string2Array() {
        ConversionService conversionService = csfb.getObject();
        String str = "[1,2,3]";

        Integer[] array = (Integer[]) conversionService.convert(str, TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Integer[].class));

        Assert.assertEquals(3, array.length);
    }

}
