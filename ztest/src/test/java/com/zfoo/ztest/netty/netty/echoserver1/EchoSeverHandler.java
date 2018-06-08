package com.zfoo.ztest.netty.netty.echoserver1;

import com.zfoo.ztest.netty.netty.constant.DelimiterConsts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public class EchoSeverHandler extends ChannelInboundHandlerAdapter {


    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("The is " + count + "times receive client : [" + body + "]");
        count++;
        body = body + DelimiterConsts.DOLLAR.getValue();
        ByteBuf writeBuffer = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(writeBuffer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
