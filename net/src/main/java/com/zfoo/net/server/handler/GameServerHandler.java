package com.zfoo.net.server.handler;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.model.EncodedPacketInfo;
import com.zfoo.net.server.model.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.12 11:17
 */
@ChannelHandler.Sharable
public class GameServerHandler extends ChannelInboundHandlerAdapter {

    private static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session-key");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<Session> sessionAttr = ctx.channel().attr(SESSION_KEY);
        Session session = sessionAttr.get();
        if (session == null) {
            return;
        }
        EncodedPacketInfo encodedPacketInfo = (EncodedPacketInfo) msg;
        NetContext.getDispatcherManager().receive(session, encodedPacketInfo.getPacket());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Attribute<Session> sessionAttr = channel.attr(SESSION_KEY);
        Session session = new Session(channel);
        boolean isActived = sessionAttr.compareAndSet(null, session);
        if (!isActived) {
            channel.close();
            return;
        }
        NetContext.getSessionManager().addSession(session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Attribute<Session> sessionAttr = ctx.channel().attr(SESSION_KEY);
        Session session = sessionAttr.get();
        if (session != null) {
            NetContext.getSessionManager().removeSession(session);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        if (channel.isOpen() || channel.isActive()) {
            channel.close();
        }
        try {
            throw cause;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
