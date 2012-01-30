package pl.mjedynak.concurrency.pitfall.synchronize;

import java.util.concurrent.Executors;

public class Pitfall {

    public static void main(String[] args) throws Exception {
        final Resource resource = new Resource();

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                new Cheater(resource).blockResource();
            }
        });

        Thread.sleep(1000);

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                resource.getVeryImportantObject();
            }
        });

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                resource.getVeryImportantObjectWithMutex();
            }
        });
    }
}
