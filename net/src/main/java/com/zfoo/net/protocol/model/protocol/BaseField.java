package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.ISerializer;

import java.lang.reflect.Field;

/**
 * 一个包里所包含的变量还有这个变量的序列化器
 * 描述boolean，byte，short，int，long，float，double，char，String等基本序列化器
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 17:45
 */
public class BaseField implements IFieldRegistration {

    private Field field;
    private ISerializer serializer;

    private BaseField() {

    }

    public static BaseField valueOf(Field field, ISerializer serializer) {
        BaseField packetField = new BaseField();
        packetField.setField(field);
        packetField.setSerializer(serializer);
        return packetField;
    }


    @Override
    public ISerializer serializer() {
        return serializer;
    }

    public void setSerializer(ISerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Field field() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
