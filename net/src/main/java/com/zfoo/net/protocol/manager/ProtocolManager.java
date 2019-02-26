package com.zfoo.net.protocol.manager;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.protocol.model.protocol.*;
import com.zfoo.net.protocol.model.serializer.*;
import com.zfoo.net.protocol.model.xml.XmlProtocolDefinition;
import com.zfoo.net.protocol.model.xml.XmlProtocols;
import com.zfoo.util.DomUtils;
import com.zfoo.util.ReflectionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 10:59
 */
public class ProtocolManager implements IProtocolManager {

    public static final int PROTOCOL_HEAD_LENGTH = 4;// 一个int字节长度

    public static final String PROTOCOL_ID = "PROTOCOL_ID";
    public static final short MAX_PROTOCOL_NUM = Short.MAX_VALUE;

    private static final String ROOT_ELEMENT_NAME = "protocols";
    private static final String CHILD_ELEMENT_NAME = "protocol";
    private static final String ID = "id";

    private static final String LOCATION = "location";
    private static final Comparator<Field> PACKET_FIELD_COMPARATOR = new Comparator<Field>() {
        @Override
        public int compare(Field a, Field b) {
            return a.getName().compareTo(b.getName());
        }
    };

    private static final List<ProtocolRegistration> protocolList = new ArrayList<>(Short.MAX_VALUE);

    private static final Map<Class<?>, ISerializer> baseSerializerMap = new HashMap<>();// 是一个基本类型序列化器

    static {
        baseSerializerMap.put(boolean.class, BooleanSerializer.getInstance());
        baseSerializerMap.put(Boolean.class, BooleanSerializer.getInstance());
        baseSerializerMap.put(byte.class, ByteSerializer.getInstance());
        baseSerializerMap.put(Byte.class, ByteSerializer.getInstance());
        baseSerializerMap.put(short.class, ShortSerializer.getInstance());
        baseSerializerMap.put(Short.class, ShortSerializer.getInstance());
        baseSerializerMap.put(int.class, IntSerializer.getInstance());
        baseSerializerMap.put(Integer.class, IntSerializer.getInstance());
        baseSerializerMap.put(long.class, LongSerializer.getInstance());
        baseSerializerMap.put(Long.class, LongSerializer.getInstance());
        baseSerializerMap.put(float.class, FloatSerializer.getInstance());
        baseSerializerMap.put(Float.class, FloatSerializer.getInstance());
        baseSerializerMap.put(double.class, DoubleSerializer.getInstance());
        baseSerializerMap.put(Double.class, DoubleSerializer.getInstance());
        baseSerializerMap.put(char.class, CharSerializer.getInstance());
        baseSerializerMap.put(Character.class, CharSerializer.getInstance());
        baseSerializerMap.put(String.class, StringSerializer.getInstance());
        for (int i = 0; i < MAX_PROTOCOL_NUM; i++) {
            protocolList.add(null);
        }
    }


