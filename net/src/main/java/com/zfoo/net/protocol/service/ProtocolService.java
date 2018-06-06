package com.zfoo.net.protocol.service;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.manager.ProtocolManager;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.protocol.model.protocol.*;
import com.zfoo.net.protocol.model.serializer.FieldSerializer;
import com.zfoo.net.protocol.model.serializer.ISerializer;
import com.zfoo.net.protocol.util.ByteBufUtils;
import com.zfoo.util.ReflectionUtils;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 10:58
 */
public class ProtocolService implements IProtocolService {

    private static FieldSerializer serializer = FieldSerializer.getInstance();

    public ProtocolService() {

    }

    @Override
    public IPacket read(ByteBuf buffer) {
        short id = ByteBufUtils.readShort(buffer);
        return (IPacket) serializer.readObjectByProtocolId(buffer, id);
    }

    @Override
    public void write(ByteBuf buffer, IPacket packet) {
        buffer.clear();
        buffer.writeInt(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 预留写入包的长度，一个int字节大小

        // 写入一个包
        short id = packet.protcolId();
        ByteBufUtils.writeShort(buffer, id);
        ByteBufUtils.writeBoolean(buffer, (packet == null) ? false : true);
        if (packet == null) {
            return;
        }
        ProtocolRegistration protocol = NetContext.getProtocolManager().getProtocolRegistration(id);
        for (IFieldRegistration packetFieldRegistration : protocol.getPacketFields()) {
            Field field = packetFieldRegistration.field();
            ISerializer serializer = packetFieldRegistration.serializer();
            Object fieldValue = ReflectionUtils.getField(field, packet);
            serializer.writeObject(buffer, fieldValue, packetFieldRegistration);
        }

        int length = buffer.readableBytes();

        int packetLength = length - ProtocolManager.PROTOCOL_HEAD_LENGTH;

        buffer.writerIndex(0);

        buffer.writeInt(packetLength);

        buffer.writerIndex(length);
    }
}
