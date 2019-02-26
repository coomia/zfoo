package com.zfoo.ztest.jvm.heap;

import javassist.*;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-22 20:09
 */
@Ignore
public class MethodArenaOOMTest {

    private static final AtomicInteger index = new AtomicInteger(0);
    private static final ClassPool classPool = ClassPool.getDefault();


    public void createClass() throws NotFoundException, CannotCompileException {
        // 定义类名称
        CtClass enhanceClazz = classPool.makeClass("MethodArenaOOM" + index.incrementAndGet());

        // 定义类中的一个成员
        CtField field = new CtField(classPool.get(MethodArenaOOMTest.class.getCanonicalName()), "bean", enhanceClazz);
        field.setModifiers(Modifier.PRIVATE);
        enhanceClazz.addField(field);

        // 定义类的构造器
        CtConstructor constructor = new CtConstructor(classPool.get(new String[]{MethodArenaOOMTest.class.getCanonicalName()}), enhanceClazz);
        constructor.setBody("{this.bean=$1;}");
        constructor.setModifiers(Modifier.PUBLIC);
        enhanceClazz.addConstructor(constructor);


        Class<?> resultClazz = enhanceClazz.toClass();
        System.out.println(resultClazz);
    }

    /*
     VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M

     设置永久代的大小，JDK7以后不再适用
     */
    @Test
    public void testMethodArenaOOM() throws NotFoundException, CannotCompileException {
        while (true) {
            createClass();
        }
    }


}
