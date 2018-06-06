package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class StringSerializer implements ISerializer {

    private static final String CODE_CHARSET = "UTF-8";


    private static final StringSerializer SERIALIZER = new StringSerializer();

    private StringSerializer() {

    }

    public static StringSerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        if (!ByteBufUtils.readBoolean(buffer)) {
            return null;
        }
        int length = ByteBufUtils.readInt(buffer);
        byte[] bytes = new byte[ length ];
        for (int i = 0; i < length; i++) {
            bytes[ i ] = ByteBufUtils.readByte(buffer);
        }
        String str = null;
        try {
            str = new String(bytes, CODE_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        String str = (String) object;
        ByteBufUtils.writeBoolean(buffer, (object == null) ? false : true);
        if (object == null) {
            return;
        }
        byte[] strBytes = null;
        try {
            strBytes = str.getBytes(CODE_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int length = strBytes.length;

        try {
            ByteBufUtils.writeInt(buffer, length);
            ByteBufUtils.writeBytes(buffer, strBytes);
        } catch (BufferOverflowException e) {
            e.printStackTrace();
        }
    }
}
