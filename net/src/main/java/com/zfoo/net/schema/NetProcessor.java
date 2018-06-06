package com.zfoo.net.schema;

import com.zfoo.net.NetContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 18:24
 */
public class NetProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {


    public NetProcessor() {
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        NetContext.getDispatcherManager().registPacketReceiverDefintion(bean);
        return bean;
    }


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
