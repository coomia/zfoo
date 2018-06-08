package com.zfoo.ztest.netty.netty.udpserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * UDP通信
 *
 * @author SunInsanity
 * @version 1.0
 * @since 2017 05.27 17:24
 */
public class UDPServer {

    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    public void init() {
        //配置服务端nio线程组
        EventLoopGroup group = new NioEventLoopGroup();//服务端接受客户端连接
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true).handler(new UDPServerHandler());
            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出，释放线程池资源
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        System.out.println("hello");
        UDPServer server = new UDPServer(9999);
        server.init();
        System.out.println("hello");
    }

}
