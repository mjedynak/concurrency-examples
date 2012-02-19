package pl.mjedynak.concurrency.latch;

public class JoiningThreadsExample {

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    Math.pow(Math.random(), 2);
                }
                System.out.println(Thread.currentThread().getName() + " finished");
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Both threads finished computing.");
    }
}
