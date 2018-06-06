package com.zfoo.orm.model.entity;

import java.io.Serializable;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-14 18:07
 */
public interface IEntity<PK extends Serializable & Comparable<PK>> {

    PK getId();

}
