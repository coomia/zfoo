package com.zfoo.ztest.jvm.heap;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-22 19:32
 */

@Ignore
public class StackOverFlowTest {

    /**
     * VM Args: -Xss128k
     * <p>
     * 设置栈空间的大小。
     * 方法栈调用过深导致soe，Stack Overflow Exception，栈溢出
     */
    @Test
    public void testStack() {
        testStack();
    }

    /**
     * VM Args: -Xss2M
     * <p>
     * 线程栈创建太多导致soe
     */
    @Test
    public void testThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                    }
                }
            });
            thread.start();
        }
    }

}
