package com.zfoo.ztest.juc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-12-21 19:57
 */
@Ignore
public class CompletableFutureTest {

    // 其中supplyAsync用于有返回值的任务，runAsync则用于没有返回值的任务。
    // Executor参数可以手动指定线程池，否则默认ForkJoinPool.commonPool()系统级公共线程池。
    @Test
    public void testRunAsync() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 提交一个一部执行的任务,无结果返回值
        // supplyAsync用于有返回值的任务，runAsync则用于没有返回值的任务
        CompletableFuture<Void> completableVoid = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": I have done Nothing");
        }, executorService);

        completableVoid.thenApplyAsync(new Function<Void, Void>() {
            @Override
            public Void apply(Void aVoid) {
                System.out.println(Thread.currentThread().getName() + ": I have done Nothing, too");
                return null;
            }
        }, executorService).thenApply(new Function<Void, Object>() {
            @Override
            public Object apply(Void aVoid) {
                System.out.println(Thread.currentThread().getName() + ": I have done Nothing, too, too");
                return null;
            }
        }).whenCompleteAsync(new BiConsumer<Object, Throwable>() {
            @Override
            public void accept(Object o, Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + ": Done");
            }
        }, executorService);

        completableVoid.join();
    }

    @Test
    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 提交一个一部执行的任务,有结果返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName() + ": I have done Nothing");
                return "hello";
            }
        }, executorService);


        //若future执行完毕,则future.get();返回执行结果'future 1',若未执行完毕,则返回给定值'111'

        System.out.println(future.get());
        future.complete("111");


        //返回一个指定结果的CompletableFuture对象
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("future 2");
    }


    @Test
    public void testAllOf() {
        Long start = System.currentTimeMillis();
        // 结果集
        List<String> list = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), executorService)
                        .thenApply(h -> Integer.toString(h))
                        .whenComplete((s, e) -> {
                            System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
                            list.add(s);
                        })
                ).toArray(CompletableFuture[]::new);
        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
        System.out.println("list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }


    public Integer calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

}
