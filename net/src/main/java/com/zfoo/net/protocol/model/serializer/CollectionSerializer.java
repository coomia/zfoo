package com.zfoo.net.protocol.model.serializer;

import com.zfoo.net.protocol.model.protocol.CollectionField;
import com.zfoo.net.protocol.model.protocol.IFieldRegistration;
import com.zfoo.net.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 15:28
 */
public class CollectionSerializer implements ISerializer {

    private static final CollectionSerializer SERIALIZER = new CollectionSerializer();


    private CollectionSerializer() {

    }

    public static CollectionSerializer getInstance() {
        return SERIALIZER;
    }

    @Override
    public Object readObject(ByteBuf buffer, IFieldRegistration fieldRegistration) {
        if (!ByteBufUtils.readBoolean(buffer)) {
            return null;
        }

        int size = ByteBufUtils.readInt(buffer);
        CollectionField collectionField = (CollectionField) fieldRegistration;
        Collection<Object> collection = collectionField.createInstance();

        ISerializer genericTypeSerializer = collectionField.getGenericTypeSerializer();
        if (genericTypeSerializer == null) {// 代表是其它协议
            for (int i = 0; i < size; i++) {
                Object value = FieldSerializer.getInstance().readObjectByProtocolId(buffer, collectionField.getProtocolId());
                collection.add(value);
            }
        } else {// 默认注册协议类型，int，float
            for (int i = 0; i < size; i++) {
                Object value = genericTypeSerializer.readObject(buffer, fieldRegistration);
                collection.add(value);
            }
        }

        return collection;
    }

    @Override
    public void writeObject(ByteBuf buffer, Object object, IFieldRegistration fieldRegistration) {
        if (object == null) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }

        Collection<?> collection = (Collection<?>) object;
        CollectionField collectionField = (CollectionField) fieldRegistration;

        int size = collection.size();
        if (size == 0) {
            ByteBufUtils.writeBoolean(buffer, false);
            return;
        }
        ByteBufUtils.writeBoolean(buffer, true);
        ByteBufUtils.writeInt(buffer, size);

        // Collection中只能包含同一种类型的元素,****这边的检查可能会有一点影响性能，上线的时候可以去掉
        // Iterator<?> iterator = collection.iterator();
        // Class<?> elementClazz = fistElement.getClass();
        // Object fistElement = iterator.next();
        // while (iterator.hasNext()) {
        //     Object element = iterator.next();
        //     if (!element.getClass().equals(elementClazz)) {
        //         throw new IllegalArgumentException(object.getClass().getCanonicalName() + " Collection中只能包含同一种病类型的元素：" + object.getClass().getCanonicalName() + "-->" + elementClazz.getCanonicalName());
        //     }
        // }


        ISerializer serializer = collectionField.getGenericTypeSerializer();

        if (serializer != null) {// 默认注册协议类型，int，float
            for (Object element : collection) {
                serializer.writeObject(buffer, element, collectionField);
            }
            return;
        }

        for (Object element : collection) {// 代表是其它协议
            FieldSerializer.getInstance().writeObject(buffer, element, collectionField);
        }
    }
}
