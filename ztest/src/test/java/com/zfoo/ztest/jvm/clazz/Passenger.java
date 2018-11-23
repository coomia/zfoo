package com.zfoo.ztest.jvm.clazz;

/**
 * 测试JVM中的内联方法
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/21
 */
public abstract class Passenger {
    abstract void depart();

    // Run with: java -XX:CompileCommand='dontinline,*.depart' Passenger
    public static void main(String[] args) {
        Passenger a = new ChinesePassenger();
        Passenger b = new ForeignerPassenger();
        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            Passenger c = (i < 1_000_000_000) ? a : b;
            c.depart();
        }
    }
}

class ChinesePassenger extends Passenger {
    @Override
    void depart() {
    }
}

class ForeignerPassenger extends Passenger {
    @Override
    void depart() {
    }
}
