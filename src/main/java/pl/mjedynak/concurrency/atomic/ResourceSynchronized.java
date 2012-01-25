package pl.mjedynak.concurrency.atomic;


public class ResourceSynchronized {

    private Object resource = new Object();

    public synchronized Object getResource() {
        return resource;
    }

    public synchronized void setResource(Object resource) {
        this.resource = resource;
    }
}
