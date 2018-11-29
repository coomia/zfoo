package com.zfoo.ztest.jvm.clazz;

/**
 * -XX:+PrintBiasedLockingStatistics 来打印各类锁的个数。
 * 由于 C2 使用的是另外一个参数 -XX:+PrintPreciseBiasedLockingStatistics，因此你可以限制 Java 虚拟机仅使用 C1 来即时编译（对应参数 -XX:TieredStopAtLevel=1）。
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/29
 */
// Run with -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
public class SynchronizedTest {

    static Lock lock = new Lock();
    static int counter = 0;

    public static void foo() {
        synchronized (lock) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // lock.hashCode(); // Step 2
        // System.identityHashCode(lock); // Step 4
        for (int i = 0; i < 1_000_000; i++) {
            foo();
        }
    }

    static class Lock {
        // @Override public int hashCode() { return 0; } // Step 3
    }
}
