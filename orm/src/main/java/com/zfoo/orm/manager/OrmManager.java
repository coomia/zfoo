package com.zfoo.orm.manager;

import com.zfoo.orm.model.cache.EntityCaches;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.orm.model.vo.CacheDef;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-29 17:50
 */
public class OrmManager implements IOrmManager {


    private Map<Class<? extends IEntity<?>>, CacheDef> cacheDefMap;

    private final Map<Class<? extends IEntity<?>>, IEntityCaches<?, ?>> entityCachesMap = new HashMap<>();

    public OrmManager(Map<Class<? extends IEntity<?>>, CacheDef> cacheDefMap) {
        this.cacheDefMap = cacheDefMap;
    }

    @Override
    public void init() {
        for (CacheDef cacheDef : cacheDefMap.values()) {
            EntityCaches<?, ?> entityCaches = new EntityCaches(cacheDef);
            entityCachesMap.put((Class<? extends IEntity<?>>) cacheDef.getClazz(), entityCaches);
        }
    }

    @Override
    public IEntityCaches<?, ?> getEntityCaches(Class<?> clazz) {
        return entityCachesMap.get(clazz);
    }


}
