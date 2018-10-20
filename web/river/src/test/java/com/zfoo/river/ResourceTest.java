package com.zfoo.river;

import com.zfoo.river.module.user.manager.IUserManager;
import com.zfoo.util.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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


    // webapp的资源在webapp目录下，而不是src/main/resources目录
    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        IUserManager userManager = context.getBean(IUserManager.class);

        Assert.assertNotNull(userManager);
    }
}
