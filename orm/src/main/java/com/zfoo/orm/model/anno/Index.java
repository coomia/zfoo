package com.zfoo.orm.model.anno;

import java.lang.annotation.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-15 09:56
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Index {

    String namedQuery();

}
