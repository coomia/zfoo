package com.zfoo.event.model.anno;

import java.lang.annotation.*;

/**
 * 接受时间注解
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 10:34
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EventReceiver {
}
