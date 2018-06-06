package com.zfoo.event.model.vo;

import com.zfoo.event.model.event.IEvent;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Method;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 11:19
 */
public class EventReceiverDefintion implements IEventReceiver {


    private Object bean;

    // 被ReceiveEvent注解的方法
    private Method method;

    // 接收的参数Class
    private Class<? extends IEvent> paraClazz;

    private EventReceiverDefintion(Object bean, Method method, Class<? extends IEvent> paraClazz) {
        this.bean = bean;
        this.method = method;
        this.paraClazz = paraClazz;
        ReflectionUtils.makeAccessible(this.method);
    }

    public static EventReceiverDefintion valueOf(Object bean, Method method) {
        Class<?>[] clazzs = method.getParameterTypes();
        if (clazzs.length != 1) {
            FormattingTuple message = MessageFormatter.format("[class:{}] [method:{}] must have one parameter!", bean.getClass().getSimpleName(), method.getName());
            throw new IllegalArgumentException(message.getMessage());
        }
        if (!IEvent.class.isAssignableFrom(clazzs[0])) {
            FormattingTuple message = MessageFormatter.format("[class:{}] [method:{}] must have one [IEvent] type parameter!", bean.getClass().getSimpleName(), method.getName());
            throw new IllegalArgumentException(message.getMessage());
        }
        return new EventReceiverDefintion(bean, method, (Class<? extends IEvent>) clazzs[0]);
    }

    @Override
    public void invoke(IEvent event) {
        ReflectionUtils.invokeMethod(bean, method, event);
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

    public Class<? extends IEvent> getParaClazz() {
        return paraClazz;
    }

    public void setParaClazz(Class<? extends IEvent> paraClazz) {
        this.paraClazz = paraClazz;
    }

}
