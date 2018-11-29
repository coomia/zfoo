package com.zfoo.ztest.jvm.heap;

/**
 * 今天的实践环节，你可以体验一下无安全点检测的计数循环带来的长暂停。你可以分别测单独跑 foo 方法或者 bar 方法的时间，然后与合起来跑的时间比较一下。
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/27
 */

// time java SafepointTestp
// 你还可以使用如下几个选项
// -XX:+PrintGC
// -XX:+PrintGCApplicationStoppedTime
// -XX:+PrintSafepointStatistics
// -XX:+UseCountedLoopSafepoints
public class SafePointTest {
    static double sum = 0;

    public static void foo() {
        for (int i = 0; i < 0x77777777; i++) {
            sum += Math.sqrt(i);
        }
    }

    public static void bar() {
        for (int i = 0; i < 50_000_000; i++) {
            new Object().hashCode();
        }
    }

    public static void main(String[] args) {
//        new Thread(SafePointTest::foo).start();
        new Thread(SafePointTest::bar).start();
    }
}

