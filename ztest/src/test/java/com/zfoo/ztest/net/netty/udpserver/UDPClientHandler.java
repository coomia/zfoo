package com.zfoo.ztest.net.netty.udpserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @author SunInsanity
 * @version 1.0
 * @since 2017 05.27 17:48
 */
public class UDPClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static String DICTIONARY = "bbb";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(DICTIONARY, CharsetUtil.UTF_8)
                , packet.sender()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
