package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class ShortSerializer implements ISerializer {


    private static final ShortSerializer SERIALIZER = new ShortSerializer();

    private ShortSerializer() {

    }

    public static ShortSerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        return ByteBufUtils.readShort(buffer);
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        ByteBufUtils.writeShort(buffer, (Short) object);
    }
}
