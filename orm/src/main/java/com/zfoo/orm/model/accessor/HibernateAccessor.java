package com.zfoo.orm.model.accessor;

import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.AssertionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 只要应用程序存在数据连接泄露，应用程序最终都将因为数据连接资源耗尽而崩溃。
 * <p>
 * spring承諾只要程序使用Spring DAO 模板，如JdbcTemplate，HibernateTemplate，进行数据库访问，
 * 就一定不会存在数据连接泄露的问题，这是Spring给于我们的郑重承诺。
 * 使用Spring Dao,则无须关注数据连接Connection，和其包装类Hibernate的Session的获取和释放操作,
 * 模板内已经帮我们完成了，对开发者是透明的。
 * 原理是使用了DataSourceUtils从事务的上下文获取链接，取不到再创建新的链接。如果没有事务还会泄露
 * <p>
 * Hibernate在ORM领域有着广泛的影响，它提供了ORM最完整，最丰富的实现。
 * </p>
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-14 20:38
 */
public class HibernateAccessor extends HibernateDaoSupport implements IAccessor {

    @Override
    public <E extends IEntity<?>> void insert(E entity) {
        AssertionUtils.notNull(entity);
        batchInsert(Collections.singletonList(entity));
    }

    @Override
    public <E extends IEntity<?>> void batchInsert(List<E> entities) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                int size = entities.size();
                session.beginTransaction();// Hibernate在Spring中，在没有事物管理的情况下，只能读数据而无法写数据
                for (E entity : entities) {
                    session.save(entity);
                }
                session.flush();
                session.clear();
                session.getTransaction().commit();
                return size;
            }
        });
    }

    @Override
    public <E extends IEntity<?>> void update(E entity) {
        AssertionUtils.notNull(entity);
        batchUpdate(Collections.singletonList(entity));
    }

    @Override
    public <E extends IEntity<?>> void batchUpdate(List<E> entities) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                int size = entities.size();
                session.beginTransaction();
                for (E entity : entities) {
                    session.update(entity);
                }
                session.flush();
                session.clear();
                session.getTransaction().commit();
                return size;
            }
        });
    }

    @Override
    public <E extends IEntity<?>> void delete(E entity) {
        AssertionUtils.notNull(entity);
        batchDelete(Collections.singletonList(entity));
    }


    @Override
    public <E extends IEntity<?>> void batchDelete(List<E> entities) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                int size = entities.size();
                session.beginTransaction();
                for (E entity : entities) {
                    session.delete(entity);
                }
                session.flush();
                session.clear();
                session.getTransaction().commit();
                return size;
            }
        });
    }

    @Override
    public <PK extends Serializable, E extends IEntity> E load(Class<E> entityClass, PK pk) {
        AssertionUtils.notNull(pk);
        return getHibernateTemplate().get(entityClass, pk);
    }


    @Override
    public <PK extends Serializable, E extends IEntity> void delete(Class<E> entityClass, PK pk) {
        E entity = load(entityClass, pk);
        if (entity == null) {
            FormattingTuple message = MessageFormatter.arrayFormat("数据库[{}]中没有主键[pk:{}]的记录"
                    , new Object[]{entityClass.getSimpleName(), pk});
            throw new IllegalArgumentException(message.getMessage());
        }
        delete(entity);
    }

}
