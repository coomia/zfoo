package com.zfoo.ztest.juc;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-12-21 21:32
 */
public class LambdaTest {

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }


    @Test
    public void testLambda() {
        LambdaTest tester = new LambdaTest();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;
        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        // 用括号
        GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
    }

    // *****************************************************************************************************************

    public static void printValur(String str) {
        System.out.println("print value : " + str);
    }

    @Test
    public void testRef() {
        List<String> list = Arrays.asList("a", "b", "c", "d");
        for (String a : list) {
            LambdaTest.printValur(a);
        }
        //下面的for each循环和上面的循环是等价的
        list.forEach(x -> {
            LambdaTest.printValur(x);
        });
        //下面的方法和上面等价的
        list.forEach(LambdaTest::printValur);
        //下面的方法和上面等价的
        Consumer<String> methodParam = LambdaTest::printValur; //方法参数
        list.forEach(x -> methodParam.accept(x));//方法执行accept
    }

    // *****************************************************************************************************************

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {

            if (predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }

    @Test
    public void testFunction() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("输出所有偶数:");
        eval(list, n -> n % 2 == 0);

        System.out.println("输出大于等于 0 的所有数字:");
        Predicate<Integer> predicate = (n) -> n >= 0;
        eval(list, predicate);
    }

}
