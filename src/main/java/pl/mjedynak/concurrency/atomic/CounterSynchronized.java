package pl.mjedynak.concurrency.atomic;


public class CounterSynchronized {
    
    private long counter = 0;

    private final Object mutex = new Object();

    public void incrementCounter() {
        synchronized (mutex) {
            counter++;
        }
    }


}
