package pl.mjedynak.concurrency.dynamicThreadPool;


import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DynamicThreadPool<T> {

    private final ThreadPoolExecutor executor;

    public DynamicThreadPool(int maxThreads, int keepAliveTimeInMillis) {
        executor = new ThreadPoolExecutor(0, maxThreads, keepAliveTimeInMillis, MILLISECONDS, new SynchronousQueue<>());
        executor.setRejectedExecutionHandler((r, theExecutor) -> {
            try {
                theExecutor.getQueue().put(r);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public Future<T> submitTask(Callable<T> task) {
        return executor.submit(task);
    }

    public int threadsCount() {
        return executor.getPoolSize();
    }
}
