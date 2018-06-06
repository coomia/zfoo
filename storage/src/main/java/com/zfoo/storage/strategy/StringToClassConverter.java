package com.zfoo.storage.strategy;

import com.zfoo.storage.StorageContext;
import org.springframework.core.convert.converter.Converter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.20 11:23
 */
public class StringToClassConverter implements Converter<String, Class> {


    @Override
    public Class convert(String source) {
        if (!source.contains(".") && !source.startsWith("[")) {
            source = "java.lang." + source;
        }

        ClassLoader loader = null;

        StorageContext context = StorageContext.getInstance();

        if (context != null) {
            loader = StorageContext.getApplicationContext().getClassLoader();
        } else {
            loader = Thread.currentThread().getContextClassLoader();
        }

        try {
            return Class.forName(source, true, loader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("无法将字符串:[" + source + "]转换为Class对象");
        }

//        try {
//            return Class.forName(source);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("无法将字符串:[" + source + "]转换为Class对象");
//        }
    }
}
