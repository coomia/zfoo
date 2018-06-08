package com.zfoo.ztest.netty.netty.timeserver2;

import com.zfoo.ztest.netty.netty.constant.DelimiterConsts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("The time server receive order :" + body + count++);
        String currentTime = "QUERY TIME ORDER" + new Date().toString() + DelimiterConsts.SEPARATOR.getValue();
        ByteBuf writeBuffer = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(writeBuffer);
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
