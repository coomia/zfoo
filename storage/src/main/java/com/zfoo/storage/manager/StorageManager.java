package com.zfoo.storage.manager;

import com.zfoo.storage.StorageContext;
import com.zfoo.storage.model.vo.ResourceDef;
import com.zfoo.storage.model.vo.Storage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.21 12:03
 */
public class StorageManager implements IStorageManager {

    public static final String DEFINITIONS_PROPERTY_VALUE = "definitions";

    private Map<Class<?>, Storage<?, ?>> storages = new HashMap<>();
    private Map<Class<?>, ResourceDef> definitions = new HashMap<>();


    @Override
    public void init() {
        if (definitions == null || definitions.size() <= 0) {
            throw new IllegalStateException("静态资源的信息定义不存在，可能是配置缺少");
        }

        for (ResourceDef definition : definitions.values()) {
            Class<?> clazz = definition.getClazz();
            Storage<?, ?> storage = StorageContext.getApplicationContext().getAutowireCapableBeanFactory().createBean(Storage.class);
            storage.init(definition);
            storages.putIfAbsent(clazz, storage);
        }
    }

    @Override
    public Storage<?, ?> getStorage(Class<?> clazz) {
        return storages.get(clazz);
    }

    public Map<Class<?>, ResourceDef> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<Class<?>, ResourceDef> definitions) {
        this.definitions = definitions;
    }
}
