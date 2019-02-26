import javassist.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.*;
import java.lang.reflect.Modifier;

/*
 测试javassist生成的类和普通用new关键字创建出来的类之间的区别<hr/>
 测试发现两者从访问方法和访问变量的速度几乎没有什么区别，可以方向的用javassist代理其它的类，不会影响速度<hr/>
 */

interface DGet {
    int getA();

    void setA(int a);

    int getB();

    void setB(int b);

    int getC();

    void setC(int c);
}

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.10 11:02
 */

class A {
    public int a;
    public int b;
    public int c;

    public A() {
    }
}

class B {
    private int a;
    private int b;
    private int c;

    public B() {
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}

class C {
    private int a;
    private int b;
    private int c;

    public C() {
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}

class D {
    private int a;
    private int b;
    private int c;

    public D() {
    }

}

public class JavassistTest {


    public static final int INTERVAL_TIME = 1000;

    public static final int A_CONSTANT = 99999;
    public static final int B_CONSTANT = 888888888;
    public static final int C_CONSTANT = 7777777;

    public static final int INT_CONSTANT = 999999;


    /**
     * 直接访问public的访问速度
     */
    public static void testA() {

        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start <= INTERVAL_TIME) {
            A a = new A();
            a.a = A_CONSTANT;
            a.b = B_CONSTANT;
            a.c = C_CONSTANT;
            count++;
        }

        System.out.println(count);
    }

    /**
     * 用get和set方法访问成员变量的速度
     */
    public static void testB() {

        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start <= INTERVAL_TIME) {
            B b = new B();
            b.setA(A_CONSTANT);
            b.setB(B_CONSTANT);
            b.setC(C_CONSTANT);
            count++;
        }

        System.out.println(count);
    }

    /*
     通过反射直接操作属性访问变量的速度
     */
    public static void testC() throws NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Field aField = C.class.getDeclaredField("a");
        aField.setAccessible(true);
        Field bField = C.class.getDeclaredField("b");
        bField.setAccessible(true);
        Field cField = C.class.getDeclaredField("c");
        cField.setAccessible(true);

        Method aSetMethod = C.class.getDeclaredMethod("setA", int.class);
        aSetMethod.setAccessible(true);
        Method bSetMethod = C.class.getDeclaredMethod("setB", int.class);
        bSetMethod.setAccessible(true);
        Method cSetMethod = C.class.getDeclaredMethod("setC", int.class);
        cSetMethod.setAccessible(true);

        Constructor<?> constructor = C.class.getConstructor((Class<?>) null);
        constructor.setAccessible(true);

        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start <= INTERVAL_TIME) {
            //C c = C.class.newInstance();
            C c = (C) constructor.newInstance(null);
            aField.set(c, A_CONSTANT);
            bField.set(c, B_CONSTANT);
            cField.set(c, C_CONSTANT);
            // aSetMethod.invoke(c, A_CONSTANT);
            // bSetMethod.invoke(c, B_CONSTANT);
            // cSetMethod.invoke(c, C_CONSTANT);
            count++;
        }

        System.out.println(count);
    }

    /*
     通过javassist访问成员变量的速度
     */
    public static void testD() throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.makeClass("DGetClass");

        ctClass.addInterface(classPool.get(DGet.class.getCanonicalName()));

        CtField a = new CtField(classPool.get(int.class.getCanonicalName()), "a", ctClass);
        a.setModifiers(Modifier.PRIVATE);
        ctClass.addField(a);

        CtField b = new CtField(classPool.get(int.class.getCanonicalName()), "b", ctClass);
        b.setModifiers(Modifier.PRIVATE);
        ctClass.addField(b);

        CtField c = new CtField(classPool.get(int.class.getCanonicalName()), "c", ctClass);
        c.setModifiers(Modifier.PRIVATE);
        ctClass.addField(c);

        CtConstructor ctConstructor = new CtConstructor(null, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        CtMethod aGetMethod = new CtMethod(classPool.get(int.class.getCanonicalName()), "getA", null, ctClass);
        aGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder aGetBuilder = new StringBuilder();
        aGetBuilder.append("{return this.a;}");
        aGetMethod.setBody(aGetBuilder.toString());
        ctClass.addMethod(aGetMethod);

        CtMethod aSetMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "setA", classPool.get(new String[]{int.class.getCanonicalName()}), ctClass);
        aGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder aSetBuilder = new StringBuilder();
        aSetBuilder.append("{this.a=$1;}");
        aSetMethod.setBody(aSetBuilder.toString());
        ctClass.addMethod(aSetMethod);

        CtMethod bGetMethod = new CtMethod(classPool.get(int.class.getCanonicalName()), "getB", null, ctClass);
        bGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder bGetBuilder = new StringBuilder();
        bGetBuilder.append("{return this.b;}");
        bGetMethod.setBody(bGetBuilder.toString());
        ctClass.addMethod(bGetMethod);

        CtMethod bSetMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "setB", classPool.get(new String[]{int.class.getCanonicalName()}), ctClass);
        aGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder bSetBuilder = new StringBuilder();
        bSetBuilder.append("{this.b=$1;}");
        bSetMethod.setBody(bSetBuilder.toString());
        ctClass.addMethod(bSetMethod);

        CtMethod cGetMethod = new CtMethod(classPool.get(int.class.getCanonicalName()), "getC", null, ctClass);
        cGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder cGetBuilder = new StringBuilder();
        cGetBuilder.append("{return this.c;}");
        cGetMethod.setBody(cGetBuilder.toString());
        ctClass.addMethod(cGetMethod);

        CtMethod cSetMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "setC", classPool.get(new String[]{int.class.getCanonicalName()}), ctClass);
        aGetMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder cSetBuilder = new StringBuilder();
        cSetBuilder.append("{this.c=$1;}");
        cSetMethod.setBody(cSetBuilder.toString());
        ctClass.addMethod(cSetMethod);

        Class<?> clazz = ctClass.toClass();
        Constructor<?> constructor = clazz.getConstructor(null);

        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start <= INTERVAL_TIME) {
            DGet d = (DGet) constructor.newInstance(null);
            d.setA(A_CONSTANT);
            d.setB(B_CONSTANT);
            d.setC(C_CONSTANT);
            count++;
        }

        System.out.println(count);

        // d.setA(A_CONSTANT);
        // System.out.println(d.getA());
        // d.setB(B_CONSTANT);
        // System.out.println(d.getB());
        // d.setC(C_CONSTANT);
        // System.out.println(d.getC());
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, CannotCompileException, NotFoundException, InvocationTargetException, NoSuchFieldException {
        testA();
        testB();
        testC();
        testD();
    }


    @Ignore
    @Test
    public void test() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
        Class c = cc.toClass();
        Hello h = (Hello) c.newInstance();
        h.say();
    }

}

class Hello {
    public void say() {
        System.out.println("Hello");
    }
}
