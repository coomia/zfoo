package com.zfoo.hotswap;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.zfoo.hotswap.service.HotSwapServiceMBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

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

    // 列出当前本机上运行的所有jvm实例描述
    @Test
    public void test() {
        List<VirtualMachineDescriptor> vmdList = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : vmdList) {
            System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
        }
    }

}
