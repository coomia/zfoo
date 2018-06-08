package com.zfoo.ztest.netty.netty.echoserver1;

import com.zfoo.ztest.netty.netty.constant.DelimiterConsts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    public static final String ECHO_REQUEST = "Welcome to netty!" + DelimiterConsts.DOLLAR.getValue();

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf firstMessage;
        byte[] req = ECHO_REQUEST.getBytes();
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(req));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is " + count + " times receive sever :[" + body + "]");
        count++;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
