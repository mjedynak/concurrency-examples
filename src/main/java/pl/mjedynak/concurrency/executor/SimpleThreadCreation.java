package pl.mjedynak.concurrency.executor;

public class SimpleThreadCreation {

    public static void main(String[] args) {
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread 1");
            }
        };
        Thread thread1 = new Thread(runnable);
        thread1.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                System.out.println("thread 2");
            }
        };
        thread2.start();

        thread2.start();


    }
}
