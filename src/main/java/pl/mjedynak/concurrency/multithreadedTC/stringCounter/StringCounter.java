package pl.mjedynak.concurrency.multithreadedTC.stringCounter;

import java.util.HashMap;
import java.util.Map;

public class StringCounter {

    private Map<String, Integer> map = new HashMap<>();


    public synchronized void addString(String string) {
        if (map.containsKey(string)) {
            Integer value = map.get(string);
            map.put(string, value + 1);
        } else {
            map.put(string, 1);
        }


    }
    
    public int getCounterFor(String string) {
        return map.get(string);
    }

}
