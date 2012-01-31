package pl.mjedynak.concurrency.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedList<String>();
        SynchronizedConsumer synchronizedConsumer = new SynchronizedConsumer(queue);
        final SynchronizedProducer synchronizedProducer = new SynchronizedProducer(queue);

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronizedProducer.produce();
                } catch (InterruptedException e) {
                }
            }
        });
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronizedProducer.produce();
                } catch (InterruptedException e) {
                }
            }
        });
        synchronizedConsumer.consume();
    }
}
