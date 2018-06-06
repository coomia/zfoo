package utils;

import com.zfoo.util.ClassUtils;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.21 18:19
 */
public class ClassUtilTest {

    @Test
    public void classLocation() throws Exception {
        String str = ClassUtils.classLocation(Integer.class);
        System.out.println(str);
    }

    @Test
    public void getAllClasses() throws Exception {
        Set<Class<?>> set = ClassUtils.getAllClasses("com.zfoo");
        for (Class<?> clazz : set) {
            System.out.println(clazz.getName());
        }
    }

    @Test
    public void test() throws Exception {
    }

}
