package com.zfoo.scheduler.model.anno;

import java.lang.annotation.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-25 12:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Scheduler {

    /*
     任务调度器的名称，这个名称必须全局唯一，不能重复
     */
    String value();

    String cronExpression();

}
