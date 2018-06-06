package utils;

import com.zfoo.util.StringUtils;
import org.testng.annotations.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 17:13
 */
public class StringUtilTest {

    @Test
    public void isEmpty() {
        System.out.println(StringUtils.isEmpty("  "));
    }

    @Test
    public void capitalize() {
        String str = "hello world!";
        System.out.println(StringUtils.capitalize(str));
    }

    @Test
    public void unCapitalize() {
        String str = "Hello world!";
        System.out.println(StringUtils.uncapitalize(str));
    }

}
