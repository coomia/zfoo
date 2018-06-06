package com.zfoo.storage.model.vo;


import com.zfoo.util.ReflectionUtils;
import com.zfoo.storage.model.anno.Index;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 14:50
 */
public class IndexDef {

    private String key;
    private boolean unique;
    private Field field;


    public IndexDef(Field field) {
        ReflectionUtils.makeAccessible(field);
        this.field = field;
        Index index = field.getAnnotation(Index.class);
        this.key = index.key();
        this.unique = index.unique();
    }

    public static Map<String, IndexDef> createResourceIndexs(Class<?> clazz) {
        Field[] fields = ReflectionUtils.getFieldsByAnnoInPOJOClass(clazz, Index.class);
        List<IndexDef> indexs = new ArrayList<>(fields.length);


        for (Field field : fields) {
            IndexDef indexDef = new IndexDef(field);
            indexs.add(indexDef);
        }

        Map<String, IndexDef> result = new HashMap<>();
        for (IndexDef index : indexs) {
            if (result.put(index.getKey(), index) != null) {
                throw new RuntimeException("资源类[" + clazz.getName() + "]索引名称重复,索引名" + index.getKey());
            }
        }

        return result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
