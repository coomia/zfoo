package com.zfoo.ztest.jvm;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-22 18:26
 */

@Ignore
public class HeapOOMTest {

    /**
     * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     * <p>
     * 设置堆得最大最小空间，防止堆自动扩展。
     * 不断的在堆中新建对象，导致OOM
     * 使用Eclipse Memory Analyzer打开堆转存快照文件
     */
    @Test
    public void testHeapOOM() {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add(new String("hello"));
        }
    }

}
