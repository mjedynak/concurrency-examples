package pl.mjedynak.concurrency.cache;

public interface Action<K, V> {

    V compute(K key);
}
