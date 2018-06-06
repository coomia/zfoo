package com.zfoo.orm.model.anno;

import java.lang.annotation.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-14 20:51
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Persister {

    String value() default "default";

}
