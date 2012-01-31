package pl.mjedynak.concurrency.queue;

import java.util.Queue;

public class SynchronizedConsumer {

    private Queue queue;

    public SynchronizedConsumer(Queue queue) {
        this.queue = queue;
    }

    public void consume() throws InterruptedException {
        while (true) {
            takeFromQueue();
            Thread.sleep(1);
        }
    }

    private void takeFromQueue() {

        synchronized (queue) {
            if (!queue.isEmpty()) {
                System.out.println(queue.remove());
            }
        }
    }
}
