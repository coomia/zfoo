package com.zfoo.pathmatch;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 08.29 10:53
 */
public class PathMatchTest {

    @Test
    public void resourceTest() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:com/zfoo/**/*.class");
        for (Resource res : resources) {
            System.out.println(res.getDescription());
        }
    }

}
