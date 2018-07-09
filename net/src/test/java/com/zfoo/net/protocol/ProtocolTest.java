package com.zfoo.net.protocol;

import com.zfoo.net.NetContext;
import com.zfoo.net.protocol.manager.ProtocolManager;
import com.zfoo.net.protocol.service.IProtocolService;
import com.zfoo.test.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 10:52
 */
public class ProtocolTest {

    private static ObjectA objectA = new ObjectA();
    private static ObjectA objectA1 = new ObjectA();
    private static ObjectB objectB = new ObjectB();

    static {
        objectA.setA(Integer.MAX_VALUE);
        objectA.setObjectB(objectB);
        objectA1.setA(Integer.MAX_VALUE);
        objectA1.setObjectB(objectB);
        objectB.setFlag(false);
    }

    @Test
    public void testCMInt() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Int cm = new CM_Int();
        cm.setFlag(false);
        cm.setA(Byte.MIN_VALUE);
        cm.setB(Short.MIN_VALUE);
        cm.setC(Integer.MIN_VALUE);
        cm.setD(Long.MIN_VALUE);
        cm.setE('e');
        cm.setF("Hello 孙来疯，this is the World!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        ByteBuf writeBuff = Unpooled.directBuffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testSMInt() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        SM_Int sm = new SM_Int();
        sm.setFlag(true);
        sm.setA(Byte.MAX_VALUE);
        sm.setB(Short.MAX_VALUE);
        sm.setC(Integer.MAX_VALUE);
        sm.setD(Long.MAX_VALUE);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, sm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);


        // System.out.println(object);
        Assert.assertEquals(object, sm);
    }

    @Test
    public void testCMObject() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Object cm = new CM_Object();
        cm.setA(Integer.MIN_VALUE);
        cm.setB(objectA);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testCMFloatMin() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Float cm = new CM_Float();
        cm.setA(Float.MIN_VALUE);
        cm.setB(Float.MIN_VALUE);
        cm.setC(Double.MIN_VALUE);
        cm.setD(Double.MIN_VALUE);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testCMFloatNormal() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Float cm = new CM_Float();
        cm.setA((float) 0.1);
        cm.setB((float) 0.2);
        cm.setC(1.1);
        cm.setD(100.1);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testCMCollection() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Collection cm = new CM_Collection();
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(Integer.MAX_VALUE);
        list.add(6);
        cm.setList(list);
        cm.setA(-9999);
        cm.setZ((float) 0.99);

        List<ObjectA> objs = new ArrayList<>();
        ObjectA obj1 = new ObjectA();
        ObjectA obj2 = new ObjectA();
        ObjectA obj3 = new ObjectA();
        ObjectB b1 = new ObjectB();
        ObjectB b2 = new ObjectB();
        b2.setFlag(true);
        obj1.setA(1);
        obj2.setA(2);
        obj3.setA(3);
        objs.add(obj1);
        objs.add(obj2);
        objs.add(obj3);
        obj1.setObjectB(b1);
        obj2.setObjectB(b2);
        cm.setObjs(objs);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testCMArray() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Array cm = new CM_Array();
        int[] a = new int[]{1, 2, 34, 5};
        cm.setA(a);
        ObjectA[] array = new ObjectA[]{objectA, null, objectA, objectA};
        cm.setB(array);

        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

    @Test
    public void testCMMap() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_test_net.xml");
        IProtocolService protocolService = NetContext.getProtocolService();
        CM_Map cm = new CM_Map();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, ObjectA> mapA = new HashMap<>();
        HashMap<ObjectA, String> mapB = new HashMap<>();
        HashMap<ObjectA, ObjectA> mapC = new HashMap<>();
        map.put(1, 1);
        map.put(100, 333);
        mapA.put(1, objectA);
        mapA.put(2, objectA);
        mapA.put(-1, objectA);

        mapB.put(objectA, "helloA");
        mapB.put(objectA1, "helloA1");

        mapC.put(objectA, objectA);
        mapC.put(objectA1, objectA1);

        cm.setMap(map);
        cm.setMapA(mapA);
        cm.setMapB(mapB);
        cm.setMapC(mapC);


        ByteBuf writeBuff = Unpooled.buffer();
        protocolService.write(writeBuff, cm);

        writeBuff.readerIndex(ProtocolManager.PROTOCOL_HEAD_LENGTH);// 信息头的长度

        Object object = protocolService.read(writeBuff);

        // System.out.println(object);
        Assert.assertEquals(object, cm);
    }

}
