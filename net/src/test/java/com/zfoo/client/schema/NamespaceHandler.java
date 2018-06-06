package com.zfoo.client.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 10:13
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(ClientNetDefinitionParser.CLIENT, new ClientNetDefinitionParser());
    }

}
