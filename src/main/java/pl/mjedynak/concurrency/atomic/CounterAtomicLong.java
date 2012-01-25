package pl.mjedynak.concurrency.atomic;

import java.util.concurrent.atomic.AtomicLong;


public class CounterAtomicLong {

    private AtomicLong counter = new AtomicLong();

    public void incrementCounter() {
        counter.incrementAndGet();
        System.out.println(counter.get());
    }

    public static void main(String[] args) {
        CounterAtomicLong ex = new CounterAtomicLong();
        ex.incrementCounter();
        ex.incrementCounter();
    }

}
