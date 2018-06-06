package com.zfoo.net.server.handler;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.manager.ProtocolManager;
import com.zfoo.net.protocol.model.EncodedPacketInfo;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.util.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.16 17:04
 */
public class PacketDecoderHandler extends ByteToMessageDecoder {

    private int length;
    private boolean remain = false;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!remain) {
            if (in.readableBytes() <= ProtocolManager.PROTOCOL_HEAD_LENGTH) {// 不够读一个int
                return;
            }
            length = in.readInt();
            remain = true;
        }

        if (in.readableBytes() < length) {// ByteBuf里的数据太小
            return;
        }

        long start = TimeUtils.currentTimeMillis();


        Object packet = NetContext.getProtocolService().read(in);

        long end = TimeUtils.currentTimeMillis();

        EncodedPacketInfo encodedPacketInfo = new EncodedPacketInfo((IPacket) packet, length, start - end);

        out.add(encodedPacketInfo);

        remain = false;
    }

}
