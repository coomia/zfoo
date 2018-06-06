package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class BooleanSerializer implements ISerializer {

    private static final BooleanSerializer SERIALIZER = new BooleanSerializer();

    private BooleanSerializer() {

    }

    public static BooleanSerializer getInstance() {
        return SERIALIZER;
    }


    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        return ByteBufUtils.readBoolean(buffer);
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        ByteBufUtils.writeBoolean(buffer, (Boolean) object);
    }
}
