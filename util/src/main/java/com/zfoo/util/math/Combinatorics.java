package com.zfoo.util.math;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 03 10 30
 */
public class Combinatorics {// |kɒmbinəˋtɒ:riks|n.组合学


    //输出数组a的startIndex~endIndex(包括端点)的全排列
    public void permutation(int[] a, int startIndex, int endIndex) {

        if (startIndex == endIndex) {
            for (int i = 0; i <= endIndex; i++) {
                System.out.print(a[i]);
            }
            System.out.println();
        }
        for (int i = startIndex; i <= endIndex; i++) {
            swap(a, i, startIndex);
            permutation(a, startIndex + 1, endIndex);
            swap(a, i, startIndex);
        }
    }

    //输出数组a的全排列
    public void permutation(int[] a) {
        permutation(a, 0, a.length - 1);
    }


    //public void Combination(int[] a,int[] b,int)

    //交换数组中的两个元素
    public void swap(int[] a, int xIndex, int yIndex) {
        int temp = a[xIndex];
        a[xIndex] = a[yIndex];
        a[yIndex] = temp;
    }


}
