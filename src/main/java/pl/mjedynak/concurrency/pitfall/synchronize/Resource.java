package pl.mjedynak.concurrency.pitfall.synchronize;


public class Resource {

    private Object veryImportantObject = new Object();

    private final Object mutex = new Object();

    public synchronized Object getVeryImportantObject() {
        System.out.println("Getting object");
        return veryImportantObject;
    }

    public Object getVeryImportantObjectWithMutex() {
        synchronized(mutex) {
            System.out.println("Getting object with mutex");
            return veryImportantObject;
        }
    }
}
