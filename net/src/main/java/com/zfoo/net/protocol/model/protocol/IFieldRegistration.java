package com.zfoo.net.protocol.model.protocol;

import com.zfoo.net.protocol.model.serializer.ISerializer;

import java.lang.reflect.Field;

/**
 * 标记性接口，所有协议里描述变量都要实现这个接口
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.13 09:54
 */
public interface IFieldRegistration {

    ISerializer serializer();

    Field field();
}
