package pl.mjedynak.concurrency.pitfall.synchronize;

public class Pitfall {

    public static void main(String[] args) throws Exception {
        final Resource resource = new Resource();

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                new Cheater(resource).blockResource();
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                resource.getVeryImportantObject();                
            }
        });

        Thread thread3 = new Thread(new Runnable() {

            @Override
            public void run() {
                resource.getVeryImportantObjectWithMutex();
            }
        });

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        thread3.start();
    }
}
