package pl.mjedynak.concurrency.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcurrentMapExampleTest {

    @Test
    public void shouldAddToMap() {
        // given
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        ConcurrentMap<Integer, String> concurrentMap = cache.asMap();

        // when
        concurrentMap.put(1, "David de Gea");

        // then
        assertThat(concurrentMap.size(), is(1));
    }

    @Ignore
    @Test
    public void shouldRemoveElementWhenTimeoutExpires() throws InterruptedException {
        // given
        Cache<Integer, String> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS).build();
        ConcurrentMap<Integer, String> concurrentMap = cache.asMap();

        // when
        concurrentMap.put(2, "Rafael da Silva");
        Thread.sleep(2000);

        // then
        assertThat(concurrentMap.size(), is(0));
    }
}
