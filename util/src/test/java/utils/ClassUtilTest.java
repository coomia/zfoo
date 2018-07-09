package utils;

import com.zfoo.util.ClassUtils;
import com.zfoo.util.StringUtils;
import org.junit.Test;

import java.util.Set;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.21 18:19
 */
public class ClassUtilTest {

    @Test
    public void classLocation() throws Exception {
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
        System.out.println("某个类的精确位置测试：");
        String str = ClassUtils.classLocation(Integer.class);
        System.out.println(str);
        System.out.println(StringUtils.MULTIPLE_HYPHENS);

    }

    @Test
    public void getAllClasses() throws Exception {
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
        System.out.println("某个包下的所有类查找测试：");
        Set<Class<?>> set = ClassUtils.getAllClasses("com.zfoo");
        for (Class<?> clazz : set) {
            System.out.println(clazz.getName());
        }
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
    }

}
