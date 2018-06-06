package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.CollectionSerializer;
import com.zfoo.net.protocol.model.serializer.ISerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.13 10:03
 */
public class CollectionField implements IFieldRegistration {

    private Field field;

    private boolean isInterfaceOrAbstractClass;

    private short protocolId;// -1表示是是BaseField，其它的表示是其它协议序列号是PRotocolRegistration的id

    private ISerializer genericTypeSerializer;// -1才有效，表示基本类型序列化器


    private CollectionField() {

    }

    public static CollectionField valueOf(Field field, short protocolId, ISerializer genericTypeSerializer) {
        CollectionField listField = new CollectionField();
        listField.setField(field);
        listField.setProtocolId(protocolId);
        listField.setGenericTypeSerializer(genericTypeSerializer);
        int modifiers = field.getType().getModifiers();
        if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)) {
            listField.setInterfaceOrAbstractClass(true);
        } else {
            listField.setInterfaceOrAbstractClass(false);
        }
        return listField;
    }

    public Collection<Object> createInstance() {
        if (isInterfaceOrAbstractClass) {
            return new ArrayList<>();
        } else {
            try {
                return (Collection<Object>) field.getType().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(this.toString());
    }

    @Override
    public String toString() {
        return "CollectionField{" + "field=" + field + ", isInterfaceOrAbstractClass=" + isInterfaceOrAbstractClass + ", protocolId=" + protocolId + ", genericTypeSerializer=" + genericTypeSerializer + '}';
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
        return CollectionSerializer.getInstance();
    }

    public ISerializer getGenericTypeSerializer() {
        return genericTypeSerializer;
    }

    public void setGenericTypeSerializer(ISerializer genericTypeSerializer) {
        this.genericTypeSerializer = genericTypeSerializer;
    }

    public boolean isInterfaceOrAbstractClass() {
        return isInterfaceOrAbstractClass;
    }

    public void setInterfaceOrAbstractClass(boolean interfaceOrAbstractClass) {
        isInterfaceOrAbstractClass = interfaceOrAbstractClass;
    }
}
