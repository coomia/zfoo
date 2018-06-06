package com.zfoo.orm.model.cache;

import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.orm.model.persister.IPersister;
import com.zfoo.orm.model.persister.PNode;
import com.zfoo.orm.model.vo.CacheDef;
import com.zfoo.orm.model.vo.PersisterDef;
import com.zfoo.orm.model.vo.UniqueIndexDef;
import com.zfoo.util.AssertionUtils;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 11:38
 */
public class EntityCaches<PK extends Comparable<PK> & Serializable, E extends IEntity<PK>>
        implements IEntityCaches<PK, E> {

    private static final Logger logger = LoggerFactory.getLogger(EntityCaches.class);

    private CacheDef cacheDef;

    private IPersister persister;

    private Map<PK, E> cacheMap;

    public EntityCaches(CacheDef cacheDef) {
        this.cacheDef = cacheDef;
        this.cacheMap = new CachedSoftHashMap<>(cacheDef.getCacheSize());

        PersisterDef persisterDef = cacheDef.getPersisterDef();
        this.persister = persisterDef.getPersister().createPersister(persisterDef.getPersisterConfig());
        this.persister.persist();
    }


    @Override
    public E load(PK pk) {
        AssertionUtils.notNull(pk);

        E currentElement = cacheMap.get(pk);

        if (currentElement != null) {
            return currentElement;
        }

        currentElement = OrmContext.getAccessor().load((Class<E>) cacheDef.getClazz(), pk);

        if (currentElement == null) {
            logger.debug("[{}]数据库没有包含主键[pk:{}]的实体缓存", cacheDef.getClazz().getSimpleName(), pk);
            return currentElement;
        }

        currentElement = cacheMap.putIfAbsent(pk, currentElement);

        if (currentElement != null) {
            return currentElement;
        }

        currentElement = cacheMap.get(pk);

        AssertionUtils.notNull(currentElement);
        return currentElement;
    }

    @Override
    public E insert(E entity) {
        AssertionUtils.notNull(entity);

        E currentElement = cacheMap.get(entity.getId());

        // 一切的数据都已缓存为主，缓存中有数据默认就已经插入到数据库了
        if (currentElement != null) {
            return currentElement;
        }

        currentElement = OrmContext.getAccessor().load((Class<E>) cacheDef.getClazz(), entity.getId());

        if (currentElement != null) {
            logger.debug("[{}]数据库包含主键[pk:{}]的实体缓存", cacheDef.getClazz().getSimpleName(), entity.getId());
            return currentElement;
        }

        // 查看需要插入的实体有没有唯一所有和其它实体重复
        for (Map.Entry<String, UniqueIndexDef> entry : cacheDef.getUniqueIndexDefMap().entrySet()) {
            String fieldName = entry.getKey();
            String namedQuery = entry.getValue().getNamedQuery();
            Object parm = ReflectionUtils.getField(entry.getValue().getField(), entity);
            AssertionUtils.notNull(namedQuery);
            AssertionUtils.notNull(parm);
            currentElement = (E) OrmContext.getQuery().uniqueQuery(namedQuery, parm);
            if (currentElement != null) {
                logger.debug("[{}]数据库已经包含唯一索引[{}]=[{}]的实体缓存:[{}]", cacheDef.getClazz().getSimpleName(), fieldName, parm, currentElement);
                return currentElement;
            }
        }

        // 保存到缓存
        currentElement = cacheMap.putIfAbsent(entity.getId(), entity);

        // 入库
        if (currentElement != null) {
            return currentElement;
        }

        persister.put(PNode.valueOfInsertPNode(entity));
        return entity;

    }

    @Override
    public void update(E entity) {
        // 更新的次数大于插入和删除，所以为了提高速度这里不需要判断数据库是否已经存在实体
        AssertionUtils.notNull(entity);
        AssertionUtils.notNull(entity.getId());
        persister.put(PNode.valueOfUpdatePNode(entity));
    }

    @Override
    public E delete(PK pk) {
        // 游戏业务中，操作最频繁的是update，不是insert，delete，query
        // 所以这边并不考虑
        AssertionUtils.notNull(pk);

        E result = OrmContext.getAccessor().load((Class<E>) cacheDef.getClazz(), pk);

        if (result == null) {
            logger.debug("[{}]数据库不包含主键[pk:{}]的实体缓存", cacheDef.getClazz().getSimpleName(), pk);
            return result;
        }

        persister.put(PNode.valueOfDeletePNode(result));

        return result;
    }

    @Override
    public E uniqueQuery(String namedQuery, Object... params) {
        AssertionUtils.notNull(namedQuery);
        return (E) OrmContext.getQuery().uniqueQuery(namedQuery, params);
    }

}
