package com.zfoo.ztest.net.netty.proxy;

import com.zfoo.util.IOUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-09-13 21:44
 */
public class HttpProxyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if (request.method() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        final String path = request.uri().split("/img/")[1];

        AsyncHttpClient asyncHttpClient = null;
        try {
            asyncHttpClient = Dsl.asyncHttpClient();
            asyncHttpClient
                    .prepareGet(path)
                    .execute()
                    .toCompletableFuture()
                    .thenApply(proxyResponse -> {
                        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderNames.CONTENT_TYPE);

                        ByteBuf buffer = Unpooled.copiedBuffer(proxyResponse.getResponseBodyAsBytes());
                        response.content().writeBytes(buffer);
                        buffer.release();
                        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

                        return response;
                    })
                    .thenAccept(c -> System.out.println(c))
                    .join();

        } finally {
            IOUtils.closeIO(asyncHttpClient);
        }
    }


    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
                , Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

}