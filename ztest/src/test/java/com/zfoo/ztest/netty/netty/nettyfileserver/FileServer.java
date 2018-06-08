package com.zfoo.ztest.netty.netty.nettyfileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 * 通过netty的通信，和java原生的RandomAccessFile来实现小文件的传输
 *
 * @author SunInsanity
 * @version 1.0
 * @since 2017 05.31 09:49
 */
public class FileServer {

    private int port;

    public FileServer(int port) {
        this.port = port;
    }

    public void init() {
        //配置服务端nio线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();//服务端接受客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//SocketChannel的网络读写
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler());

            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            channel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));//三者组合起来就是文本换行编码解码器
            channel.pipeline().addLast(new FileServerHandler());
        }
    }


    public static void main(String[] args) {
        System.out.println("hello");
        FileServer server = new FileServer(9999);
        server.init();
        System.out.println("hello");
    }
}
