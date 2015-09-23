package pl.mjedynak.concurrency.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class RateLimiterExample {

    private static final int ONE_PERMIT_PER_SECOND = 1;
    private static final int TWO_PERMITS_PER_SECOND = 2;
    private static final long ONE_SECOND = 1000L;
    private static final long TOLERANCE = 100L;
    private RateLimiter rateLimiter = RateLimiter.create(ONE_PERMIT_PER_SECOND);

    @Test
    public void shouldLimitInvocationsFromSingleThread() {
        long startTime = System.currentTimeMillis();

        rateLimiter.acquire();
        rateLimiter.acquire();

        long endTime = System.currentTimeMillis();
        long elapsedTimeInMillis = endTime - startTime;
        assertThat(elapsedTimeInMillis).isBetween(ONE_SECOND - TOLERANCE, ONE_SECOND + TOLERANCE);
    }

    @Test
    public void shouldLimitInvocationsFromMultipleThreads() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> rateLimiter.acquire());
        executorService.submit(() -> rateLimiter.acquire());
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        long endTime = System.currentTimeMillis();
        long elapsedTimeInMillis = endTime - startTime;
        assertThat(elapsedTimeInMillis).isBetween(ONE_SECOND - TOLERANCE, ONE_SECOND + TOLERANCE);
    }

    @Test
    public void shouldBeAbleToChangeRateLimitAtRuntime() {
        long startTime = System.currentTimeMillis();

        rateLimiter.acquire();
        rateLimiter.setRate(TWO_PERMITS_PER_SECOND);
        rateLimiter.acquire();

        long endTime = System.currentTimeMillis();
        long elapsedTimeInMillis = endTime - startTime;
        assertThat(elapsedTimeInMillis).isLessThanOrEqualTo(ONE_SECOND);
    }
}
