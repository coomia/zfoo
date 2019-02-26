package com.zfoo.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.zfoo.util.exception.ExceptionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;
import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.13 11:52
 */
public abstract class JsonUtils {

    //只要在各个类方法中不调用configure方法，则MAPPER都是线程安全的
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //序列化
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化枚举是以toString()来输出，默认false，即默认以name()来输出
        // MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);


        //反序列化
        //当反序列化有未知属性则抛异常，true打开这个设置
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public static <T> T string2Object(String json, Class<T> clazz) {//json=json string
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[json:{}]转换为对象[class:{}]时异常[error:{}]"
                    , new Object[]{json, clazz, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
    }

    public static String object2String(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter
                    .format("将对象[object:{}]转换为json字符串时异常[error:{}]", object, e.getMessage());
            throw new RuntimeException(message.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> string2List(String json, Class<T> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        try {
            return (List<T>) MAPPER.readValue(json, collectionType);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter
                    .format("将json字符串[json:{}]转换为list异常[error:{}]", json, e.getMessage());
            throw new RuntimeException(message.getMessage());
        }
    }

    //元素不可重复
    @SuppressWarnings("unchecked")
    public static <T> Set<T> string2Set(String json, Class<T> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(HashSet.class, clazz);
        try {
            return (Set<T>) MAPPER.readValue(json, collectionType);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[json:{}]转换为Set[set:{}]异常[error:{}]",
                    new Object[]{json, clazz, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
    }

    public static <C extends Collection<T>, T> C string2Collection(String json, Class<C> collectionType, Class<T> elementType) {
        try {
            CollectionType e = MAPPER.getTypeFactory().constructCollectionType(collectionType, elementType);
            return MAPPER.readValue(json, e);
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[json:{}]转换为Collection[collection:{}]异常[error:{}]",
                    new Object[]{json, collectionType, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> string2Map(String json, Class<K> kClazz, Class<V> vClazz) {
        MapType mapType = MAPPER.getTypeFactory().constructMapType(HashMap.class, kClazz, vClazz);
        try {
            return (Map<K, V>) MAPPER.readValue(json, mapType);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[json:{}]转换为map[key:{},value:{}]异常[error:{}]",
                    new Object[]{json, kClazz, vClazz, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] string2Array(String json, Class<T> clazz) {
        ArrayType arrayType = MAPPER.getTypeFactory().constructArrayType(clazz);
        try {
            return (T[]) MAPPER.readValue(json, arrayType);
        } catch (Exception e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[{}]转换为array[element:{}]异常[error:{}]",
                    new Object[]{json, clazz, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
    }

    /**
     * 在js中获取属性名为nodeName的节点
     * <p>
     * 只遍历树的第一层节点
     *
     * @param json     json string
     * @param nodeName 节点名称
     * @return 节点名称为nodeName的json节点，没有返回空
     */
    public static JsonNode getNode(String json, String nodeName) {
        try {
            Queue<JsonNode> queue = new ArrayDeque<>();
            JsonNode rootNode = MAPPER.readTree(json);//将Json串以树状结构读入内存
            JsonNode resultNode;
            queue.add(rootNode);
            while (!queue.isEmpty()) {//深度优先遍历算法
                JsonNode pollNode = queue.poll();
                resultNode = pollNode.get(nodeName);
                if (resultNode != null) {
                    return resultNode;
                }
                Iterator<JsonNode> iterator = pollNode.elements();
                while (iterator.hasNext()) {//循环遍历子节点下的信息
                    JsonNode node = iterator.next();
                    queue.add(node);
                }
            }
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.arrayFormat("将json字符串[json:{}]转换为jsonTree[nodeName:{}]异常[error:{}]",
                    new Object[]{json, nodeName, ExceptionUtils.getStackTrace(e)});
            throw new RuntimeException(message.getMessage());
        }
        return null;
    }
}
