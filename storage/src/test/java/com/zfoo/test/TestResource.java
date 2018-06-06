package com.zfoo.test;

import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.04 17:37
 */
@Component
public class TestResource {

    @ResInjection
    Storage<String, StudentResource> resources;

    public void printAll() {
        for (StudentResource resource : resources.getAll()) {
            System.out.println(resource);
        }
    }

}
