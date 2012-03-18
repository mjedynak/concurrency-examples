package pl.mjedynak.concurrency.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BuyerWithFuture {

    private PriceChecker priceChecker = new PriceChecker();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void buyProduct() throws Exception {

        Callable<Double> getPriceTask = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return priceChecker.checkPrice();
            }
        };
        Future<Double> priceFuture = executorService.submit(getPriceTask);
        Double price = priceFuture.get();
        int quantity = getQuantity();

        System.out.println("Buying product, price = " + price + " , quantity = " + quantity);
        executorService.shutdown();
    }

    private int getQuantity() {
        return new Random().nextInt(100);
    }
}
