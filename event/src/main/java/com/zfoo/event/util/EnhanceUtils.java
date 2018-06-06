package com.zfoo.event.util;

import com.zfoo.event.model.event.IEvent;
import com.zfoo.event.model.vo.EventReceiverDefintion;
import com.zfoo.event.model.vo.IEventReceiver;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.17 18:18
 */
public abstract class EnhanceUtils {

    private static final AtomicInteger index = new AtomicInteger(0);
    private static final ClassPool classPool = ClassPool.getDefault();

    public static IEventReceiver createEventReciver(EventReceiverDefintion definition) throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object bean = definition.getBean();
        Method method = definition.getMethod();
        Class<?> clazz = definition.getParaClazz();

        // 定义类名称
        CtClass enhanceClazz = classPool.makeClass(bean.getClass().getSimpleName() + method.getName() + index.incrementAndGet());
        enhanceClazz.addInterface(classPool.get(IEventReceiver.class.getCanonicalName()));

        // 定义类中的一个成员
        CtField field = new CtField(classPool.get(bean.getClass().getCanonicalName()), "bean", enhanceClazz);
        field.setModifiers(Modifier.PRIVATE);
        enhanceClazz.addField(field);

        // 定义类的构造器
        CtConstructor constructor = new CtConstructor(classPool.get(new String[]{bean.getClass().getCanonicalName()}), enhanceClazz);
        constructor.setBody("{this.bean=$1;}");
        constructor.setModifiers(Modifier.PUBLIC);
        enhanceClazz.addConstructor(constructor);

        // 定义类实现的接口方法
        CtMethod invokeMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "invoke", classPool.get(new String[]{IEvent.class.getCanonicalName()}), enhanceClazz);
        invokeMethod.setModifiers(Modifier.PUBLIC + Modifier.FINAL);
        String invokeMethodBody = "{this.bean." + method.getName() + "((" + clazz.getCanonicalName() + ")$1);}";// 强制类型转换，转换为具体的Event类型的类型
        invokeMethod.setBody(invokeMethodBody);
        enhanceClazz.addMethod(invokeMethod);

        Class<?> resultClazz = enhanceClazz.toClass();
        Constructor<?> resultContructor = resultClazz.getConstructor(bean.getClass());
        IEventReceiver receiver = (IEventReceiver) resultContructor.newInstance(bean);
        return receiver;
    }
}
