package com.zfoo;

import com.zfoo.test.TestResource;
import org.junit.Ignore;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 06.27 16:17
 */

@Ignore
public class ExcelResourceTest {

    @Test
    public void excelTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_resource_reader.xml");

        TestResource testResource = context.getBean(TestResource.class);

        testResource.printAll();
    }
}
