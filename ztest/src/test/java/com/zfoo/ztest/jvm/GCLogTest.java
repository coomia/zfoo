package com.zfoo.ztest.jvm;

import com.zfoo.util.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-22 22:24
 */
@Ignore
public class GCLogTest {

    /**
     * VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * <p>
     * 执行到d的时候发生了Minor GC
     */
    @Test
    public void testMinorGC() {
        byte[] a, b, c, d;
        a = new byte[IOUtils.BYTES_PER_MB * 2];
        b = new byte[IOUtils.BYTES_PER_MB * 2];
        c = new byte[IOUtils.BYTES_PER_MB * 2];
        d = new byte[IOUtils.BYTES_PER_MB * 4];
    }


    /**
     * VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * <p>
     * 大对象直接进入老年代，tenure  [ten·ure || 'te‚njə(r)]，n.  占有; 占有期; 占有权; 任期
     */
    @Test
    public void testPrentenureSizeThreshold() {
        byte[] a;
        a = new byte[IOUtils.BYTES_PER_MB * 8];
    }


    /**
     * VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
     * <p>
     * 长期存活的对象将进入老年代
     */
    @Test
    public void testTenuringThreshold() {
        byte[] a, b, c, d;
        for(int i=0;i<10;i++) {
            a = new byte[IOUtils.BYTES_PER_MB / 4];
            b = new byte[IOUtils.BYTES_PER_MB * 4];
            c = new byte[IOUtils.BYTES_PER_MB * 4];
            c = null;
            c = new byte[IOUtils.BYTES_PER_MB * 4];
        }
    }

}
