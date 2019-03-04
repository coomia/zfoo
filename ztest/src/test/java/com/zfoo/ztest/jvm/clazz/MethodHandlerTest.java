package com.zfoo.ztest.jvm.clazz;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 * class Horse {
 *   public void race() {
 *     System.out.println("Horse.race()");
 *   }
 * }
 *
 * class Deer {
 *   public void race() {
 *     System.out.println("Deer.race()");
 *   }
 * }
 *
 * class Cobra {
 *   public void race() {
 *     System.out.println("How do you turn this on?");
 *   }
 * }
 *
 * 鸭子类型（duck typing）不需要使用对象的method，以相同的方式调用race()本身，更关注对象的行为。
 *
 * 方法句柄是一个强类型的，能够被直接执行的引用。该引用可以指向常规的静态方法或者实例方法，也可以指向构造器或者字段。
 *
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/23
 */
@Ignore
public class MethodHandlerTest {

    public void testMethod(String str) {

    }

    //900ms
    @Test
    public void testReflect() throws Exception{
        MethodHandlerTest test = new MethodHandlerTest();

        Class cls = MethodHandlerTest.class;
        Method testMethod = cls.getMethod("testMethod", String.class);
        testMethod.setAccessible(true);

        Instant start = Instant.now();

        for (int i = 0; i < 3_000_000_00L ; i++) {
            testMethod.invoke(test, "a");
        }

        Instant end = Instant.now();
        Duration between = Duration.between(start, end);

        System.out.println("reflect : " + between.toMillis());
    }

    //1300ms
    @Test
    public void testMethodHandle() throws Throwable {
        MethodHandlerTest test = new MethodHandlerTest();

        MethodType methodType = MethodType.methodType(void.class, String.class);
        MethodHandle testMethod = MethodHandles.lookup().findVirtual(MethodHandlerTest.class, "testMethod", methodType);

        Instant start = Instant.now();

        for (int i = 0; i < 3_000_000_00L ; i++) {
            testMethod.invoke(test, "a");
            // invokeExact()，此方法有比较严格的限制，其返回类型要和MethodType的返回值严格相同
        }

        Instant end = Instant.now();
        Duration between = Duration.between(start, end);

        System.out.println("method : " + between.toMillis());
    }

}
