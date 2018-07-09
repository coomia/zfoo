package com.zfoo.net.protocol.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.27 16:49
 */
public class ByteBufUtilsTest {

    @Test
    public void readByte() {
        byte[] values = new byte[]{Byte.MIN_VALUE, -100, -2, -1, 0, 1, 2, 100, Byte.MAX_VALUE};
        for (byte value : values) {
            ByteBuf byteBuf = Unpooled.buffer();
            ByteBufUtils.writeByte(byteBuf, value);
            int result = ByteBufUtils.readByte(byteBuf);
            Assert.assertEquals(result, value);
        }
    }

    @Test
    public void readShort() {
        short[] values = new short[]{Short.MIN_VALUE, -100, -2, -1, 0, 1, 2, 100, Short.MAX_VALUE};
        for (short value : values) {
            ByteBuf byteBuf = Unpooled.buffer();
            ByteBufUtils.writeShort(byteBuf, value);
            int result = ByteBufUtils.readShort(byteBuf);
            Assert.assertEquals(result, value);
        }
    }

    @Test
    public void readInt() {
        int[] values = new int[]{Integer.MIN_VALUE, -100, -2, -1, 0, 1, 2, 100, Integer.MAX_VALUE};
        for (int value : values) {
            ByteBuf byteBuf = Unpooled.buffer();
            ByteBufUtils.writeInt(byteBuf, value);
            int result = ByteBufUtils.readInt(byteBuf);
            Assert.assertEquals(result, value);
        }
    }


    @Test
    public void readLong() {
        long[] values = new long[]{Long.MIN_VALUE, -100, -2, -1, 0, 1, 2, 100, Long.MAX_VALUE};
        for (long value : values) {
            ByteBuf byteBuf = Unpooled.buffer();
            ByteBufUtils.writeLong(byteBuf, value);
            long result = ByteBufUtils.readLong(byteBuf);
            Assert.assertEquals(result, value);
        }
    }


    /**
     * 测试所有int值，运行时间太长，放弃测试
     */
    @Ignore
    @Test
    public void readIntTest() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (int value = Integer.MIN_VALUE; value <= Integer.MAX_VALUE; value++) {
            byteBuf.clear();
            ByteBufUtils.writeInt(byteBuf, value);
            int result = ByteBufUtils.readInt(byteBuf);
            if (value != result) {
                throw new IllegalArgumentException("error:" + value + "-->" + result);
            }
        }
    }


    @Ignore
    @Test
    public void readLongTest() {
        for (long value = Long.MIN_VALUE; value <= Long.MAX_VALUE; value++) {
            ByteBuf byteBuf = Unpooled.buffer();
            ByteBufUtils.writeLong(byteBuf, value);
            long result = ByteBufUtils.readLong(byteBuf);
            if (value != result) {
                throw new IllegalArgumentException("error:" + value + "-->" + result);
            }
        }
    }

}
