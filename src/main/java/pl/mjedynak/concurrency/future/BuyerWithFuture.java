package pl.mjedynak.concurrency.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BuyerWithFuture {

    private PriceChecker priceChecker = new PriceChecker();
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void buyProduct() throws ExecutionException, InterruptedException {

        Callable<Double> getPriceTask = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return priceChecker.checkPrice();
            }
        };
        Future<Double> priceFuture = executorService.submit(getPriceTask);
        Double price = priceFuture.get();
        executorService.shutdown();
        int quantity = getQuantity();

        System.out.println("Buying product, price = " + price + " , quantity = " + quantity);
    }

    private int getQuantity() {
        return new Random().nextInt(100);
    }


}
