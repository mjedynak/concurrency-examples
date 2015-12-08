package pl.mjedynak.concurrency.cache;

import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> {

    private final ConcurrentHashMap<K, V> internalCache = new ConcurrentHashMap<>();
    private final Action<K, V> action;

    public Cache(Action<K, V> action) {
        this.action = action;
    }

    public V get(K key) throws Exception {
        return internalCache.computeIfAbsent(key, action::compute);
    }

}
