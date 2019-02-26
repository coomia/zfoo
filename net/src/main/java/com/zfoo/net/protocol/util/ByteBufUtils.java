package com.zfoo.net.protocol.util;

import io.netty.buffer.ByteBuf;

/**
 * “可变长字节码算法”的压缩数据的算法，以达到压缩数据，减少磁盘IO。
 *
 * google的ProtocolBuffer和Facebook的thrift底层的通信协议都是由这个算法实现
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.27 11:00
 */
public abstract class ByteBufUtils {

    private static final int MTU = 1500;// MTU (Maximum Transmission Unit,最大传输单位)

    public static boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readByte() == 1;
    }

    public static void writeBoolean(ByteBuf byteBuf, boolean value) {
        writeByte(byteBuf, value ? ((byte) 1) : ((byte) 0));
    }

    public static byte readByte(ByteBuf byteBuf) {
        return byteBuf.readByte();
    }

    public static void writeByte(ByteBuf byteBuf, byte value) {
        byteBuf.writeByte(value);
    }

    public static short readShort(ByteBuf byteBuf) {
        return byteBuf.readShort();
    }

    public static void writeShort(ByteBuf byteBuf, short value) {
        byteBuf.writeShort(value);
    }

    public static int readInt(ByteBuf byteBuf) {
        return decodeZigzagInt(readVarint(byteBuf));
    }

    public static void writeInt(ByteBuf byteBuf, int value) {
        writeVarint(byteBuf, encodeZigzagInt(value));
    }

    public static long readLong(ByteBuf byteBuf) {
        return decodeZigzagLong(readVarlong(byteBuf));
    }

    public static void writeLong(ByteBuf byteBuf, long value) {
        writeVarlong(byteBuf, encodeZigzagLong(value));
    }

    public static float readFloat(ByteBuf byteBuf) {
        return byteBuf.readFloat();
    }

    public static void writeFloat(ByteBuf byteBuf, float value) {
        byteBuf.writeFloat(value);
    }

    public static double readDouble(ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }

    public static void writeDouble(ByteBuf byteBuf, double value) {
        byteBuf.writeDouble(value);
    }

    public static char readChar(ByteBuf byteBuf) {
        return byteBuf.readChar();
    }

    public static void writeChar(ByteBuf byteBuf, char value) {
        byteBuf.writeChar(value);
    }

    public static byte[] readBytes(ByteBuf byteBuf, int length) {
        return byteBuf.readBytes(length).array();
    }

    public static void writeBytes(ByteBuf byteBuf, byte[] value) {
        byteBuf.writeBytes(value);
    }

    // 用Zigzag算法压缩int和long的值
    // 再用Varint紧凑算法表示数字的有效位

    private static int encodeZigzagInt(int n) {
        return (n << 1) ^ (n >> 31);// 有效位左移一位+符号位右移31位
    }

    private static int decodeZigzagInt(int n) {
        return (n >>> 1) ^ -(n & 1);
    }

    private static long encodeZigzagLong(long n) {
        return (n << 1) ^ (n >> 63);
    }

    private static long decodeZigzagLong(long n) {
        return (n >>> 1) ^ -(n & 1);
    }

    private static void writeVarint(ByteBuf byteBuf, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {// 如果最高位是0,直接写
                writeByte(byteBuf, (byte) value);
                return;
            } else {
                writeByte(byteBuf, (byte) ((value & 0x7F) | 0x80));//先写入低7位，最高位置1
                value >>>= 7;//再写高7位
            }
        }
    }

    private static void writeVarlong(ByteBuf byteBuf, long value) {
        while (true) {
            if ((value & ~0x7F) == 0) {// 如果最高位是0,直接写
                writeByte(byteBuf, (byte) value);
                return;
            } else {
                writeByte(byteBuf, (byte) ((value & 0x7F) | 0x80));//先写入低7位，最高位置1
                value >>>= 7;//再写高7位
            }
        }
    }

    private static int readVarint(ByteBuf byteBuf) {
        int x = 0;
        int y = 0;
        int count = 0;

        do {
            x = byteBuf.readByte();
            y = y | ((x & 0x7F) << count);
            count += 7;
        } while ((x & ~0x7F) != 0 && count < 31);

        if ((x & ~0x7F) == 0) {
            return y;
        }

        x = byteBuf.readByte();// 大数第5个字节
        if (x < 0) {
            throw new IllegalArgumentException("int读取错误");
        }
        int offset = 0;
        while ((x >>> offset) == 0) {
            offset++;
        }

        return y | x << (count + offset);
    }


    private static long readVarlong(ByteBuf byteBuf) {
        long x = 0;
        long y = 0;
        int count = 0;

        do {
            x = byteBuf.readByte();
            y = y | ((x & 0x7F) << count);
            count += 7;
        } while ((x & ~0x7F) != 0 && count < 63);

        if ((x & ~0x7F) == 0) {
            return y;
        }

        x = byteBuf.readByte();// 大数第9个字节
        if (x < 0) {
            throw new IllegalArgumentException("long读取错误");
        }
        int offset = 0;
        while ((x >>> offset) == 0) {
            offset++;
        }

        return y | x << (count + offset);
    }

}
