package pl.mjedynak.concurrency.cache;

public interface DataSource<K, V> {

    V get(K key);
}
