package com.zfoo.hotswap;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.zfoo.hotswap.service.HotSwapServiceMBean;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author jaysunxiao
 * @since 2017-8-12  14:13:02
 */
public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

    // VM: -Djdk.attach.allowAttachSelf=true
    // argument: --illegal-access=permit
    @Ignore
    @Test
    public void testHotSwap() throws InterruptedException {
        TestA a = new TestA();
        a.print();
        while (true) {
            logger.info("hello");
            HotSwapServiceMBean.getSingleInstance().hotSwapByRelativePath("hotscript");
            a.print();
            Thread.sleep(3000);
        }
    }



    // 列出当前本机上运行的所有jvm实例描述
    @Ignore
    @Test
    public void test() {
        List<VirtualMachineDescriptor> vmdList = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : vmdList) {
            System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
        }
    }

}

