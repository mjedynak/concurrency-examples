package pl.mjedynak.concurrency.dynamicThreadPool;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class DynamicThreadPoolTest {

    private static final int MAX_THREADS = 2;
    private static final int KEEP_ALIVE_TIME_IN_MILLISECONDS = 300;
    private static final long DELAY_TIME_IN_MILLISECONDS = 50;
    private DynamicThreadPool<String> dynamicThreadPool
            = new DynamicThreadPool<>(MAX_THREADS, KEEP_ALIVE_TIME_IN_MILLISECONDS);

    private static final Callable<String> TASK_WITH_DELAY = () -> {
        simulateDelay(DELAY_TIME_IN_MILLISECONDS);
        return randomUUID().toString();
    };

    private static void simulateDelay(long delayTimeInMilliseconds) throws InterruptedException {
        MILLISECONDS.sleep(delayTimeInMilliseconds);
    }

    @Test
    public void shouldHaveZeroThreadsAtTheBeginning() {
        int activeThreads = dynamicThreadPool.threadsCount();
        assertThat(activeThreads).isEqualTo(0);
    }

    @Test
    public void shouldComputeConcurrently() throws Exception {
        Future<String> firstFuture = dynamicThreadPool.submitTask(TASK_WITH_DELAY);
        Future<String> secondFuture = dynamicThreadPool.submitTask(TASK_WITH_DELAY);

        String firstResult = firstFuture.get();
        String secondResult = secondFuture.get(DELAY_TIME_IN_MILLISECONDS / 2, MILLISECONDS);
        assertThat(firstResult).isNotNull();
        assertThat(secondResult).isNotNull();
    }

    @Test
    public void shouldDynamicallyIncreaseThreadsNumber() {
        dynamicThreadPool.submitTask(TASK_WITH_DELAY);
        dynamicThreadPool.submitTask(TASK_WITH_DELAY);

        await().atMost(KEEP_ALIVE_TIME_IN_MILLISECONDS, SECONDS)
                .until(() -> dynamicThreadPool.threadsCount() == MAX_THREADS);
    }

    @Test
    public void shouldHaveZeroThreadsWhenNoActiveTasks() {
        dynamicThreadPool.submitTask(() -> randomUUID().toString());
        dynamicThreadPool.submitTask(() -> randomUUID().toString());

        await().atMost(KEEP_ALIVE_TIME_IN_MILLISECONDS * 2, SECONDS)
                .until(() -> dynamicThreadPool.threadsCount() == 0);
    }

    @Test
    public void shouldBlockAddingTasksWhenMaxThreadsCountExceeded() throws Exception {
        dynamicThreadPool.submitTask(TASK_WITH_DELAY);
        dynamicThreadPool.submitTask(TASK_WITH_DELAY);
        long startTime = System.currentTimeMillis();
        Future<String> lastTaskFuture = dynamicThreadPool.submitTask(TASK_WITH_DELAY);
        String result = lastTaskFuture.get();
        long timeElapsed = System.currentTimeMillis() - startTime;

        assertThat(timeElapsed).isGreaterThan(DELAY_TIME_IN_MILLISECONDS);
        assertThat(result).isNotNull();
    }

}
