package pl.mjedynak.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteLockExampleRunner {
    public static void main(String[] args) {
        final ReadWriteLockExample example = new ReadWriteLockExample();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                example.showInfoAboutBook();
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                example.setNumberOfPages(301);
            }
        };

        for (int i = 0; i < 100; i++) {
            executor.submit(readTask);
            if (i % 10 == 0) {
                executor.submit(writeTask);
            }
        }
        executor.shutdown();
    }
}
