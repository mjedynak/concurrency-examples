package pl.mjedynak.concurrency.pitfall.synchronize;

public class Cheater {

    private final Resource resource;

    public Cheater(Resource resource) {
        this.resource = resource;
    }

    public void blockResource() {
        synchronized(resource) {
            System.out.println("I'm blocking resource");
            while(true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
