package com.zfoo.ztest.jvm.clazz;

import java.util.ArrayList;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/29
 */
public class AutoBoxTest {

    /**
     * public int foo();
     *   Code:
     *      0: new java/util/ArrayList
     *      3: dup
     *      4: invokespecial java/util/ArrayList."<init>":()V
     *      7: astore_1
     *      8: aload_1
     *      9: iconst_0
     *     10: invokestatic java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
     *     13: invokevirtual java/util/ArrayList.add:(Ljava/lang/Object;)Z
     *     16: pop
     *     17: aload_1
     *     18: iconst_0
     *     19: invokevirtual java/util/ArrayList.get:(I)Ljava/lang/Object;
     *     22: checkcast java/lang/Integer
     *     25: invokevirtual java/lang/Integer.intValue:()I
     *     28: istore_2
     *     29: iload_2
     *     30: ireturn
     * @return
     */

    public int foo() {
        ArrayList<Integer> list = new ArrayList<>();
        // 对于基本类型的数值来说，我们需要先将其转换为对应的包装类，再存入容器之中。在 Java 程序中，这个转换可以是显式，也可以是隐式的，后者正是 Java 中的自动装箱。
        // java.lang.Integer.IntegerCache.high这个参数将影响这里面的 IntegerCache.high。
        list.add(0);
        int result = list.get(0);
        return result;
    }


}
