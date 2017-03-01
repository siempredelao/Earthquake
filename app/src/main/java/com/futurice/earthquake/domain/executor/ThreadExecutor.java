package com.futurice.earthquake.domain.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor implements Executor {

    private static final int                     CORE_POOL_SIZE  = 3;
    private static final int                     MAX_POOL_SIZE   = 5;
    private static final int                     KEEP_ALIVE_TIME = 120;
    private static final TimeUnit                TIME_UNIT       = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE      = new LinkedBlockingQueue<>();
    private static final ThreadFactory           THREAD_FACTORY  = new JobThreadFactory();

    private final ThreadPoolExecutor         threadPoolExecutor;
    private final Map<Interactor, Future<?>> interactorToTaskMap;

    public ThreadExecutor() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                                                    MAX_POOL_SIZE,
                                                    KEEP_ALIVE_TIME,
                                                    TIME_UNIT,
                                                    WORK_QUEUE,
                                                    THREAD_FACTORY);
        interactorToTaskMap = new HashMap<>();
    }

    @Override
    public void run(final Interactor interactor) {
        if (interactor == null) {
            throw new IllegalArgumentException("Interactor to execute can't be null");
        }
        final Future<?> task = threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                interactor.run();
            }
        });
        interactorToTaskMap.put(interactor, task);
    }

    @Override
    public void stop(final Interactor interactor) {
        if (interactor == null) {
            throw new IllegalArgumentException("Interactor to stop can't be null");
        }
        final Future<?> future = interactorToTaskMap.get(interactor);
        if (future != null && (!future.isDone() || !future.isCancelled())) {
            future.cancel(true);
        }
        interactorToTaskMap.remove(interactor);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private static final String THREAD_NAME = "android_";
        private              int    counter     = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + counter++);
        }
    }
}
