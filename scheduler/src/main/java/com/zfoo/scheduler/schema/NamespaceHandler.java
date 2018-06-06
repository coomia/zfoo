package com.zfoo.scheduler.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 11:14
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(SchedulerDefintionParser.SCHEDULER, new SchedulerDefintionParser());
    }
}
