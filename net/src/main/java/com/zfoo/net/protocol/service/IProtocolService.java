package com.zfoo.net.protocol.service;

import com.zfoo.net.protocol.model.packet.IPacket;
import io.netty.buffer.ByteBuf;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 10:57
 */
public interface IProtocolService {

    IPacket read(ByteBuf buffer);

    void write(ByteBuf buffer, IPacket packet);

}
