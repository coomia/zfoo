package com.zfoo.storage.model.anno;

import java.lang.annotation.*;

/**
 * 静态数据的注入
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.10 15:58
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ResInjection {

    String value() default "";

    boolean unique() default false;

}
