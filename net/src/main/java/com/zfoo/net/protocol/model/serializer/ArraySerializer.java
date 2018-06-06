package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.ArrayField;
import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class ArraySerializer implements ISerializer {


    private static final ArraySerializer SERIALIZER = new ArraySerializer();

    private ArraySerializer() {

    }

    public static ArraySerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        if (!ByteBufUtils.readBoolean(buffer)) {
            return null;
        }

        ArrayField arrayField = (ArrayField) fieldRegistration;

        int length = ByteBufUtils.readInt(buffer);
        Object array = Array.newInstance(arrayField.field().getType().getComponentType(), length);

        ISerializer arrayTypeSerializer = arrayField.getArrayTypeSerializer();

        if (arrayTypeSerializer == null) {// 代表是其它协议
            for (int i = 0; i < length; i++) {
                Object value = FieldSerializer.getInstance().readObjectByProtocolId(buffer, arrayField.getProtocolId());
                Array.set(array, i, value);
            }
        } else {// 默认注册协议类型，int，float
            for (int i = 0; i < length; i++) {
                Object value = arrayTypeSerializer.readObject(buffer, arrayField);
                Array.set(array, i, value);
            }
        }

        return array;
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        if (object == null) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }

        ArrayField arrayField = (ArrayField) fieldRegistration;

        int length = Array.getLength(object);
        if (length == 0) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }

        ByteBufUtils.writeBoolean(buffer, true);
        ByteBufUtils.writeInt(buffer, length);

        ISerializer arrayTypeSerializer = arrayField.getArrayTypeSerializer();

        if (arrayTypeSerializer != null) {// 默认注册协议类型，int，float
            for (int i = 0; i < length; i++) {
                Object element = Array.get(object, i);
                arrayTypeSerializer.writeObject(buffer, element, arrayField);
            }
            return;
        }

        for (int i = 0; i < length; i++) {// 代表是其它协议
            Object element = Array.get(object, i);
            FieldSerializer.getInstance().writeObject(buffer, element, arrayField);
        }

    }
}
