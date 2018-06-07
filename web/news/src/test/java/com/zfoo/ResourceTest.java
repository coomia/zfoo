package com.zfoo;

import com.zfoo.news.user.manager.IUserManager;
import com.zfoo.util.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-07 13:46
 */
public class ResourceTest {

    @Test
    public void test() {
        File file = FileUtils.serachFileInProject("UserPrivilegeResource.xls");
        System.out.println(file);
    }


    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        IUserManager userManager = context.getBean(IUserManager.class);

    }
}
