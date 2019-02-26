package com.zfoo.orm.model.query;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 11:40
 */
public class HibernateQuery extends HibernateDaoSupport implements IQuery {
    @Override
    public <E> List<E> queryAll(Class<E> entityClass) {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    public List<?> namedQuery(String queryName, Object... params) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
            @Override
            public List<?> doInHibernate(Session session) throws HibernateException {
                Query query = session.getNamedQuery(queryName);
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                return query.list();
            }
        });
    }

    @Override
    public Object uniqueQuery(String queryName, Object... params) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.getNamedQuery(queryName);

                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                return query.uniqueResult();
            }
        });
    }

    @Override
    public List<?> pagedQuery(String queryName, Page page, Object... params) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
            @Override
            public List<?> doInHibernate(Session session) throws HibernateException {
                Query query = session.getNamedQuery(queryName);
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                query.setFirstResult(page.firstResult());
                query.setMaxResults(page.getSize());
                return query.list();
            }
        });
    }
}
