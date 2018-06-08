package com.zfoo.ztest.netty.netty.echoserver2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public class EchoClient {


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
            channel.pipeline().addLast(new FixedLengthFrameDecoder(20));
            channel.pipeline().addLast(new StringDecoder());
            channel.pipeline().addLast(new EchoClientHandler());
        }
    }

    public static void main(String[] args) {
        new EchoClient().connect(9999, "127.0.0.1");
        System.out.println("hello");
    }

}
