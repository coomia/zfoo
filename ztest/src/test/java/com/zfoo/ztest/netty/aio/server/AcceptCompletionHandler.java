package com.zfoo.ztest.netty.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {
    @Override
    public void completed(AsynchronousSocketChannel result, AioServer attachment) {
        attachment.getAsynchronousServerSocketChannel().accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AioServer attachment) {
        exc.printStackTrace();
        attachment.getLatch().countDown();
    }
}
