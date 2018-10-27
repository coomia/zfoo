package com.zfoo.ztest.juc;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/25
 */
@Ignore
public class DeamonThreadTest {


    class ChildThread extends Thread {
        private String name = null;

        public ChildThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(this.name + "--child thead begin");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(this.name + "--child thead over");
        }
    }

    // Java的线程机制，在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)

    // Attention：必须要在main函数中运行，复制一下代码到main函数

    /*
    只要用户线程没有退出，程序就不会退出，知道用户线程全部退出，主线程才会退出
    输出结果：
    parent thread begin
    parent thread over
    thread2--child thead begin
    thread1--child thead begin
    thread2--child thead over
    thread1--child thead over
    **/
    @Test
    public void testUserThread() {
        System.out.println("parent thread begin ");

        ChildThread t1 = new ChildThread("thread1");
        ChildThread t2 = new ChildThread("thread2");
        t1.start();
        t2.start();

        System.out.println("parent thread over ");
    }


    /*
    输出结果：
    parent thread begin
    parent thread over
    thread1--child thead begin
    thread2--child thead begin
    **/
    @Test
    public void testDeamonThread() {
        System.out.println("parent thread begin ");

        ChildThread t1 = new ChildThread("thread1");
        ChildThread t2 = new ChildThread("thread2");
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        System.out.println("parent thread over ");
    }


    public static void main(String[] args) {
        // new DeamonThreadTest().testUserThread();
        // new DeamonThreadTest().testDeamonThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("This is shutdown-hook!!!");
            System.out.println("The run function in here is final to run!!!");
            System.out.println("It is usally used for release resource!");
        }, "shutdown-hook"));

    }

}
