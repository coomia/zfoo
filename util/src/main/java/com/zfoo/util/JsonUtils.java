package com.zfoo.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;

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
    private static JsonNode pollNode;

    static {
        //序列化
        MAPPER.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //MAPPER.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化枚举是以toString()来输出，默认false，即默认以name()来输出
        //MAPPER.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_INDEX, true);


        //反序列化
        //当反序列化有未知属性则抛异常，ture打开这个设置
        MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public static <E> E string2Object(String js, Class<E> clazz) {//js=json string
        try {
            return MAPPER.readValue(js, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将json字符串转换为对象时异常:" + js + "-->" + "{" + clazz + "}");
        }
    }

    public static String object2String(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将对象转换为json字符串时异常:{" + object + "}");
        }
    }


    public static <E> List<E> string2List(String js, Class<E> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        try {
            return (List<E>) MAPPER.readValue(js, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将json字符串转换为list异常:" + js + ",-->" + "{" + clazz + "}");
        }
    }

    //元素不可重复
    public static <E> Set<E> string2Set(String js, Class<E> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(HashSet.class, clazz);
        try {
            return (Set<E>) MAPPER.readValue(js, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将json字符串转换为Set异常:" + js + ",-->" + "{" + clazz + "}");
        }
    }

    public static <C extends Collection<E>, E> C string2Collection(String js, Class<C> collectionType, Class<E> elementType) {
        try {
            CollectionType e = MAPPER.getTypeFactory().constructCollectionType(collectionType, elementType);
            return MAPPER.readValue(js, e);
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new RuntimeException("将json字符串转换为Collection异常:" + js + ",-->" + "{" + collectionType + "," + elementType + "}");
        }
    }

    public static <K, V> Map<K, V> string2Map(String js, Class<K> kClazz, Class<V> vClazz) {
        MapType mapType = MAPPER.getTypeFactory().constructMapType(HashMap.class, kClazz, vClazz);
        try {
            return (Map<K, V>) MAPPER.readValue(js, mapType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将json字符串转换为map异常:" + js + ",-->" + "key:" + kClazz + "," + "value:" + vClazz);
        }
    }

    public static <E> E[] string2Array(String js, Class<E> clazz) {
        ArrayType arrayType = MAPPER.getTypeFactory().constructArrayType(clazz);
        try {
            return (E[]) MAPPER.readValue(js, arrayType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将json字符串转换为array异常:" + js + ",-->" + "{" + clazz + "}");
        }
    }

    /**
     * 在js中获取属性名为nodeName的节点
     * <p>
     * 只遍历树的第一层节点
     *
     * @param js       json string
     * @param nodeName 节点名称
     * @return 节点名称为nodeName的json节点，没有返回空
     */
    public static JsonNode getNode(String js, String nodeName) {
        try {
            Queue<JsonNode> queue = new ArrayDeque<>();
            JsonNode rootNode = MAPPER.readTree(js);//将Json串以树状结构读入内存
            JsonNode resultNode = null;
            queue.add(rootNode);
            while (!queue.isEmpty()) {//深度优先遍历算法
                pollNode = queue.poll();
                resultNode = pollNode.get(nodeName);
                if (resultNode != null) {
                    return resultNode;
                }
                Iterator<JsonNode> iterator = pollNode.getElements();
                while (iterator.hasNext()) {//循环遍历子节点下的信息
                    JsonNode node = iterator.next();
                    queue.add(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json字符串转换为jsonTree异常:" + js + ",-->" + "nodeName:" + nodeName);
        }
        return null;
    }
}
