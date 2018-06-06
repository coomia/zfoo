package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 10:51
 */
public class SM_Int implements IPacket {

    private static final transient short PROTOCOL_ID = 1;

    private Boolean flag;

    private Byte a;

    private Short b;

    private Integer c;

    private Long d;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Byte getA() {
        return a;
    }

    public void setA(Byte a) {
        this.a = a;
    }

    public Short getB() {
        return b;
    }

    public void setB(Short b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Long getD() {
        return d;
    }

    public void setD(Long d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "SM_Int{" + "flag=" + flag + ", a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + '}';
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SM_Int sm_int = (SM_Int) o;
        return Objects.equals(flag, sm_int.flag) &&
                Objects.equals(a, sm_int.a) &&
                Objects.equals(b, sm_int.b) &&
                Objects.equals(c, sm_int.c) &&
                Objects.equals(d, sm_int.d);
    }

    @Override
    public int hashCode() {

        return Objects.hash(flag, a, b, c, d);
    }
}

