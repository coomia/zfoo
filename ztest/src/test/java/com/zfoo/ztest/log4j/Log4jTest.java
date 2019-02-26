package com.zfoo.ztest.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-05 16:56
 */
public class Log4jTest {


    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    /*
     这个log会输出到ztest文件夹下，但是将这个类放在src目录下执行，log就会出现在ztest同级目录
     */
    public static void main(String[] args) {
        logger.info("message:info info ............................");
        logger.debug("message:debug debug ............................");
        logger.error("message:error error ............................");
    }
}
