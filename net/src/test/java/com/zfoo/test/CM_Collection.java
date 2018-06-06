package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.List;
import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.13 14:45
 */
public class CM_Collection implements IPacket {

    private static final transient short PROTOCOL_ID = 8;

    private int a;

    private List<Integer> list;

    private float z;

    private List<ObjectA> objs;


    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public List<ObjectA> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjectA> objs) {
        this.objs = objs;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public static short getProtocolId() {
        return PROTOCOL_ID;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CM_Collection that = (CM_Collection) o;
        return a == that.a && Float.compare(that.z, z) == 0 && Objects.equals(list, that.list) && Objects.equals(objs, that.objs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, list, z, objs);
    }
}
