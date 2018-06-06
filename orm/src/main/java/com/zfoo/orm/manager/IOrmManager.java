package com.zfoo.orm.manager;

import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.model.entity.IEntity;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-29 17:50
 */
public interface IOrmManager {

    void init();


    IEntityCaches<?, ?> getEntityCaches(Class<?> clazz);
}
