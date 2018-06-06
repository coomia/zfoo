package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 16:00
 */
public class CM_Float implements IPacket {

    private static final transient short PROTOCOL_ID = 2;

    private float a;

    private Float b;

    private double c;

    private Double d;

    @Override
    public String toString() {
        return "CM_Float{" + "a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + '}';
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public Float getB() {
        return b;
    }

    public void setB(Float b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CM_Float cm_float = (CM_Float) o;
        return Float.compare(cm_float.a, a) == 0 &&
                Double.compare(cm_float.c, c) == 0 &&
                Objects.equals(b, cm_float.b) &&
                Objects.equals(d, cm_float.d);
    }

    @Override
    public int hashCode() {

        return Objects.hash(a, b, c, d);
    }
}
