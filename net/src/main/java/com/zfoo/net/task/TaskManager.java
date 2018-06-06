package com.zfoo.net.task;

import com.zfoo.net.server.model.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 21:23
 */
public final class TaskManager implements ITaskManager {

    // 线程池的大小
    private static final int EXECUTORS_SIZE = Runtime.getRuntime().availableProcessors();

    private static final TaskManager manager;

    private static final ExecutorService[] executors;

    static {
        // 只初始化一次
        manager = new TaskManager();
        executors = new ExecutorService[ EXECUTORS_SIZE ];
        for (int i = 0; i < executors.length; i++) {
            executors[ i ] = Executors.newSingleThreadExecutor();
        }
    }

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        return manager;
    }

    @Override
    public void addTask(Session session, Runnable task) {
        executors[ Math.abs(session.getThreadId() % EXECUTORS_SIZE) ].submit(task);
    }
}
