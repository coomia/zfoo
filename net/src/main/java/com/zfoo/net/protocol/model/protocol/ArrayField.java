package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.ArraySerializer;
import com.zfoo.net.protocol.model.serializer.ISerializer;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.13 17:46
 */
public class ArrayField implements IFieldRegistration {

    private Field field;

    private short protocolId;// -1表示是是BaseField，其它的表示是其它协议序列号是PRotocolRegistration的id

    private ISerializer arrayTypeSerializer;// -1才有效，表示基本类型序列化器


    private ArrayField() {

    }

    public static ArrayField valueOf(Field field, short protocolId, ISerializer genericTypeSerializer) {
        ArrayField arrayField = new ArrayField();
        arrayField.setField(field);
        arrayField.setProtocolId(protocolId);
        arrayField.setArrayTypeSerializer(genericTypeSerializer);
        return arrayField;
    }

    @Override
    public Field field() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    public short getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(short protocolId) {
        this.protocolId = protocolId;
    }

    @Override
    public ISerializer serializer() {
        return ArraySerializer.getInstance();
    }

    public ISerializer getArrayTypeSerializer() {
        return arrayTypeSerializer;
    }

    public void setArrayTypeSerializer(ISerializer arrayTypeSerializer) {
        this.arrayTypeSerializer = arrayTypeSerializer;
    }
}

