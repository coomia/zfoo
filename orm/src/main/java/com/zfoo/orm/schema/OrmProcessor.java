package com.zfoo.orm.schema;

import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.OrmInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * FactoryBean，在某些情况下，实例化Bean非常复杂，如果按照传统的方式，则需要在bean标签中配置大量的信息，
 * 配置方式的灵活性是受到限制的，这时采用编码的方式可能会获得一个简单的方案
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 13 20 12
 */
public class OrmProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectionUtils.filterFieldsInClass(bean.getClass(), new ReflectionUtils.FieldFilter() {
            @Override
            public boolean matches(Field field) {
                if (!field.isAnnotationPresent(OrmInjection.class)) {
                    return false;
                }
                return true;
            }
        }, new ReflectionUtils.FieldCallback() {
            @Override
            public void callBack(Field field) throws IllegalArgumentException, IllegalAccessException {
                Type type = field.getGenericType();

                if (!(type instanceof ParameterizedType)) {
                    throw new RuntimeException("类型声明不正确，不是泛型类：" + type);
                }

                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                Class<? extends IEntity<?>> clazz = (Class<? extends IEntity<?>>) types[1];
                IEntityCaches<?, ?> entityCaches = OrmContext.getOrmManager().getEntityCaches(clazz);

                if (entityCaches == null) {
                    FormattingTuple message = MessageFormatter.format("实体缓存对象[entityCaches:{}]不存在", clazz);
                    throw new RuntimeException(message.getMessage());
                }

                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, entityCaches);
            }
        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }
}
