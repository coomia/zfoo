package com.zfoo.orm.model.accessor;

import com.zfoo.orm.model.entity.IEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 对数据库进行（增，删，改）的相关方法
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-14 18:14
 */
public interface IAccessor {

    <E extends IEntity<?>> void insert(E entity);

    <E extends IEntity<?>> void batchInsert(List<E> entities);

    <E extends IEntity<?>> void update(E entity);

    <E extends IEntity<?>> void batchUpdate(List<E> entities);

    <E extends IEntity<?>> void delete(E entity);

    <E extends IEntity<?>> void batchDelete(List<E> entities);

    /**
     * 只有这一个不需要事务
     *
     * @param entityClass
     * @param pk
     * @param <PK>
     * @param <E>
     * @return
     */
    <PK extends Serializable, E extends IEntity> E load(Class<E> entityClass, PK pk);

    <PK extends Serializable, E extends IEntity> void delete(Class<E> entityClass, PK pk);

}
