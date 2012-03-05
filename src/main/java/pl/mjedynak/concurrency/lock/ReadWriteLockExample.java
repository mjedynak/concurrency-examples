package pl.mjedynak.concurrency.lock;

import pl.mjedynak.equals.java7.Book;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Book book = new Book("Haruki Murakami", "1Q84", 300);

    public void showInfoAboutBook() {
        try {
            readWriteLock.readLock().lock();
            System.out.println(book);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void setNumberOfPages(int pages) {
        try {
            readWriteLock.writeLock().lock();
            book.setPages(pages);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockExample example = new ReadWriteLockExample();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Reading book info from thread " + Thread.currentThread().getName());
                example.showInfoAboutBook();
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Setting book property from thread " +Thread.currentThread().getName());
                example.setNumberOfPages(301);
            }
        };

        for (int i = 0; i < 100; i++) {
            executor.submit(readTask);
        }
        executor.submit(writeTask);
        executor.shutdown();

    }


}
