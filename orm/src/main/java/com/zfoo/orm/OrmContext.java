package com.zfoo.orm;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.zfoo.orm.manager.IOrmManager;
import com.zfoo.orm.manager.OrmManager;
import com.zfoo.orm.model.accessor.HibernateAccessor;
import com.zfoo.orm.model.accessor.IAccessor;
import com.zfoo.orm.model.query.HibernateQuery;
import com.zfoo.orm.model.query.IQuery;
import com.zfoo.orm.schema.OrmDefinitionParser;
import com.zfoo.orm.service.IOrmService;
import com.zfoo.orm.service.OrmService;
import com.zfoo.util.ReflectionUtils;
import com.zfoo.util.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public static void shutdown() throws NoSuchFieldException, InterruptedException {
        IOrmService orm = instance.ormService;
        if (orm == null) {
            return;
        }
        Field field = OrmService.class.getDeclaredField("executor");
        ReflectionUtils.makeAccessible(field);
        ScheduledExecutorService executor = (ScheduledExecutorService) ReflectionUtils.getField(field, orm);
        if (executor != null) {
            executor.shutdown();
            // 确保所有的数据都回写到数据库
            while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                // System.out.println("线程池没有关闭");
            }
        }
    }

    public static void shutdownDataSource() throws SQLException, InterruptedException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            DriverManager.deregisterDriver(driver);
        }
        AbandonedConnectionCleanupThread.shutdown();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
