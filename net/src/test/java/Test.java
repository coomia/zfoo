import com.zfoo.util.security.MD5Utils;

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
        System.out.println(MD5Utils.bytesToMD5("account=jaysunxiao@gmail.com&adult_flag=1&country=*&game_id=346&game_time=0&ip=172.25.49.158&lang=en-us&sid=2625420002&time=1534498328&uuzu_op_id=590Dn5lbIjEhyFnrPL5".getBytes()).toLowerCase());
    }

}
