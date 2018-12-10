package com.zfoo.ztest.bigdata.zookeeper.recipes.distributedbarrier;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-03 16:13
 */
public class JavaBarrierTest {

    public static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1号选手")));
        executor.submit(new Thread(new Runner("2号选手")));
        executor.submit(new Thread(new Runner("3号选手")));
        executor.shutdown();
    }
}

class Runner implements Runnable {
    private String name;

    public Runner(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + " 准备好了.");
        try {
            JavaBarrierTest.barrier.await();
        } catch (Exception e) {
        }
        System.out.println(name + " 起跑!");
    }

}
