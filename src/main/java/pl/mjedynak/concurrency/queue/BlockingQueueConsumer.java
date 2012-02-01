package pl.mjedynak.concurrency.queue;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueConsumer {
    private BlockingQueue queue;

    public BlockingQueueConsumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void consume() throws InterruptedException {
        while (true) {
            takeFromQueue();
        }
    }

    private void takeFromQueue() throws InterruptedException {
        System.out.println(queue.take());
    }
}
