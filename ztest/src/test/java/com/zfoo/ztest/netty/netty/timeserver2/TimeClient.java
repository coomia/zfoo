package com.zfoo.ztest.netty.netty.timeserver2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.CharArrayReader;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public class TimeClient {

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChildChannelHandler());
        ChannelFuture future = null;
        try {
            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            channel.pipeline().addLast(new StringDecoder());
            channel.pipeline().addLast(new TimeClientHandler());
        }
    }

    public static void main(String[] args) {
        new TimeClient().connect(9999, "127.0.0.1");
        System.out.println("hello");
    }

}
