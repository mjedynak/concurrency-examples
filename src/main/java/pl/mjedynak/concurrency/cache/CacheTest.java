package pl.mjedynak.concurrency.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CacheTest {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String KEY_2 = "key2";
    private static final String VALUE_2 = "value2";
    private static final int THREAD_POOLS_SIZE = 100;

    @Mock private DataSource<String, String> dataSource;

    private Cache<String, String> cache = new Cache<>(new Action<String, String>() {
        @Override
        public String compute(String key) {
            return dataSource.get(key);
        }
    });

    @Before
    public void before() {
        given(dataSource.get(KEY)).willReturn(VALUE);
    }

    @Test
    public void shouldGetValue() throws Exception {
        String result = cache.get(KEY);

        assertThat(result).isEqualTo(VALUE);
    }

    @Test
    public void shouldGetMultipleValues() throws Exception {
        given(dataSource.get(KEY_2)).willReturn(VALUE_2);

        String firstResult = cache.get(KEY);
        String secondResult = cache.get(KEY_2);

        assertThat(firstResult).isEqualTo(VALUE);
        assertThat(secondResult).isEqualTo(VALUE_2);
    }

    @Test
    public void shouldHaveTheSameValueForTheSameKey() throws Exception {
        String firstResult = cache.get(KEY);
        String secondResult = cache.get(KEY);

        assertThat(firstResult).isEqualTo(secondResult);
    }

    @Test
    public void shouldCallDataSourceOnlyOnce() throws Exception {
        cache.get(KEY);
        cache.get(KEY);

        verify(dataSource, times(1)).get(KEY);
    }

    @Test
    public void shouldCallDataSourceOnlyOnceInConcurrentAccess() throws Exception {
        ExecutorService executorService = newFixedThreadPool(THREAD_POOLS_SIZE);
        CountDownLatch latch = new CountDownLatch(THREAD_POOLS_SIZE);

        for (int i = 0; i < THREAD_POOLS_SIZE; i++) {
            executorService.submit((Runnable) () -> {
                try {
                    latch.countDown();
                    latch.await();
                    cache.get(KEY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        verify(dataSource, times(1)).get(KEY);
    }
}
