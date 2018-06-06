package com.zfoo.storage.model.vo;

import com.zfoo.storage.StorageContext;
import com.zfoo.storage.interpreter.IResourceReader;
import com.zfoo.util.IOUtils;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.21 12:03
 */
public class Storage<K, V> {

    private ResourceDef definition;

    private Map<K, V> values = new HashMap<>();
    private Map<String, Map<Object, List<V>>> indexs = new HashMap<>();
    private Map<String, Map<Object, V>> uniqueIndexs = new HashMap<>();

    private IdDef idDef;
    private Map<String, IndexDef> indexsDef;


    public Storage() {
    }

    public void init(ResourceDef definition) {
        this.definition = definition;
        IResourceReader reader = StorageContext.getResourceReader();
        idDef = IdDef.valueOf(definition.getClazz());
        indexsDef = IndexDef.createResourceIndexs(definition.getClazz());


        InputStream input = null;
        try {
            Resource resource = StorageContext.getApplicationContext().getResource(definition.getLocation());
            input = resource.getInputStream();

            List<?> list = reader.read(input, definition.getClazz());

            values.clear();
            indexs.clear();
            uniqueIndexs.clear();

            for (Object object : list) {
                put((V) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeIO(input);
        }

    }

    public Collection<V> getAll() {
        return Collections.unmodifiableCollection(values.values());
    }


    public V get(K key) {
        V result = values.get(key);
        if (result == null) {
            throw new IllegalStateException("表示为:" + key + "的静态资源不存在");
        }
        return (V) result;
    }


    private V put(V value) {
        K key = (K) ReflectionUtils.getField(idDef.getField(), value);

        if (key == null) {
            throw new RuntimeException("静态资源存在id未配置的项");
        }

        if (values.containsKey(key)) {
            FormattingTuple message = MessageFormatter.format("静态资源[resource:{}]的[id:{}]重复"
                    , definition.getClazz().getSimpleName(), key);
            throw new RuntimeException(message.getMessage());
        }

        // 添加资源
        V result = values.put(key, value);

        // 添加索引
        for (IndexDef def : indexsDef.values()) {
            String indexKey = def.getKey();
            Object indexValue = ReflectionUtils.getField(def.getField(), value);
            if (def.isUnique()) {// 唯一索引
                if (!uniqueIndexs.containsKey(indexKey)) {
                    uniqueIndexs.put(indexKey, new HashMap<>());
                }
                Map<Object, V> index = uniqueIndexs.get(indexKey);
                if (index.put(indexValue, value) != null) {
                    throw new RuntimeException("资源的唯一索引重复：" + definition.getClazz().getName() + "----" + indexKey + "-----" + indexValue);
                }
            } else {// 不是唯一索引
                if (!indexs.containsKey(indexKey)) {
                    Map<Object, List<V>> map = new HashMap<>();
                    indexs.put(indexKey, map);
                }
                Map<Object, List<V>> index = indexs.get(indexKey);

                if (!index.containsKey(key)) {
                    List<V> list = new ArrayList<V>();
                    index.put(key, list);
                }
                index.get(key).add(value);
            }
        }
        return result;
    }
}
