package com.zfoo.storage.strategy;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.20 11:29
 */
public class StringToDateConverter implements Converter<String, Date> {


    @Override
    public Date convert(String source) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return df.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("字符串:" + source + ",不符合格式要求:[yyyy-MM-dd HH:mm:ss]");
        }
    }
}
