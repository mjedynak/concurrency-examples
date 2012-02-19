package pl.mjedynak.concurrency.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchExample {

    public static void main(String[] args) throws InterruptedException {

        int threadsNumber = 2;
        final CountDownLatch latch = new CountDownLatch(threadsNumber);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    Math.pow(Math.random(), 2);
                }
                System.out.println(Thread.currentThread().getName() + " finished");
                latch.countDown();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
        executorService.submit(runnable);
        executorService.submit(runnable);

        latch.await();

        System.out.println("Both threads finished computing.");
    }
}
