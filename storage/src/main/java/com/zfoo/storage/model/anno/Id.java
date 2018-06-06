package com.zfoo.storage.model.anno;


import java.lang.annotation.*;

/**
 * 主键
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.10 14:56
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Id {
}
