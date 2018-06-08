package com.zfoo.ztest.netty.nio.scattergather;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
public class Scatter {

    public static void main(String[] args) throws FileNotFoundException {

        //分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中
        //聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel
        RandomAccessFile file = new RandomAccessFile("rainbow.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        CharBuffer header = CharBuffer.allocate(5);
        CharBuffer body = CharBuffer.allocate(6);

        CharBuffer[] charBuffers = {header, body};

//        ByteBuffer header = ByteBuffer.allocate(128);
//        ByteBuffer body   = ByteBuffer.allocate(1024);
//
//        ByteBuffer[] bufferArray = { header, body };
//
//        channel.read(bufferArray);


//        ByteBuffer header = ByteBuffer.allocate(128);
//        ByteBuffer body   = ByteBuffer.allocate(1024);
//
////write data into buffers
//
//        ByteBuffer[] bufferArray = { header, body };
//
//        channel.write(bufferArray);

    }

}
