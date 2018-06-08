package com.zfoo.ztest.netty.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class AioClient implements Runnable {

    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AioClient(String host, int port, int latchNum) {
        this.host = host;
        this.port = port;
        this.latch = new CountDownLatch(latchNum);
    }

    public void init() {
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ConnectCompletionHandler handler = new ConnectCompletionHandler(client, latch);
        client.connect(new InetSocketAddress(host, port), handler, handler);
        try {
            latch.await();
            client.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AioClient aioClient = new AioClient("127.0.0.1", 9999, 1);
        aioClient.init();
        new Thread(aioClient, "client").start();

    }
}
