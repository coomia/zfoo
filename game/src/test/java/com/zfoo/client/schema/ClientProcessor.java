package com.zfoo.client.schema;

import com.zfoo.net.NetContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 11:47
 */
public class ClientProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {

    private int count = 0;

    public ClientProcessor() {
    }

    public void printBean(Object bean) {
        count++;
        System.out.println(this.getClass().getSimpleName() + "-->" + bean + "-->" + count);
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        printBean(bean);
        packetReceiverRegister(bean);
        return bean;
    }

    private void packetReceiverRegister(Object bean) {
        NetContext.getDispatcherManager().registPacketReceiverDefintion(bean);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
