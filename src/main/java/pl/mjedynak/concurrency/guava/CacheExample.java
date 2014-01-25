package pl.mjedynak.concurrency.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.mjedynak.concurrency.guava.Service.SERVICE_PROCESSING_TIME;
import static pl.mjedynak.concurrency.guava.Service.VALUE;

@RunWith(MockitoJUnitRunner.class)
public class CacheExample {

    private static final long CACHE_SIZE = 2;
    private static final int MULTIPLE_FACTOR = 10;
    private static final int THREADS_NUMBER = MULTIPLE_FACTOR;
    private static final String KEY = "key1";

    @Spy private Service service;
    private LoadingCache<String, String> cache;

    @Before
    public void setUp() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(SERVICE_PROCESSING_TIME * 2, TimeUnit.MILLISECONDS)
                .maximumSize(CACHE_SIZE)
                .recordStats()
                .build(
                        new CacheLoader<String, String>() {
                            public String load(String key) {
                                return service.getData(key);
                            }
                        });
    }

    @Test
    public void shouldEvictOnceBasedOnTime() throws Exception {
        // when
        cache.get(KEY);
        cache.get(KEY);
        Thread.sleep(3 * SERVICE_PROCESSING_TIME);
        cache.get(KEY);

        // then
        CacheStats stats = cache.stats();
        assertThat(stats.evictionCount(), is(1L));
        verify(service, times(2)).getData(KEY);
    }

    @Test
    public void shouldEvictBasedOnSize() throws Exception {
        // when
        cache.get("key1");
        cache.get("key2");
        cache.get("key3");

        // then
        assertThat(cache.stats().evictionCount(), is(1L));
    }

    @Test
    public void shouldCopeWithManyThreads() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
        List<Future<String>> results = new ArrayList<>();

        // when
        hitCacheWithMultipleThreads(executorService, results);

        // then
        for (Future<String> result : results) {
            assertThat(result.get(), is(VALUE));
        }
        assertThat(results, hasSize(THREADS_NUMBER * MULTIPLE_FACTOR));
        verify(service, times(1)).getData(KEY);
    }

    @Test
    public void shouldCopeWithManyThreadsWithEviction() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);
        List<Future<String>> results = new ArrayList<>();

        // when
        hitCacheWithMultipleThreads(executorService, results);
        Thread.sleep(4 * SERVICE_PROCESSING_TIME);
        hitCacheWithMultipleThreads(executorService, results);

        // then
        for (Future<String> result : results) {
            assertThat(result.get(), is(VALUE));
        }
        assertThat(results, hasSize(2 * THREADS_NUMBER * MULTIPLE_FACTOR));
        assertThat(cache.stats().evictionCount(), is(1L));
        verify(service, times(2)).getData(KEY);
    }

    private void hitCacheWithMultipleThreads(ExecutorService executorService, List<Future<String>> results) {
        for (int i = 0; i < THREADS_NUMBER * MULTIPLE_FACTOR; i++) {
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return cache.get(KEY);
                }
            });
            results.add(future);
        }
    }

}


class Service {
    public static final long SERVICE_PROCESSING_TIME = 300;
    public static final String VALUE = "value";

    public String getData(String key) {
        System.out.println("accessing " + key);
        try {
            Thread.sleep(SERVICE_PROCESSING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return VALUE;
    }
}
