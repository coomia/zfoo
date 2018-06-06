package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 15:39
 */
public class ObjectA implements IPacket {

    private static final transient short PROTOCOL_ID = 6;

    private int a;

    private ObjectB objectB;

    @Override
    public String toString() {
        return "ObjectA{" + "a=" + a + ", objectB=" + objectB + '}';
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public ObjectB getObjectB() {
        return objectB;
    }

    public void setObjectB(ObjectB objectB) {
        this.objectB = objectB;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectA objectA = (ObjectA) o;
        return a == objectA.a &&
                Objects.equals(objectB, objectA.objectB);
    }

    @Override
    public int hashCode() {

        return Objects.hash(a, objectB);
    }
}
