package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 10:45
 */
public class CM_Int implements IPacket {

    private static final transient short PROTOCOL_ID = 0;

    private boolean flag;

    private byte a;

    private short b;

    private int c;

    private long d;

    @Override
    public String toString() {
        return "CM_Int{" + "flag=" + flag + ", a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", f='" + f + '\'' + '}';
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    private char e;

    private String f;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public byte getA() {
        return a;
    }

    public char getE() {
        return e;
    }

    public void setE(char e) {
        this.e = e;
    }

    public void setA(byte a) {
        this.a = a;
    }

    public short getB() {
        return b;
    }

    public void setB(short b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public long getD() {
        return d;
    }

    public void setD(long d) {
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
        CM_Int cm_int = (CM_Int) o;
        return flag == cm_int.flag &&
                a == cm_int.a &&
                b == cm_int.b &&
                c == cm_int.c &&
                d == cm_int.d &&
                e == cm_int.e &&
                Objects.equals(f, cm_int.f);
    }

    @Override
    public int hashCode() {

        return Objects.hash(flag, a, b, c, d, e, f);
    }
}
