package pl.mjedynak.concurrency.lock;

import pl.mjedynak.equals.java7.Book;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Book book = new Book("Haruki Murakami", "1Q84", 300);

    public void showInfoAboutBook() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("Reading book info from thread " + Thread.currentThread().getName());
            System.out.println(book);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void setNumberOfPages(int pages) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("Setting book property from thread " + Thread.currentThread().getName());
            book.setPages(pages);
            System.out.println("New value for book has been set");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
