package com.zfoo.ztest.jvm.clazz;

import org.junit.Ignore;

/**
 * 这个测试静态代码块的初始化时机
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/21
 */
@Ignore
public class ClassLoaderTest {


    private ClassLoaderTest() {
    }

    private static class LazyHolder {
        static final ClassLoaderTest INSTANCE = new ClassLoaderTest();

        static {
            System.out.println("LazyHolder.<clinit>");
        }
    }

    public static Object getInstance(boolean flag) {
        if (flag) {
            return new LazyHolder[2];
        }
        return LazyHolder.INSTANCE;
    }

    /**
     * VM Args: -verbose:class
     * <p>
     * JVM 规范枚举了下述多种触发情况：
     * 1.当虚拟机启动时，初始化用户指定的主类；
     * 2.当遇到用以新建目标类实例的 new 指令时，初始化 new 指令的目标类；
     * 3.当遇到调用静态方法的指令时，初始化该静态方法所在的类；
     * 4.当遇到访问静态字段的指令时，初始化该静态字段所在的类；
     * 5.子类的初始化会触发父类的初始化；
     * 6.如果一个接口定义了 default 方法，那么直接实现或者间接实现该接口的类的初始化，会触发该接口的初始化；
     * 7.使用反射 API 对某个类进行反射调用时，初始化这个类；
     * 8.当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的类。
     */
    public static void main(String[] args) {
        getInstance(true);
        System.out.println("----");
        getInstance(false);
    }

}
