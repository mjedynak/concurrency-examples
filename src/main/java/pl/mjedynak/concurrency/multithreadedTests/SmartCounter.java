package pl.mjedynak.concurrency.multithreadedTests;

import java.util.concurrent.atomic.AtomicInteger;

public class SmartCounter implements Counter {
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public int incrementAndGet() {
        return atomicInteger.incrementAndGet();
    }
}
