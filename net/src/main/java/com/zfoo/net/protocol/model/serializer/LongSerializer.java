package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class LongSerializer implements ISerializer {

    private static final LongSerializer SERIALIZER = new LongSerializer();

    private LongSerializer() {

    }

    public static LongSerializer getInstance() {
        return SERIALIZER;
    }


    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        return ByteBufUtils.readLong(buffer);
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        ByteBufUtils.writeLong(buffer, (Long) object);
    }
}
