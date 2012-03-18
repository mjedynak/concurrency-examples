package pl.mjedynak.concurrency.future;

import java.util.Random;

public class PriceChecker {
    
    private static final Random RANDOM = new Random();
    
    public Double checkPrice() {
        int sleepTime = RANDOM.nextInt(3);
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random();
    }
}
