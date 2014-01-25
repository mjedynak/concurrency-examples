package pl.mjedynak.concurrency.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentMapExample {

    private static final int TIMEOUT_IN_MILLIS = 500;
    private static final int KEY = 1;
    private static final String VALUE = "David de Gea";

    private ConcurrentMap<Integer, String> concurrentMap;

    @Before
    public void setUp() {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().expireAfterWrite(TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS).build();
        concurrentMap = cache.asMap();
    }
    
    @Test
    public void shouldRemoveElementWhenTimeoutExpires() throws InterruptedException {
        // when
        concurrentMap.put(KEY, VALUE);
        Thread.sleep(TIMEOUT_IN_MILLIS * 2);  // wait for the element to be removed

        // then
        assertThat(concurrentMap.containsKey(KEY), is(false));
        assertThat(concurrentMap.containsValue(VALUE), is(false));
    }

    @Test
    public void shouldNotRemoveElementWhenTimeoutDoesNotExpire() throws InterruptedException {
        // when
        concurrentMap.put(KEY, VALUE);

        // then
        assertThat(concurrentMap.containsKey(KEY), is(true));
        assertThat(concurrentMap.containsValue(VALUE), is(true));
    }
}
