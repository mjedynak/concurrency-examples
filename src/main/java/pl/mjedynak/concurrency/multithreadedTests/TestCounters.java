package pl.mjedynak.concurrency.multithreadedTests;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

public class TestCounters {
    private static final int THREADS_COUNT = 5;
    private Counter counter;

    @Before
    public void resetCounter() {
        counter = new DumbCounter();
//        counter = new SmartCounter();
    }

    @Test
    public void test() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        int last = counter.incrementAndGet();
                        for (int i = 0; i < 1000000; i++) {
                            int value = counter.incrementAndGet();
                            assertTrue(value > last);
                            last = value;
                        }
                    } finally {
                        latch.countDown();
                    }
                }
            }).start();
        }

        latch.await();
    }

    @Test
    public void testAsync() throws InterruptedException {
        AsynchTester[] testers = new AsynchTester[THREADS_COUNT];
        for(int i = 0;i < THREADS_COUNT; i++) {
            testers[i] = new AsynchTester(new Runnable() {

                @Override
                public void run() {
                    int last = counter.incrementAndGet();
                    for (int i = 0; i < 1000000; i++) {
                        int value = counter.incrementAndGet();
                        assertTrue ("v=" + value + " l=" + last, value > last);
                        last = value;
                    }
                }
            });
            testers[i].start();
        }

        for(AsynchTester tester : testers)
            tester.test();
    }


}


