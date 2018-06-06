package com.zfoo.orm.schema;

import com.zfoo.orm.OrmContext;
import com.zfoo.orm.manager.OrmManager;
import com.zfoo.orm.model.accessor.HibernateAccessor;
import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.vo.CacheDef;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.orm.model.vo.PersisterDef;
import com.zfoo.orm.model.query.HibernateQuery;
import com.zfoo.orm.service.OrmService;
import com.zfoo.util.AssertionUtils;
import com.zfoo.util.DomUtils;
import com.zfoo.util.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 13 17 36
 */
public class OrmDefinitionParser extends AbstractBeanDefinitionParser {

    public static final String ORM = "orm";

    // 数据库配置的sessionFactory的Bean
    public static final String SESSION_FACTORY = "sessionFactory";

    // orm相关的标签名称
    private static final String SESSION = "session";

    private static final String CACHES = "caches";
    private static final String CACHE = "cache";
    private static final String CACHE_NAME = "name";
    private static final String CACHE_SIZE = "size";

    private static final String PERSISTERS = "persisters";
    private static final String PERSIETER = "persister";
    private static final String PERSISTER_NAME = "name";
    private static final String PERSISTER_TYPE = "type";
    private static final String PERSISTER_CONFIG = "config";

    private static final String ENTITIES = "entities";
    private static final String ENTITY_PACKAGE = "entity-package";

    private static final String PREFIX_PATTERN = "classpath*:";
    private static final String SUFFIX_PATTERN = "**/*.class";


    private static ResourcePatternResolver resourcePatternResolver;
    private static MetadataReaderFactory metadataReaderFactory;

    static {
        resourcePatternResolver = new PathMatchingResourcePatternResolver();
        metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        String session = element.getAttribute(SESSION);

        Class<?> clazz;
        String name;
        BeanDefinitionBuilder builder;

        // 注册OrmSpringContext
        clazz = OrmContext.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册OrmProcessor
        clazz = OrmProcessor.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册HibernateAccessor
        clazz = HibernateAccessor.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addPropertyReference(SESSION_FACTORY, session);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册HibernateQuery
        clazz = HibernateQuery.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addPropertyReference(SESSION_FACTORY, session);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 解析CACHES标签
        Map<String, Integer> cacheMap = new HashMap<>();
        Element caches = DomUtils.getFirstChildElementByTagName(element, CACHES);
        List<Element> cacheList = DomUtils.getChildElementsByTagName(caches, CACHE);
        for (Element e : cacheList) {
            String cahceName = e.getAttribute(CACHE_NAME);
            int cacheSize = Integer.valueOf(e.getAttribute(CACHE_SIZE));
            Integer previousSize = cacheMap.putIfAbsent(cahceName, cacheSize);
            AssertionUtils.isNull(previousSize, "orm标签中的caches标签中不能含有重复的[cache:{}]", cahceName);
        }

        // 解析PERSISTERS标签
        Map<String, PersisterDef> persisterMap = new HashMap<>();
        Element persisters = DomUtils.getFirstChildElementByTagName(element, PERSISTERS);
        List<Element> persisterList = DomUtils.getChildElementsByTagName(persisters, PERSIETER);
        for (Element e : persisterList) {
            String persisterName = e.getAttribute(PERSISTER_NAME);
            String persisterType = e.getAttribute(PERSISTER_TYPE);
            String persisterConfig = e.getAttribute(PERSISTER_CONFIG);
            PersisterDef persisterDef = PersisterDef.valueOf(persisterType, persisterConfig);
            PersisterDef previousPersisterDef = persisterMap.putIfAbsent(persisterName, persisterDef);
            AssertionUtils.isNull(previousPersisterDef, "orm标签中的persisters标签中不能含有重复的[persister:{}]", persisterName);
        }

        // 解析ENTITIES标签
        Map<Class<? extends IEntity<?>>, CacheDef> cacheDefMap = new HashMap<>();
        Element entitys = DomUtils.getFirstChildElementByTagName(element, ENTITIES);
        List<Element> entityList = DomUtils.getChildElementsByTagName(entitys, ENTITY_PACKAGE);
        for (Element e : entityList) {
            String scanLocation = e.getTextContent();
            Set<String> locationSet = getEntityLocation(scanLocation);
            for (String location : locationSet) {
                Class<?> entityClazz;
                try {
                    entityClazz = Class.forName(location);
                } catch (ClassNotFoundException exception) {
                    exception.printStackTrace();
                    throw new RuntimeException("无法获取实体类[" + location + "]");
                }
                CacheDef cacheDef = CacheDef.valueOf(entityClazz, cacheMap, persisterMap);
                CacheDef previousCacheDef = cacheDefMap.putIfAbsent((Class<? extends IEntity<?>>) entityClazz, cacheDef);
                AssertionUtils.isNull(previousCacheDef, "缓存实体不能包含重复的[class:{}]", entityClazz.getSimpleName());
            }
        }

        // 注册OrmManager
        clazz = OrmManager.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.addConstructorArgValue(cacheDefMap);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        // 注册OrmService
        clazz = OrmService.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        parserContext.getRegistry().registerBeanDefinition(name, builder.getBeanDefinition());

        AbstractBeanDefinition definition = builder.getBeanDefinition();
        return definition;
    }


    private Set<String> getEntityLocation(String scanLocation) {
        try {
            String packageSearchPath = PREFIX_PATTERN + scanLocation.replace(StringUtils.PERIOD, StringUtils.SLASH) + StringUtils.SLASH + SUFFIX_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            Set<String> result = new HashSet<>();
            String name = Cache.class.getName();
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                if (resource.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    AnnotationMetadata annoMeta = metadataReader.getAnnotationMetadata();
                    if (annoMeta.hasAnnotation(name)) {
                        ClassMetadata clazzMeta = metadataReader.getClassMetadata();
                        result.add(clazzMeta.getClassName());
                    }
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法读取实体信息:" + e);
        }
    }


}
