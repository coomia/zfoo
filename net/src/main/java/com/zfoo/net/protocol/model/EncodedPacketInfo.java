package com.zfoo.net.protocol.model;

import com.zfoo.net.protocol.model.packet.IPacket;

/**
 * 被解码后的Packet的信息
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 12:07
 */
public class EncodedPacketInfo {

    private IPacket packet;// 解码后的包
    private int length;// 长度
    private long encodedTime;// 解码所用时间

    public EncodedPacketInfo(IPacket packet, int length, long encodedTime) {
        this.packet = packet;
        this.length = length;
        this.encodedTime = encodedTime;
    }

    public IPacket getPacket() {
        return packet;
    }

    public void setPacket(IPacket packet) {
        this.packet = packet;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getEncodedTime() {
        return encodedTime;
    }

    public void setEncodedTime(long encodedTime) {
        this.encodedTime = encodedTime;
    }
}
