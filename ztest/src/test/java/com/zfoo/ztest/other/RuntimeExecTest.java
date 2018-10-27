package com.zfoo.ztest.other;

import com.zfoo.util.IOUtils;
import com.zfoo.util.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import sun.nio.cs.ext.GBK;

import java.io.InputStream;

/**
 * Runtime.getRuntime().exec()同步执行操作系统的外部命令
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/25
 */
@Ignore
public class RuntimeExecTest {

    @Test
    public void test() throws Exception {
        //执行命令
        Process p = Runtime.getRuntime().exec("ipconfig");

        // 其他线程都等待这个线程完成
        p.waitFor();

        // 获取javac线程的退出值，0代表正常退出，非0代表异常中止
        int exitValue = p.exitValue();

        // 返回编译是否成功
        if (exitValue != 0) {
            throw new Exception("执行命令错误，返回码：" + exitValue);
        }

        //取得命令结果的输出流
        InputStream inputStream = p.getInputStream();

        byte[] bytes = IOUtils.toByteArray(inputStream);

        System.out.println(new String(bytes, new GBK()));
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
        System.out.println(new String(bytes));
        System.out.println(StringUtils.MULTIPLE_HYPHENS);
    }

    @Test
    public void testErrorExec() throws Exception {
        //执行命令
        Process p = Runtime.getRuntime().exec("javac");

        // 其他线程都等待这个线程完成
        p.waitFor();

        // 获取javac线程的退出值，0代表正常退出，非0代表异常中止
        int exitValue = p.exitValue();

        // 返回编译是否成功
        if (exitValue != 0) {
            InputStream errStream = p.getErrorStream();
            byte[] bytes = IOUtils.toByteArray(errStream);

            FormattingTuple message = MessageFormatter.arrayFormat("执行命令错误，errorValue:[{}]，error:[{}]，返回码："
                    , new Object[]{new String(bytes, new GBK()), exitValue});
            throw new Exception(message.getMessage());
        }
    }


}
