package pl.mjedynak.concurrency.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheWithHolder<K, V> {
    private final Action<K, V> computeIfAbsent;
    private final ConcurrentHashMap<K, Holder> internalCache = new ConcurrentHashMap<>();

    public CacheWithHolder(Action<K, V> computeIfAbsent) {
        this.computeIfAbsent = computeIfAbsent;
    }

    public V get(K key) throws Exception {
        internalCache.putIfAbsent(key, new Holder());
        Holder holder = internalCache.get(key);
        return holder.get(key);
    }

    private class Holder {
        private V value;

        public synchronized V get(K key) throws Exception {
            if (value == null) {
                value = computeIfAbsent.compute(key);
            }
            return value;
        }
    }

}
