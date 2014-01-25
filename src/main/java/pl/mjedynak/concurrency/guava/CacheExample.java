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

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.mjedynak.concurrency.guava.Service.SERVICE_PROCESSING_TIME;

@RunWith(MockitoJUnitRunner.class)
public class CacheExample {

    private static final long CACHE_SIZE = 2;
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
}


class Service {
    public static final long SERVICE_PROCESSING_TIME = 300;
    public String getData(String key) {
        System.out.println("accessing " + key);
        try {
            Thread.sleep(SERVICE_PROCESSING_TIME);
        } catch (InterruptedException e) {
        }
        return "value";
    }
}
