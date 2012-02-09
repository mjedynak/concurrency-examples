package pl.mjedynak.concurrency.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapExample {

    private ConcurrentMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();

    // bad solution
    public void addEntryIfKeyDoesNotExistBad(String key, Integer value) {
       if (!map.containsKey(key)) {
           map.put(key, value);
       }
    }

    public void addEntryIfKeyDoesNotExist(String key, Integer value) {
        map.putIfAbsent(key, value);
    }

}
