package com.zfoo.storage.model.anno;

import java.lang.annotation.*;

/**
 * 索引，用HaspMap<>实现
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.10 15:42
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Index {

    String key();

    boolean unique() default false;

}
