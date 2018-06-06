package com.zfoo.event.manager;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.event.model.event.IEvent;
import com.zfoo.event.model.vo.EventReceiverDefintion;
import com.zfoo.event.model.vo.IEventReceiver;
import com.zfoo.event.util.EnhanceUtils;
import com.zfoo.util.ReflectionUtils;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.08 11:15
 */
public final class EventBusManager implements IEventBusManager {

    // 线程池的大小
    private static final int EXECUTORS_SIZE = Runtime.getRuntime().availableProcessors();

    private String eventBusId;
    private String eventBusPackage;

    private ExecutorService[] executors;

    private Map<Class<? extends IEvent>, List<IEventReceiver>> receiverMap;

    static class EventThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        EventThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "event-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    // 只初始化一次
    public EventBusManager(String eventBusId, String eventBusPackage) {
        this.eventBusId = eventBusId;
        this.eventBusPackage = eventBusPackage;
        receiverMap = new HashMap<>();
        executors = new ExecutorService[EXECUTORS_SIZE];

        ThreadFactory namedThreadFactory = new EventThreadFactory();
        for (int i = 0; i < executors.length; i++) {
            executors[i] = Executors.newSingleThreadExecutor(namedThreadFactory);
        }
    }


    // 同步的提交事件，事件在当前的线程处理
    @Override
    public void syncSubmit(IEvent event) {
        submit(event);
    }

    // 异步提交事件，事件不在同一个线程中处理
    @Override
    public void asyncSubmit(IEvent event) {
        executors[Math.abs(event.threadId() % EXECUTORS_SIZE)].submit(new Runnable() {
            @Override
            public void run() {
                submit(event);
            }
        });
    }

    private void submit(IEvent event) {
        List<IEventReceiver> list = receiverMap.get(event.getClass());
        if (list == null || list.size() <= 0) {
            FormattingTuple message = MessageFormatter.format("no any receivers found for [event:{}]", event.getClass());
            throw new IllegalStateException(message.getMessage());
        }
        for (IEventReceiver receiver : list) {
            receiver.invoke(event);
        }
    }


    public void registEventReceiver(Object bean) {
        try {
            Class<?> clazz = bean.getClass();
            Method[] methods = ReflectionUtils.getMethodsByAnnoInPOJOClass(clazz, EventReceiver.class);
            for (Method method : methods) {
                EventReceiverDefintion receiverDefintion = EventReceiverDefintion.valueOf(bean, method);
                Class<? extends IEvent> eventClazz = receiverDefintion.getParaClazz();
                if (!receiverMap.containsKey(eventClazz)) {
                    receiverMap.put(eventClazz, new ArrayList<>());
                }
                IEventReceiver receiver = EnhanceUtils.createEventReciver(receiverDefintion);
                receiverMap.get(eventClazz).add(receiver);
            }
        } catch (NotFoundException | CannotCompileException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}


