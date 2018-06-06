package com.zfoo.storage.schema;

import com.zfoo.storage.StorageContext;
import com.zfoo.storage.interpreter.ExcelResourceReader;
import com.zfoo.storage.manager.StorageManager;
import com.zfoo.storage.model.scheam.ResourceSchemaDef;
import com.zfoo.storage.model.scheam.ScanSchemaDef;
import com.zfoo.storage.model.vo.ResourceDef;
import com.zfoo.storage.strategy.*;
import com.zfoo.util.DomUtils;
import com.zfoo.util.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
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
 * @since 2017 07.21 10:12
 */
public class StorageDefinitionParser extends AbstractBeanDefinitionParser {

    // ANT通配符有三种, ? :匹配任何单字符; * :匹配0或者任意数量的字符; ** :匹配0或者更多的目录
    // 1. /project/*.a	匹配项目根路径下所有在project路径下的.a文件
    // 2. /project/p?ttern	匹配项目根路径下 /project/pattern 和 /app/pXttern,但是不包括/app/pttern
    // 3. /**/example	匹配项目根路径下 /project/example, /project/foow/example, 和 /example
    // 4. /project/**/dir/file.*	匹配项目根路径下/project/dir/file.jsp, /project/foow/dir/file.html,/project/foow/bar/dir/file.pdf
    // 5. /**/*.jsp	匹配项目根路径下任何的.jsp 文件
    // classpath：和classpath*： 的区别，前者只会第一个加载到的类，后者会加载所有的类，包括jar文件下的类
    private static final String PREFIX_PATTERN = "classpath*:";
    private static final String SUFFIX_PATTERN = "**/*.class";

    // 配置标签名称
    public static final String STORAGE = "storage";

    // 需要扫描的包名
    private static final String SCAN = "scan";
    private static final String SCAN_PACKAGE = "package";

    // 资源位置
    private static final String RESOURCE = "resource";
    private static final String RESOURCE_LOCATION = "location";
    private static final String RESOURCE_SUFFIX = "suffix";

    private static ResourcePatternResolver resourcePatternResolver;
    private static MetadataReaderFactory metadataReaderFactory;

    static {
        resourcePatternResolver = new PathMatchingResourcePatternResolver();
        metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    }

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

        ScanSchemaDef scanSchemaDef = parseScanDefintion(element);
        ResourceSchemaDef resourceSchemaDef = parseResourceDefinition(element);

        // 注入StorageSpringContext，ExcelResourceReader，ResInjectionProcessor，ConversionService
        registerBeanDefinition(parserContext);

        // 注入StorageManager
        BeanDefinitionBuilder storageManagerBuilder = BeanDefinitionBuilder.rootBeanDefinition(StorageManager.class);

        Map<Class<?>, ResourceDef> resourceDefinitionMap = new HashMap<>();
        Set<String> clazzNameSet = scanResourceAnno(scanSchemaDef.getPackageName());
        for (String clazzName : clazzNameSet) {
            Class<?> clazz;
            try {
                clazz = Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("无法获取资源类[" + clazzName + "]");
            }
            ResourceDef resourceDef = new ResourceDef(clazz, resourceSchemaDef);
            if (resourceDefinitionMap.containsKey(clazz)) {
                throw new RuntimeException("类的资源定义已经存在:" + clazz + "---" + resourceDef);
            }
            resourceDefinitionMap.put(clazz, resourceDef);
        }

        storageManagerBuilder.addPropertyValue(StorageManager.DEFINITIONS_PROPERTY_VALUE, resourceDefinitionMap);
        AbstractBeanDefinition definition = storageManagerBuilder.getBeanDefinition();
        return definition;
    }

    private ScanSchemaDef parseScanDefintion(Element element) {
        Element scanElement = DomUtils.getFirstChildElementByTagName(element, SCAN);
        if (scanElement == null) {
            throw new RuntimeException("XML文件缺少[" + SCAN + "]元素定义");
        }
        String packageName = scanElement.getAttribute(SCAN_PACKAGE);
        ScanSchemaDef scanSchemaDef = new ScanSchemaDef(packageName);
        return scanSchemaDef;
    }

    private ResourceSchemaDef parseResourceDefinition(Element element) {
        Element resourceElement = DomUtils.getFirstChildElementByTagName(element, RESOURCE);
        if (resourceElement == null) {
            throw new RuntimeException("XML文件缺少[" + RESOURCE + "]元素定义");
        }
        String resourceLocation = resourceElement.getAttribute(RESOURCE_LOCATION);
        String suffix = resourceElement.getAttribute(RESOURCE_SUFFIX);
        ResourceSchemaDef resourceSchemaDef = new ResourceSchemaDef(resourceLocation, suffix);
        return resourceSchemaDef;
    }

    private Set<String> scanResourceAnno(String scanLocation) {
        try {
            String packageSearchPath = PREFIX_PATTERN + scanLocation.replace(StringUtils.PERIOD, StringUtils.SLASH) + StringUtils.SLASH + SUFFIX_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            Set<String> result = new HashSet<>();
            String name = com.zfoo.storage.model.anno.Resource.class.getName();
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
            throw new RuntimeException("无法读取资源信息:" + e);
        }
    }

    private void registerBeanDefinition(ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();

        Class<?> clazz;
        String name;
        BeanDefinitionBuilder builder;

        // 注入StorageSpringContext
        clazz = StorageContext.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        registry.registerBeanDefinition(name, builder.getBeanDefinition());

        // 注入ExcelResourceReader
        clazz = ExcelResourceReader.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        registry.registerBeanDefinition(name, builder.getBeanDefinition());

        // 注入StaticInjectProcessor
        clazz = ResInjectionProcessor.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        registry.registerBeanDefinition(name, builder.getBeanDefinition());

        // 注入ConversionServiceFactoryBean
        clazz = ConversionServiceFactoryBean.class;
        name = StringUtils.uncapitalize(clazz.getSimpleName());
        builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        List<Object> converters = new ArrayList<>();
        converters.add(new JsonToArrayConverter());
        converters.add(new JsonToMapConverter());
        converters.add(new StringToClassConverter());
        converters.add(new StringToDateConverter());
        converters.add(new StringToMapConverter());
        builder.addPropertyValue("converters", converters);
        registry.registerBeanDefinition(name, builder.getBeanDefinition());
    }
}
