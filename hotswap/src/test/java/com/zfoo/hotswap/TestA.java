package com.zfoo.hotswap;

/**
 * @author jaysunxiao
 * @date 2017-8-12  14:12:23
 * @des
 */
public class TestA {

    private int a;

    private TestB testB = new TestB();

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void print() {
        System.out.println("11111111");
        testB.print();
    }
}
