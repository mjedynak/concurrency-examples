package pl.mjedynak.concurrency.queue;

import java.util.Queue;
import java.util.UUID;

public class SynchronizedProducer {

    private Queue<String> queue;

    public SynchronizedProducer(Queue queue) {
        this.queue = queue;
    }

    public void produce() throws InterruptedException {
        while (true) {
            putOnQueue(UUID.randomUUID().toString());
            Thread.sleep(1);
        }

    }

    private void putOnQueue(String message) {
        synchronized (queue) {
            queue.add(message);
        }
    }
}
