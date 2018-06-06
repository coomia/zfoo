package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.model.protocol.MapField;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class MapSerializer implements ISerializer {


    private static final MapSerializer SERIALIZER = new MapSerializer();

    private MapSerializer() {

    }

    public static MapSerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        if (!ByteBufUtils.readBoolean(buffer)) {
            return null;
        }

        int size = ByteBufUtils.readInt(buffer);
        MapField mapField = (MapField) fieldRegistration;
        Map<Object, Object> map = mapField.createInstance();

        ISerializer keySerializer = mapField.getKeyTypeSerializer();
        ISerializer valueSerializer = mapField.getValueTypeSerializer();

        for (int i = 0; i < size; i++) {
            Object key = null;
            Object value = null;
            if (keySerializer != null) {// 默认注册协议类型，int，float
                key = keySerializer.readObject(buffer, mapField);
            } else {// 代表是其它协议
                key = FieldSerializer.getInstance().readObjectByProtocolId(buffer, mapField.getKeyProtocolId());
            }

            if (valueSerializer != null) {
                value = valueSerializer.readObject(buffer, mapField);
            } else {
                value = FieldSerializer.getInstance().readObjectByProtocolId(buffer, mapField.getValueProtocolId());
            }
            map.put(key, value);
        }
        return map;
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        if (object == null) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }

        Map<?, ?> map = (Map<?, ?>) object;
        MapField mapField = (MapField) fieldRegistration;

        int size = map.size();
        if (size == 0) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }
        ByteBufUtils.writeBoolean(buffer, true);
        ByteBufUtils.writeInt(buffer, size);

        ISerializer keySerializer = mapField.getKeyTypeSerializer();
        ISerializer valueSerializer = mapField.getValueTypeSerializer();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (keySerializer != null) {// 默认注册协议类型，int，float
                keySerializer.writeObject(buffer, entry.getKey(), mapField);
            } else {
                FieldSerializer.getInstance().writeObject(buffer, entry.getKey(), mapField);
            }

            if (valueSerializer != null) {// 代表是其它协议
                valueSerializer.writeObject(buffer, entry.getValue(), mapField);
            } else {
                FieldSerializer.getInstance().writeObject(buffer, entry.getValue(), mapField);
            }
        }
    }
}
