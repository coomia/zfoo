import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.15 15:34
 */
public class Test {

    private static final int HASH_INCREMENT = 0x61c88647;
    private static AtomicInteger nextHashCode = new AtomicInteger();

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    public static void main(String[] args) {
        for (int j = 0; j < 31; j++) {
            int size = 64;
            // hash = 0;
            int[] indexArray = new int[ size ];
            for (int i = 0; i < size; i++) {
                indexArray[ i ] = nextHashCode() & (size - 1);
            }
            for (int a = 0; a < size; a++) {
                int temp = indexArray[ a ];
                for (int b = 0; b < size; b++) {
                    if (a != b && temp == indexArray[ b ]) {
                        System.out.println("error:a -- b" + a + "," + b);
                    }
                }
            }
            System.out.println("indexs = " + Arrays.toString(indexArray));
        }
    }

}
