package pl.mjedynak.concurrency.future;

public class FutureExampleRunner {

    public static void main(String[] args) throws Exception {
        Buyer buyerWithoutFuture = new Buyer();
        buyerWithoutFuture.buyProduct();

        BuyerWithFuture buyerWithFuture = new BuyerWithFuture();
        buyerWithFuture.buyProduct();
        
    }
}
