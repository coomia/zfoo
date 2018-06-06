package com.zfoo.util.math;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 03 10 33
 */
public class Sort {
    public void bubbleSort(int[] a) {
        int arrayLength = a.length;//数组长度
        boolean isOrdered = false;//判断是否有序，如果有序，直接退出循环

        for (int i = 0; i < arrayLength - 1; i++) {//最多要冒多少次泡,精确一点的为-1
            isOrdered = true;
            for (int j = 0; j < arrayLength - i - 1; j++) {//-i是因为最上面的已经比较过，-1是为例防止数组越界
                if (a[j] > a[j + 1]) {//交换元素
                    a[j] = a[j] ^ a[j + 1];//如果两个数组索引小标相同，则交换失败
                    a[j + 1] = a[j] ^ a[j + 1];
                    a[j] = a[j] ^ a[j + 1];
                    isOrdered = false;
                }
            }
            if (isOrdered) {
                break;
            }
        }
    }
}
