package pl.mjedynak.concurrency.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThreadCreation {

    public static void main(String[] args) {

        singleThreadExecutor();
        threadPool();

    }

    private static void threadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        executor.execute(runnable);
        executor.execute(runnable);

        executor.shutdown();
    }

    private static void singleThreadExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        executor.execute(runnable);

        // executing once again
        executor.execute(runnable);

        executor.shutdown();
    }
}
