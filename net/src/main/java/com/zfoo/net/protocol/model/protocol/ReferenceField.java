package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.FieldSerializer;
import com.zfoo.net.protocol.model.serializer.ISerializer;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.13 10:49
 */
public class ReferenceField implements IFieldRegistration {

    private short protocolId;// 协议序列号是PRotocolRegistration的id
    private Field field;

    private ReferenceField() {

    }

    public static ReferenceField valueOf(Field field, short protocolId) {
        ReferenceField referenceField = new ReferenceField();
        referenceField.setProtocolId(protocolId);
        referenceField.setField(field);
        return referenceField;
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
        return FieldSerializer.getInstance();
    }
}
