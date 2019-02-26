package com.zfoo.ztest.jvm.heap;

import com.zfoo.util.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-22 20:20
 */
@Ignore
public class DirectMemoryOOMTest {

    /*
    VM Args: -Xms20m -Xmx20m -XX:MaxDirectMemorySize=10M -XX:+HeapDumpOnOutOfMemoryError
    DirectMemory导致内存溢出，一个明显的特征是在Heap Dump File中找不到什么明显的异常，也不会生成这个文件
     */
    @Test
    public void testDirectMemoryOOM() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(IOUtils.BITS_PER_MB);
        }
    }

}
