package com.zfoo.net.dispatcher.util;

import com.zfoo.net.dispatcher.model.vo.IPacketReceiver;
import com.zfoo.net.dispatcher.model.vo.PacketReceiverDefinition;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.server.model.Session;
import javassist.*;

import java.lang.reflect.*;
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

    static {
        Class<?>[] classArray = new Class[]{
                IPacket.class,
                IPacketReceiver.class,
                Session.class
        };

        for (Class<?> clazz : classArray) {
            if (classPool.find(clazz.getCanonicalName()) == null) {
                ClassClassPath classPath = new ClassClassPath(clazz);
                classPool.insertClassPath(classPath);
            }
        }
    }

    public static IPacketReceiver createPacketReciver(PacketReceiverDefinition definition) throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object bean = definition.getBean();
        Method method = definition.getMethod();
        Class<?> clazz = definition.getClazz();

        // 定义类名称
        CtClass enhanceClazz = classPool.makeClass(bean.getClass().getSimpleName() + method.getName() + index.incrementAndGet());
        enhanceClazz.addInterface(classPool.get(IPacketReceiver.class.getCanonicalName()));

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
        CtMethod invokeMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "invoke", classPool.get(new String[]{Session.class.getCanonicalName(), IPacket.class.getCanonicalName()}), enhanceClazz);
        invokeMethod.setModifiers(Modifier.PUBLIC + Modifier.FINAL);
        String invokeMethodBody = "{this.bean." + method.getName() + "($1,(" + clazz.getCanonicalName() + ")$2);}";// 强制类型转换，转换为CM_Int的类型
        invokeMethod.setBody(invokeMethodBody);
        enhanceClazz.addMethod(invokeMethod);

        Class<?> resultClazz = enhanceClazz.toClass();
        Constructor<?> resultContructor = resultClazz.getConstructor(bean.getClass());
        IPacketReceiver receiver = (IPacketReceiver) resultContructor.newInstance(bean);
        return receiver;
    }
}
