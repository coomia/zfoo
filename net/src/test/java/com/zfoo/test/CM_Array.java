package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Arrays;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.14 11:19
 */
public class CM_Array implements IPacket {

    private static final transient short PROTOCOL_ID = 9;

    private int[] a;

    private ObjectA[] b;

    @Override
    public String toString() {
        return "CM_Array{" + "a=" + Arrays.toString(a) + ", b=" + Arrays.toString(b) + '}';
    }

    public int[] getA() {
        return a;
    }

    public void setA(int[] a) {
        this.a = a;
    }

    public ObjectA[] getB() {
        return b;
    }

    public void setB(ObjectA[] b) {
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
        CM_Array cm_array = (CM_Array) o;
        return Arrays.equals(a, cm_array.a) && Arrays.equals(b, cm_array.b);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(a);
        result = 31 * result + Arrays.hashCode(b);
        return result;
    }
}
