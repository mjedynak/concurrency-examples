package pl.mjedynak.concurrency.queue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueProducer {

    private BlockingQueue<String> queue;

    public BlockingQueueProducer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void produce() throws InterruptedException {
        while (true) {
            putOnQueue(UUID.randomUUID().toString());
            Thread.sleep(1);
        }

    }

    private void putOnQueue(String message) {
        queue.add(message);
    }
}
