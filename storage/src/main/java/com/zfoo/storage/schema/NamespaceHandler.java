package com.zfoo.storage.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.21 10:12
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(StorageDefinitionParser.STORAGE, new StorageDefinitionParser());
    }
}
