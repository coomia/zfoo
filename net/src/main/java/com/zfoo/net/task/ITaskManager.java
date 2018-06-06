package com.zfoo.net.task;

import com.zfoo.net.server.model.Session;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.29 10:05
 */
public interface ITaskManager {
    void addTask(Session session, Runnable task);
}
