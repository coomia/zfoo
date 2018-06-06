package com.zfoo.net.schema;

import com.zfoo.net.NetContext;
import com.zfoo.net.dispatcher.manager.PacketDispatcherManager;
import com.zfoo.net.protocol.manager.ProtocolManager;
import com.zfoo.net.protocol.service.ProtocolService;
import com.zfoo.net.server.manager.SessionManager;
import com.zfoo.net.server.Server;
import com.zfoo.util.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 14:23
 */
public class NetDefinitionParser extends AbstractBeanDefinitionParser {

    public static final String NET = "net";

    public static final String ID = "id";

    public static final String PROTOCOL_LOCATION = "protocol-location";

    public static final String RECEIVER_PACKAGE = "receiver-package";

    public static final String MAX_SIZE = "max-size";

    public static final String HOST_ADRESS = "host-address";

    public static final String PORT = "port";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        String id = element.getAttribute(ID);
        String protocolLocation = element.getAttribute(PROTOCOL_LOCATION);
        String receiverPackage = element.getAttribute(RECEIVER_PACKAGE);
        int maxSize = Integer.valueOf(element.getAttribute(MAX_SIZE));
        String hostName = element.getAttribute(HOST_ADRESS);
        int port = Integer.valueOf(element.getAttribute(PORT));

        Class<?> clazz;
        String name;
        BeanDefinitionBuilder builder;

        // 注册NetSpringContext
        clazz = NetContext.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addConstructorArgValue(protocolLocation);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册NetProcessor
        clazz = NetProcessor.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册ProtocolManager
        clazz = ProtocolManager.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册ProtocolService
        clazz = ProtocolService.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册PacketDispatcherManager
        clazz = PacketDispatcherManager.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册SessionManager
        clazz = SessionManager.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册Server
        clazz = Server.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addConstructorArgValue(hostName);
        builder.addConstructorArgValue(port);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        return builder.getBeanDefinition();
    }

}