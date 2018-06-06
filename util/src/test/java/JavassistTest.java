import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.15 18:23
 */
public class JavassistTest {

    @Test
    public void test() {
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s2.add(3);
        System.out.println(s1.retainAll(s2));
        System.out.println(s1.size());
    }

}
