package com.zfoo.ztest.logback;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-05 16:56
 */
@Ignore
public class LogbackTest {


    private static final Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    /*
     这个log会输出到ztest文件夹下，但是将这个类放在src目录下执行，log就会出现在ztest同级目录
     */

    @Test
    public void test() {
        logger.info("message:info info ............................");
        logger.debug("message:debug debug ............................");
        logger.warn("message:warn warn ............................");
        logger.error("message:error error ............................");
    }
}
