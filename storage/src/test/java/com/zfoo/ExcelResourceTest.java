package com.zfoo;

import com.zfoo.test.StudentResource;
import com.zfoo.test.TestResource;
import com.zfoo.util.JsonUtils;
import com.zfoo.util.StringUtils;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 06.27 16:17
 */

@Ignore
public class ExcelResourceTest {

    private static final Logger logger = LoggerFactory.getLogger(ExcelResourceTest.class);

    @Test
    public void excelTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_resource_reader.xml");

        TestResource testResource = context.getBean(TestResource.class);

        testResource.printAll();

        System.out.println(StringUtils.MULTIPLE_HYPHENS);

        for(StudentResource resource : testResource.getAllStudentResource()) {
            logger.info("{}", JsonUtils.object2String(resource));
        }
    }
}
