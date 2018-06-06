package com.zfoo.orm.model.query;

import java.util.List;

/**
 * 对数据库进行（查找）的相关方法
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 11:23
 */
public interface IQuery {

    <E> List<E> queryAll(Class<E> entityClass);

    List<?> namedQuery(String queryName, Object... params);

    Object uniqueQuery(String queryName, Object... params);

    List<?> pagedQuery(String queryName, Page page, Object... params);

}
