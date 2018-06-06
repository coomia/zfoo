package com.zfoo.orm.model.cache;

import com.zfoo.orm.model.entity.IEntity;

import java.io.Serializable;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 16:26
 */
public interface IEntityCaches<PK extends Comparable<PK> & Serializable, E extends IEntity<PK>> {

    E load(PK pk);

    E insert(E entity);

    void update(E entity);

    E delete(PK pk);

    E uniqueQuery(String namedQuery, Object... params);

}