    @Override
    public void parseProtocol(String protocolLocation) {
        ApplicationContext applicationContext = NetContext.getApplicationContext();

        Resource resource = applicationContext.getResource(protocolLocation);
        try {
            XmlProtocols xmlProtocols = DomUtils.inputStream2Object(resource.getInputStream(), XmlProtocols.class);
            for(XmlProtocolDefinition definition : xmlProtocols.getProtocols()) {
                short id = definition.getId();
                String location = definition.getLocation();
                Class<?> clazz = Class.forName(location);
                if (protocolList.get(id) != null) {
                    FormattingTuple message = MessageFormatter.format("duplicate defintion [id:{}] Exception!", id);
                    throw new IllegalArgumentException(message.getMessage());
                }
                // 注册协议
                protocolList.set(id, parseProtocolRegistration(id, clazz));
            }

        } catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private ProtocolRegistration parseProtocolRegistration(short id, Class<?> clazz) throws NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 是否为一个简单的javabean
        if (!clazz.getSuperclass().equals(Object.class)) {
            FormattingTuple message = MessageFormatter.format("[class:{}]不是简单的javabean，不能继承别的类", clazz.getCanonicalName());
            throw new IllegalArgumentException(message.getMessage());
        }

        // 是否实现了IPacket接口
        if (!IPacket.class.isAssignableFrom(clazz)) {
            FormattingTuple message = MessageFormatter.format("[class:{}]没有实现接口[IPacket:{}]", clazz.getCanonicalName(), IPacket.class.getCanonicalName());
            throw new IllegalArgumentException(message.getMessage());
        }

        Field protocolIdField = clazz.getDeclaredField(PROTOCOL_ID);

        if (protocolIdField == null) {
            FormattingTuple message = MessageFormatter.format("[class:{}]没有[{}]协议序列号", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        // 是否被private修饰
        if (!Modifier.isPrivate(protocolIdField.getModifiers())) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]没有被private修饰", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        // 是否被static修饰
        if (!Modifier.isStatic(protocolIdField.getModifiers())) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]没有被static修饰", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        // 是否被final修饰
        if (!Modifier.isFinal(protocolIdField.getModifiers())) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]没有被final修饰", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        // 是否被transient修饰
        if (!Modifier.isTransient(protocolIdField.getModifiers())) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]没有被transient修饰", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        ReflectionUtils.makeAccessible(protocolIdField);
        // 协议号是否和id是否相等
        if (!protocolIdField.get(null).equals(id)) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]和协议文件里的协议序列号不相等", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }

        // 必须要有一个空的构造器
        Constructor<?> constructor = clazz.getDeclaredConstructor(null);
        if (constructor == null) {
            FormattingTuple message = MessageFormatter.format("[class:{}]协议序列号[{}]必须有一个空的构造器", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }
        IPacket packet = (IPacket) constructor.newInstance();
        if (packet.protcolId() != id) {
            FormattingTuple message = MessageFormatter.format("[class:{}]的protocolId返回的值和[{}]不相等", clazz.getCanonicalName(), PROTOCOL_ID);
            throw new IllegalArgumentException(message.getMessage());
        }


        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.equals(protocolIdField)) {
                continue;
            }
            int modifiers = field.getModifiers();
            if (Modifier.isTransient(modifiers) || Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                FormattingTuple message = MessageFormatter.format("[class:{}]一个包里只能有一个static final transient的变量，既只有PROTOCOL_ID", clazz.getCanonicalName());
                throw new IllegalArgumentException(message.getMessage());
            }
            ReflectionUtils.makeAccessible(field);
            fields.add(field);
        }

        // 按变量名称从小到大排序
        Collections.sort(fields, PACKET_FIELD_COMPARATOR);

        IFieldRegistration[] packetFields = new IFieldRegistration[fields.size()];
        for (int i = 0; i < packetFields.length; i++) {
            Field field = fields.get(i);

            Class<?> fieldTypeClazz = field.getType();

            ISerializer serializer = getBaseSerializer(fieldTypeClazz);

            if (serializer != null) {// 是一个基本类型变量
                packetFields[i] = BaseField.valueOf(field, serializer);
            } else if (Collection.class.isAssignableFrom(fieldTypeClazz)) {// Collection
                Type type = field.getGenericType();

                if (!(type instanceof ParameterizedType)) {
                    FormattingTuple message = MessageFormatter.format("类型声明不正确，不是泛型类[type:{}]", type);
                    throw new RuntimeException(message.getMessage());
                }

                Type[] types = ((ParameterizedType) type).getActualTypeArguments();

                if (types.length != 1) {
                    throw new RuntimeException("类型声明不正确，必须声明泛型类");
                }

                Class<?> genericTypeClazz = (Class<?>) types[0];

                short protocolId = -1;
                ISerializer genericTypeSerializer = getBaseSerializer(genericTypeClazz);

                if (genericTypeSerializer == null) {// 集合里是一个其它协议
                    Field referenceField = genericTypeClazz.getDeclaredField(PROTOCOL_ID);
                    if (referenceField == null) {
                        throw new RuntimeException("Collection里只能是基本类型和其它协议,int[],protocol[]");
                    }

                    ReflectionUtils.makeAccessible(referenceField);
                    protocolId = (Short) referenceField.get(null);
                }
                packetFields[i] = CollectionField.valueOf(field, protocolId, genericTypeSerializer);

            } else if (fieldTypeClazz.getComponentType() != null) {// 是一个数组
                Class<?> arrayClazz = fieldTypeClazz.getComponentType();
                short protocolId = -1;
                ISerializer arrayTypeSerializer = getBaseSerializer(arrayClazz);
                if (arrayTypeSerializer == null) {// 数组内容是一个其它协议
                    Field referenceField = arrayClazz.getDeclaredField(PROTOCOL_ID);
                    if (referenceField == null) {
                        throw new RuntimeException("数组里只能是基本类型和其它协议,int[],protocol[]");
                    }
                    ReflectionUtils.makeAccessible(referenceField);
                    protocolId = (Short) referenceField.get(null);
                }
                packetFields[i] = ArrayField.valueOf(field, protocolId, arrayTypeSerializer);
            } else if (Map.class.isAssignableFrom(fieldTypeClazz)) {// Map
                Type type = field.getGenericType();

                if (!(type instanceof ParameterizedType)) {
                    FormattingTuple message = MessageFormatter.format("类型声明不正确，不是泛型类[type:{}]", type);
                    throw new RuntimeException(message.getMessage());
                }

                Type[] types = ((ParameterizedType) type).getActualTypeArguments();

                if (types.length != 2) {
                    throw new RuntimeException("类型声明不正确，必须声明泛型类");
                }

                Class<?> keyTypeClazz = (Class<?>) types[0];
                Class<?> valueTypeClazz = (Class<?>) types[1];

                short keyProtocolId = -1;
                short valueProtocolId = -1;
                ISerializer keyTypeSerializer = getBaseSerializer(keyTypeClazz);
                ISerializer valueTypeSerializer = getBaseSerializer(valueTypeClazz);

                if (keyTypeSerializer == null) {// 集合里是一个其它协议
                    Field referenceField = keyTypeClazz.getDeclaredField(PROTOCOL_ID);
                    if (referenceField == null) {
                        throw new RuntimeException("Map里的Key只能是基本类型和其它协议,int[],protocol[]");
                    }

                    ReflectionUtils.makeAccessible(referenceField);
                    keyProtocolId = (Short) referenceField.get(null);
                }
                if (valueTypeSerializer == null) {// 集合里是一个其它协议
                    Field referenceField = valueTypeClazz.getDeclaredField(PROTOCOL_ID);
                    if (referenceField == null) {
                        throw new RuntimeException("Map里的value只能是基本类型和其它协议,int[],protocol[]");
                    }

                    ReflectionUtils.makeAccessible(referenceField);
                    valueProtocolId = (Short) referenceField.get(null);
                }
                packetFields[i] = MapField.valueOf(field, keyProtocolId, valueProtocolId, keyTypeSerializer, valueTypeSerializer);
            } else { // 是一个协议引用变量
                Field referenceField = field.getType().getDeclaredField(PROTOCOL_ID);
                ReflectionUtils.makeAccessible(referenceField);
                packetFields[i] = ReferenceField.valueOf(field, (Short) referenceField.get(null));
            }
        }

        ReflectionUtils.makeAccessible(constructor);
        ProtocolRegistration protocol = new ProtocolRegistration();
        protocol.setId(id);
        protocol.setConstructor(constructor);
        protocol.setPacketFields(packetFields);
        return protocol;
    }


    @Override
    public ProtocolRegistration getProtocolRegistration(short id) {
        return protocolList.get(id);
    }

    private ISerializer getBaseSerializer(Class<?> clazz) {
        return baseSerializerMap.get(clazz);
    }
}
