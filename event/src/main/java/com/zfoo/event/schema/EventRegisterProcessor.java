package com.zfoo.event.schema;

import com.zfoo.event.EventContext;
import com.zfoo.event.manager.EventBusManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 11:14
 */
public class EventRegisterProcessor extends InstantiationAwareBeanPostProcessorAdapter {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        EventBusManager manager = (EventBusManager) EventContext.getEventBusManager();
        manager.registEventReceiver(bean);
        return bean;
    }


}
