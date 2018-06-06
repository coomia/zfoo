package com.zfoo.storage.model.vo;

import com.zfoo.storage.model.anno.Resource;
import com.zfoo.storage.model.scheam.ResourceSchemaDef;
import com.zfoo.util.StringUtils;

import java.io.File;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 16:57
 */
public class ResourceDef {

    private final Class<?> clazz;
    private final String location;

    public ResourceDef(Class<?> clazz, ResourceSchemaDef resourceSchemaDef) {
        this.clazz = clazz;
        Resource anno = clazz.getAnnotation(Resource.class);
        String name = clazz.getSimpleName();
        this.location = resourceSchemaDef.getLocation() + File.separator + name + StringUtils.PERIOD + resourceSchemaDef.getSuffix();

    }


    public Class<?> getClazz() {
        return clazz;
    }

    public String getLocation() {
        return location;
    }

}
