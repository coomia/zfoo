package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.ISerializer;
import com.zfoo.net.protocol.model.serializer.MapSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.14 11:41
 */
public class MapField implements IFieldRegistration {

    private Field field;

    private boolean isInterfaceOrAbstractClass;

    private short keyProtocolId;// -1表示是是BaseField，其它的表示是其它协议序列号是PRotocolRegistration的id

    private short valueProtocolId;// -1表示是是BaseField，其它的表示是其它协议序列号是PRotocolRegistration的id

    private ISerializer keyTypeSerializer;// -1才有效，表示基本类型序列化器

    private ISerializer valueTypeSerializer;// -1才有效，表示基本类型序列化器

    private MapField() {

    }

    public static MapField valueOf(Field field, short keyProtocolId, short valueProtocolId, ISerializer keyTypeSerializer, ISerializer valueTypeSerializer) {
        MapField mapField = new MapField();
        mapField.setField(field);
        mapField.setKeyProtocolId(keyProtocolId);
        mapField.setValueProtocolId(valueProtocolId);
        mapField.setKeyTypeSerializer(keyTypeSerializer);
        mapField.setValueTypeSerializer(valueTypeSerializer);
        int modifiers = field.getType().getModifiers();
        if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)) {
            mapField.setInterfaceOrAbstractClass(true);
        } else {
            mapField.setInterfaceOrAbstractClass(false);
        }
        return mapField;
    }


    @Override
    public ISerializer serializer() {
        return MapSerializer.getInstance();
    }

    @Override
    public Field field() {
        return field;
    }

    public Map<Object, Object> createInstance() {
        if (isInterfaceOrAbstractClass) {
            return new HashMap<>();
        } else {
            try {
                return (Map<Object, Object>) field.getType().newInstance();
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
        return "MapField{" + "field=" + field + ", isInterfaceOrAbstractClass=" + isInterfaceOrAbstractClass + ", keyProtocolId=" + keyProtocolId + ", valueProtocolId=" + valueProtocolId + ", keyTypeSerializer=" + keyTypeSerializer + ", valueTypeSerializer=" + valueTypeSerializer + '}';
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public short getKeyProtocolId() {
        return keyProtocolId;
    }

    public void setKeyProtocolId(short keyProtocolId) {
        this.keyProtocolId = keyProtocolId;
    }

    public short getValueProtocolId() {
        return valueProtocolId;
    }

    public void setValueProtocolId(short valueProtocolId) {
        this.valueProtocolId = valueProtocolId;
    }

    public boolean isInterfaceOrAbstractClass() {
        return isInterfaceOrAbstractClass;
    }

    public void setInterfaceOrAbstractClass(boolean interfaceOrAbstractClass) {
        isInterfaceOrAbstractClass = interfaceOrAbstractClass;
    }

    public ISerializer getKeyTypeSerializer() {
        return keyTypeSerializer;
    }

    public void setKeyTypeSerializer(ISerializer keyTypeSerializer) {
        this.keyTypeSerializer = keyTypeSerializer;
    }

    public ISerializer getValueTypeSerializer() {
        return valueTypeSerializer;
    }

    public void setValueTypeSerializer(ISerializer valueTypeSerializer) {
        this.valueTypeSerializer = valueTypeSerializer;
    }
}
