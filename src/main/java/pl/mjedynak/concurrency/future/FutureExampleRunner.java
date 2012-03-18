package pl.mjedynak.concurrency.future;

public class FutureExampleRunner {

    public static void main(String[] args) throws Exception {
        BuyerWithoutFuture buyerWithoutFuture = new BuyerWithoutFuture();
        buyerWithoutFuture.buyProduct();

        BuyerWithFuture buyerWithFuture = new BuyerWithFuture();
        buyerWithFuture.buyProduct();
        
    }
}
