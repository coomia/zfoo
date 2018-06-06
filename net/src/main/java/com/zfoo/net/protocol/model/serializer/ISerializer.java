package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import io.netty.buffer.ByteBuf;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:05
 */
public interface ISerializer {

    Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration);

    void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration);
}
