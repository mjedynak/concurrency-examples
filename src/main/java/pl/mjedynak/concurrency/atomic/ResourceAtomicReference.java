package pl.mjedynak.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class ResourceAtomicReference {

    private Object resource = new Object();
    private AtomicReference<Object> reference = new AtomicReference<Object>(resource);

    public Object getResource() {
        return reference.get();
    }

    public void setResource(Object resource) {
        reference.set(resource);
    }

    public static void main(String[] args) {
        ResourceAtomicReference atomicReference = new ResourceAtomicReference();
        System.out.println(atomicReference.getResource());
    }
}
