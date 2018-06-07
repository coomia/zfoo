package com.zfoo.orm.model.vo;

import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.anno.UniqueIndex;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.AssertionUtils;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 11:40
 */
public class CacheDef {

    private Class<? extends IEntity<?>> clazz;

    private int cacheSize;

    private PersisterDef persisterDef;

    private Map<String, IndexDef> indexDefMap;

    private Map<String, UniqueIndexDef> uniqueIndexDefMap;

    private CacheDef() {
    }

    public static CacheDef valueOf(Class<?> clazz, Map<String, Integer> cacheMap, Map<String, PersisterDef> persisterMap) {
        if (!IEntity.class.isAssignableFrom(clazz)) {
            FormattingTuple message = MessageFormatter.arrayFormat("被[{}]注解标注的实体类[{}]必须继承[{}]"
                    ,new Object[]{ Cache.class.getName(),clazz.getName(),IEntity.class.getName()});
            throw new IllegalArgumentException(message.getMessage());
        }

        CacheDef cacheDef = new CacheDef();
        cacheDef.setClazz((Class<? extends IEntity<?>>) clazz);

        Cache cache = clazz.getAnnotation(Cache.class);
        AssertionUtils.notNull(cache);
        cacheDef.setCacheSize(cacheMap.get(cache.size()));

        Persister persister = cache.persister();
        AssertionUtils.notNull(persister);
        PersisterDef persisterDef = persisterMap.get(persister.value());
        cacheDef.setPersisterDef(persisterDef);

        Field[] fields;

        Map<String, IndexDef> indexDefMap = new HashMap<>();
        fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(clazz, Index.class);
        for (Field field : fields) {
            IndexDef indexDef = new IndexDef(field, field.getAnnotation(Index.class));
            indexDefMap.put(field.getName(), indexDef);
        }

        Map<String, UniqueIndexDef> uniqueIndexDefMap = new HashMap<>();
        fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(clazz, UniqueIndex.class);
        for (Field field : fields) {
            UniqueIndexDef uniqueIndexDef = new UniqueIndexDef(field, field.getAnnotation(UniqueIndex.class));
            uniqueIndexDefMap.put(field.getName(), uniqueIndexDef);
        }

        cacheDef.setIndexDefMap(indexDefMap);
        cacheDef.setUniqueIndexDefMap(uniqueIndexDefMap);

        return cacheDef;
    }


    public Class<? extends IEntity<?>> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends IEntity<?>> clazz) {
        this.clazz = clazz;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public PersisterDef getPersisterDef() {
        return persisterDef;
    }

    public void setPersisterDef(PersisterDef persisterDef) {
        this.persisterDef = persisterDef;
    }

    public Map<String, IndexDef> getIndexDefMap() {
        return indexDefMap;
    }

    public void setIndexDefMap(Map<String, IndexDef> indexDefMap) {
        this.indexDefMap = indexDefMap;
    }

    public Map<String, UniqueIndexDef> getUniqueIndexDefMap() {
        return uniqueIndexDefMap;
    }

    public void setUniqueIndexDefMap(Map<String, UniqueIndexDef> uniqueIndexDefMap) {
        this.uniqueIndexDefMap = uniqueIndexDefMap;
    }


}
