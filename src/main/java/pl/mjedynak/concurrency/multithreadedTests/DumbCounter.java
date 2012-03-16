package pl.mjedynak.concurrency.multithreadedTests;

public class DumbCounter implements Counter {

    private int counter;

    @Override
    public int incrementAndGet() {
        return ++counter;     // other thread may overwrite value
    }
}
