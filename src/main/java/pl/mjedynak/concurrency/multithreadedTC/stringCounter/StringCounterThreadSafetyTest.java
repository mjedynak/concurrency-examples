package pl.mjedynak.concurrency.multithreadedTC.stringCounter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class StringCounterThreadSafetyTest {
    private static final String STRING = "someString";
    private static final int THREADS_NUMBER = 5;


    @Repeat(100)
    @Test
    public void shouldBeThreadSafe() throws InterruptedException {
        // given
        final CountDownLatch startLatch = new CountDownLatch(THREADS_NUMBER);
        final CountDownLatch endLatch = new CountDownLatch(THREADS_NUMBER);
        final StringCounter stringCounter = new StringCounter();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                startLatch.countDown();
                stringCounter.addString(STRING);
                endLatch.countDown();
            }
        };


        // when
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(r);
        }
        endLatch.await();
        int result = stringCounter.getCounterFor(STRING);

        // then
        assertThat(result, is(THREADS_NUMBER));

    }


}
