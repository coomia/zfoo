package com.zfoo.event.schema;

import com.zfoo.event.EventContext;
import com.zfoo.event.manager.EventBusManager;
import com.zfoo.util.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 10:44
 */
public class EventDefintionParser extends AbstractBeanDefinitionParser {

    // 配置标签名称
    public static final String EVENT = "event";

    public static final String EVENT_BUS_ID = "id";

    public static final String EVENT_BUS_PACKAGE = "event-bus-package";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        Class<?> clazz;
        String name;
        BeanDefinitionBuilder builder;

        // 注册EventSpringContext
        clazz = EventContext.class;
        name = StringUtils.uncapitalize(clazz.getName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册EventRegisterProcessor，event事件处理
        clazz = EventRegisterProcessor.class;
        name = StringUtils.uncapitalize(clazz.getName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册EventBusManager
        String eventBusId = element.getAttribute(EVENT_BUS_ID);
        String eventBusPackage = element.getAttribute(EVENT_BUS_PACKAGE);
        clazz = EventBusManager.class;
        name = StringUtils.uncapitalize(clazz.getName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addConstructorArgValue(eventBusId);
        builder.addConstructorArgValue(eventBusPackage);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        return builder.getBeanDefinition();
    }

}
