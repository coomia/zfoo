package com.zfoo.util.security;

import com.zfoo.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 15:27
 */
public abstract class ZipUtils {

    private static final int BUFFER_SIZE = IOUtils.ONE_BYTE;

    // compression level (0-9)，只能是0-9
    private static final int COMPRESS_LEVEL = 5;

    public static byte[] zip(byte[] bytes) {
        // deflate  [dɪ'fleɪt]  v.抽出空气; 缩小
        Deflater deflater = new Deflater(COMPRESS_LEVEL);
        deflater.setInput(bytes);
        deflater.finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            baos.write(buffer, 0, count);
        }
        deflater.end();

        IOUtils.closeIO(baos);

        return baos.toByteArray();
    }

    public static byte[] unZip(byte[] bytes) {
        Inflater inflater = new Inflater();
        inflater.setInput(bytes);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                baos.write(buffer, 0, count);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeIO(baos);
        }
        return baos.toByteArray();
    }

}
