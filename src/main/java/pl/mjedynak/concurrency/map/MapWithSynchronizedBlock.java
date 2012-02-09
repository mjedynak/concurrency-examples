package pl.mjedynak.concurrency.map;

import java.util.HashMap;
import java.util.Map;

public class MapWithSynchronizedBlock {

    private Map<String, Integer> map = new HashMap<>();

    public Integer getKey(String key) {
        synchronized (map) {
            return map.get(key);
        }
    }
}
