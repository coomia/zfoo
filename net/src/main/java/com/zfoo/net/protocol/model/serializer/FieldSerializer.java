package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.protocol.model.protocol.*;
import com.zfoo.net.protocol.util.ByteBufUtils;
import com.zfoo.util.ReflectionUtils;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;

/**
 * 只要是protocol都是使用FieldSerializer
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:31
 */
public class FieldSerializer implements ISerializer {

    private static final FieldSerializer SERIALIZER = new FieldSerializer();

    private FieldSerializer() {

    }

    public static FieldSerializer getInstance() {
        return SERIALIZER;
    }


    public Object readObjectByProtocolId(ByteBuf buffer, short protocolId) {
        if (!ByteBufUtils.readBoolean(buffer)) {
            return null;
        }
        ProtocolRegistration protocol = NetContext.getProtocolManager().getProtocolRegistration(protocolId);
        Object object = ReflectionUtils.newInstance(protocol.getConstructor());
        for (IFieldRegistration packetFieldRegistration : protocol.getPacketFields()) {
            Field field = packetFieldRegistration.field();
            ISerializer serializer = packetFieldRegistration.serializer();
            Object fieldValue = serializer.readObject(buffer, packetFieldRegistration);
            ReflectionUtils.setField(field, object, fieldValue);
        }

        return object;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        ReferenceField referenceField = (ReferenceField) fieldRegistration;
        return readObjectByProtocolId(buffer, referenceField.getProtocolId());
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        ByteBufUtils.writeBoolean(buffer, (object == null) ? false : true);
        if (object == null) {
            return;
        }

        IPacket packet = (IPacket) object;
        short id = packet.protcolId();
        ProtocolRegistration protocol = NetContext.getProtocolManager().getProtocolRegistration(id);
        for (IFieldRegistration packetFieldRegistration : protocol.getPacketFields()) {
            Field field = packetFieldRegistration.field();
            ISerializer serializer = packetFieldRegistration.serializer();
            Object fieldValue = ReflectionUtils.getField(field, object);
            serializer.writeObject(buffer, fieldValue, packetFieldRegistration);
        }
    }
}
