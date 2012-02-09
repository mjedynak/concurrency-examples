package pl.mjedynak.concurrency.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMap {

    private Map<String, Double> map = Collections.synchronizedMap(new HashMap<String, Double>());
}



