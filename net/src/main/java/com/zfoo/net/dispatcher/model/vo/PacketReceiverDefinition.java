package com.zfoo.net.dispatcher.model.vo;

import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.server.model.Session;
import com.zfoo.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.29 12:39
 */
public class PacketReceiverDefinition implements IPacketReceiver {

    private Object bean;// 一个facade的bean，这个bean里有void methodName(Session session,CM_Int cm)接受的方法

    private Method method;// 接受的方法void methodName(Session session,CM_Int cm)

    private Class<?> clazz;// 接受的包的Class类，如CM_Int

    public PacketReceiverDefinition(Object bean, Method method,Class<?> clazz) {
        this.bean = bean;
        this.method = method;
        this.clazz=clazz;
        ReflectionUtils.makeAccessible(method);
    }

    @Override
    public void invoke(Session session, IPacket packet) {
        ReflectionUtils.invokeMethod(bean, method, session, packet);
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
