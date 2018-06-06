package com.zfoo.hotswap;

import com.zfoo.hotswap.service.HotSwapServiceMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaysunxiao
 * @date 2017-8-12  14:13:02
 * @des
 */
public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) throws Exception {
        TestA a = new TestA();
        a.print();
        while (true) {
            logger.info("hello");
            HotSwapServiceMBean.getSingleInstance().hotSwapByRelativePath("hotscript");
            a.print();
            Thread.sleep(3000);
        }
    }

}
