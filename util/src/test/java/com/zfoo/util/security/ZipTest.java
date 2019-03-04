package com.zfoo.util.security;

import com.zfoo.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 15:56
 */
public class ZipTest {

    // Zip算法压缩测试
    @Test
    public void test() {
        String str = "ZIP，是一个文件的压缩的算法，原名Deflate（真空），发明者为菲利普·卡兹（Phil Katz)），" +
                "他于1989年1月公布了该格式的资料。ZIP通常使用后缀名“.zip”，它的MIME格式为 application/zip 。" +
                "目前，ZIP格式属于几种主流的压缩格式之一，其竞争者包括RAR格式以及开放源码的7-Zip格式。" +
                "从性能上比较，RAR格式较ZIP格式压缩率较高，但是它的压缩时间远远高于Zip。" +
                "而7-Zip(7z)由于提供了免费的压缩工具而逐渐在更多的领域得到应用。";

        byte[] bytes = str.getBytes();

        // 压缩前数组长度
        Assert.assertEquals(bytes.length, 555);

        bytes = ZipUtils.zip(bytes);
        // 压缩后数组长度
        Assert.assertEquals(bytes.length, 438);

        bytes = ZipUtils.unZip(bytes);

        Assert.assertEquals(new String(bytes),str);
    }

}
