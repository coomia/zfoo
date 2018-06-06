package com.zfoo.net.protocol.model.protocol;

import java.lang.reflect.Constructor;

/**
 * 协议必须为一个简单的POJO对象，必须有一个标识为private static final transient的PROTOCOL_ID号
 * 必须实现IPacket接口,返回的protocolId必须和PROTOCOL_ID号一致
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:03
 */
public class ProtocolRegistration {


    private short id;
    private Constructor<?> constructor;
    private IFieldRegistration[] packetFields;// 所有的协议里的发送顺序都是按字段名称排序

    public ProtocolRegistration() {

    }


    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public IFieldRegistration[] getPacketFields() {
        return packetFields;
    }

    public void setPacketFields(IFieldRegistration[] packetFields) {
        this.packetFields = packetFields;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }
}
