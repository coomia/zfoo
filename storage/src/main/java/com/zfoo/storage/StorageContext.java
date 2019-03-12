package com.zfoo.storage;

import com.zfoo.storage.interpreter.IResourceReader;
import com.zfoo.storage.manager.IStorageManager;
import com.zfoo.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-29 21:14
 */
public class StorageContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static StorageContext instance;

    private ApplicationContext applicationContext;

    private IResourceReader resourceReader;

    private IStorageManager storageManager;

    private ConversionService conversionService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StorageContext.instance == null) {
            StorageContext.instance = this;
            instance.applicationContext = applicationContext;
            instance.conversionService = (ConversionService) applicationContext.getBean(StringUtils.uncapitalize(ConversionServiceFactoryBean.class.getName()));
            instance.resourceReader = applicationContext.getBean(IResourceReader.class);
            instance.storageManager = applicationContext.getBean(IStorageManager.class);
            // 初始化
            instance.storageManager.init();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    public static StorageContext getInstance() {
        return instance;
    }

    public static ConversionService getConversionService() {
        return instance.conversionService;
    }

    public static IResourceReader getResourceReader() {
        return instance.resourceReader;
    }

    public static IStorageManager getStorageManager() {
        return instance.storageManager;
    }

}
