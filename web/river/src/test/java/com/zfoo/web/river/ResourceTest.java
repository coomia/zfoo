package com.zfoo.web.river;

import com.zfoo.util.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
@Ignore
public class ResourceTest {

    @Test
    public void test() {
        File file = FileUtils.serachFileInProject("UserPrivilegeResource.xls");
        System.out.println(file);
    }


}
