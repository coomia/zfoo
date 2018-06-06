package com.zfoo.orm;

import com.zfoo.orm.manager.IOrmManager;
import com.zfoo.orm.manager.OrmManager;
import com.zfoo.orm.model.accessor.HibernateAccessor;
import com.zfoo.orm.model.accessor.IAccessor;
import com.zfoo.orm.model.query.HibernateQuery;
import com.zfoo.orm.model.query.IQuery;
import com.zfoo.orm.schema.OrmDefinitionParser;
import com.zfoo.orm.service.IOrmService;
import com.zfoo.orm.service.OrmService;
import com.zfoo.util.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-16 16:45
 */
public class OrmContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static OrmContext instance;

    private ApplicationContext applicationContext;

    private IAccessor accessor;

    private IQuery query;

    private IOrmManager ormManager;

    private IOrmService ormService;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (OrmContext.instance == null) {
            OrmContext.instance = this;
            instance.applicationContext = applicationContext;

            // 初始化accessor，query，sessionFactory
            HibernateAccessor accessor = (HibernateAccessor) applicationContext.getBean(IAccessor.class);
            HibernateQuery query = (HibernateQuery) applicationContext.getBean(IQuery.class);
            SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean(OrmDefinitionParser.SESSION_FACTORY);
            accessor.setSessionFactory(sessionFactory);
            query.setSessionFactory(sessionFactory);
            instance.accessor = accessor;
            instance.query = query;

            instance.ormManager = (IOrmManager) applicationContext.getBean(StringUtils.uncapitalize(OrmManager.class.getSimpleName()));
            instance.ormService = (IOrmService) applicationContext.getBean(StringUtils.uncapitalize(OrmService.class.getSimpleName()));

            instance.ormManager.init();

        }
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    public static IAccessor getAccessor() {
        return instance.accessor;
    }

    public static IQuery getQuery() {
        return instance.query;
    }

    public static IOrmManager getOrmManager() {
        return instance.ormManager;
    }

    public static IOrmService getOrmService() {
        return instance.ormService;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
