package com.zfoo.storage.schema;

import com.zfoo.storage.StorageContext;
import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import com.zfoo.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Bean级生命周期接口和容器生命周期接口是个性和共性的辩证统一思想的体现，
 * 前者解决Bean个性化处理的问题，而后者解决容器中某些Bean共性处理的问题
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.21 10:13
 */
public class ResInjectionProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {


    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectionUtils.filterFieldsInClass(bean.getClass(), null, new ReflectionUtils.FieldCallback() {
            @Override
            public void callBack(Field field) throws IllegalArgumentException, IllegalAccessException {
                ResInjection anno = field.getAnnotation(ResInjection.class);
                if (anno != null) {
                    injectStroage(bean, field, anno);
                }
            }
        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    private void injectStroage(Object bean, Field field, ResInjection anno) {
        Type type = field.getGenericType();

        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("类型声明不正确，不是泛型类：" + type);
        }

        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        // @ResInjection
        // Storage<Integer, ActivityResource> resources;
        Class<?> keyClazz = (Class<?>) types[0];

        Class<?> resourceClazz = (Class<?>) types[1];

        Storage<?, ?> storage = StorageContext.getStorageManager().getStorage(resourceClazz);

        if (storage == null) {
            throw new RuntimeException("静态类资源不存在：" + resourceClazz);
        }

        Field[] fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(resourceClazz, Id.class);
        if (fields.length != 1) {
            throw new RuntimeException("静态类资源id配置重复不存在：" + resourceClazz);
        }
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, storage);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
