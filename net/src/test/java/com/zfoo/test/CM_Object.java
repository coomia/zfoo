package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 16:00
 */
public class CM_Object implements IPacket {

    private static final transient short PROTOCOL_ID = 4;

    private int a;

    private ObjectA b;

    @Override
    public String toString() {
        return "CM_Object{" + "a=" + a + ", b=" + b + '}';
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public ObjectA getB() {
        return b;
    }

    public void setB(ObjectA b) {
        this.b = b;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CM_Object cm_object = (CM_Object) o;
        return a == cm_object.a &&
                Objects.equals(b, cm_object.b);
    }

    @Override
    public int hashCode() {

        return Objects.hash(a, b);
    }
}
