package com.zfoo.orm.model.cache;

import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.AssertionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 带上限的HashMap，key和value不能为空
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-04 10:52
 */
public class CachedSoftHashMap<K extends Comparable<K> & Serializable, V extends IEntity<K>> implements Map<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(CachedSoftHashMap.class);

    private int cacheMaxSize;

    private Map<K, Reference<V>> cacheMap = new ConcurrentHashMap<>();


    public CachedSoftHashMap(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    // CachedSoftHashMap如果已经超过最大上限，则将所有的缓存都会写到数据库，并清除所有的缓存数据
    private void flushAllCaches() {
        for (Entry<K, Reference<V>> entry : cacheMap.entrySet()) {
            Reference<V> reference = entry.getValue();
            if (reference == null) {
                continue;
            }
            V value = reference.get();
            if (value == null) {
                continue;
            }
            IEntityCaches<K, V> entityCaches = (IEntityCaches<K, V>) OrmContext.getOrmManager().getEntityCaches(value.getClass());
            entityCaches.update(value);
        }

        cacheMap.clear();
    }


    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public boolean isEmpty() {
        return cacheMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cacheMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        Reference<V> reference = cacheMap.get(key);
        if (reference == null) {
            return null;
        }

        // 如果弱引用为空，则直接将缓存中的所有数据移除
        V value = reference.get();
        if (value == null) {
            cacheMap.remove(key);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        return putIfAbsent(key, value);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (size() > cacheMaxSize) {
            flushAllCaches();
            logger.error("[{}]的缓存已经达到最大上线[cacheMaxSize:{}]，执行清除所有缓存的操作！", value.getClass().getSimpleName(), cacheMaxSize);
        }
        AssertionUtils.notNull(key);
        AssertionUtils.notNull(value);
        Reference<V> reference = new ReferenceEntry(key, value);

        Reference<V> previousReference = cacheMap.putIfAbsent(key, reference);

        if (previousReference == null) {
            return null;
        }

        return previousReference.get();
    }

    @Override
    public V remove(Object key) {
        return cacheMap.remove(key).get();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        AssertionUtils.notNull(m);
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            put(key, value);
        }
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return cacheMap.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = new HashSet<>();
        for (Entry<K, Reference<V>> entry : cacheMap.entrySet()) {
            V value = entry.getValue().get();
            if (value != null) {
                collection.add(value);
            }
        }
        return collection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (Entry<K, Reference<V>> entry : cacheMap.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue().get();
            ReferenceEntry<K, V> referenceEntry = new ReferenceEntry<>(key, value);

        }
        return set;
    }

    private class ReferenceEntry<K, V> extends SoftReference<V> implements Entry<K, V> {
        private K key;

        public ReferenceEntry(K key, V value) {
            super(value);
            this.key = key;
        }


        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return get();
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }

}
