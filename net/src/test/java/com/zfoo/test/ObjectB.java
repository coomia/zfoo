package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 15:39
 */
public class ObjectB implements IPacket {

    private static final transient short PROTOCOL_ID = 7;

    private boolean flag;

    @Override
    public String toString() {
        return "ObjectB{" + "flag=" + flag + '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectB objectB = (ObjectB) o;
        return flag == objectB.flag;
    }

    @Override
    public int hashCode() {

        return Objects.hash(flag);
    }
}

