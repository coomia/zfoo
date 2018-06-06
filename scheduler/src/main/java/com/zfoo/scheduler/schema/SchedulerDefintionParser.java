package com.zfoo.scheduler.schema;

import com.zfoo.scheduler.SchedulerContext;
import com.zfoo.scheduler.manager.SchedulerManager;
import com.zfoo.scheduler.service.SchedulerService;
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
public class SchedulerDefintionParser extends AbstractBeanDefinitionParser {

    // 配置标签名称
    public static final String SCHEDULER = "scheduler";

    public static final String SCHEDULER_ID = "id";

    public static final String SCHEDULER_PACKAGE = "scheduler-package";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        Class<?> clazz;
        String name;
        BeanDefinitionBuilder builder;

        // 注册SchedulerSpringContext
        clazz = SchedulerContext.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册SchedulerRegisterProcessor
        clazz = SchedulerRegisterProcessor.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册SchedulerService
        clazz = SchedulerService.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册SchedulerManager
        String schedulerId = element.getAttribute(SCHEDULER_ID);
        String schedulerPackage = element.getAttribute(SCHEDULER_PACKAGE);
        clazz = SchedulerManager.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addConstructorArgValue(schedulerId);
        builder.addConstructorArgValue(schedulerPackage);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());


        return builder.getBeanDefinition();
    }

}
