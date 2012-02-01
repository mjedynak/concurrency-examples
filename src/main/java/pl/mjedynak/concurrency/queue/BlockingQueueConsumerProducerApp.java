package pl.mjedynak.concurrency.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueConsumerProducerApp {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        final BlockingQueueConsumer consumer = new BlockingQueueConsumer(queue);
        final BlockingQueueProducer producer = new BlockingQueueProducer(queue);

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    producer.produce();
                } catch (InterruptedException e) {
                }
            }
        });
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer.consume();
                } catch (InterruptedException e) {
                }
            }
        });
    }
}
