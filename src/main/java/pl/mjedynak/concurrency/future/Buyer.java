package pl.mjedynak.concurrency.future;

import java.util.Random;

public class Buyer {
    private PriceChecker priceChecker = new PriceChecker();
    private Double price;

    public void buyProduct() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                price = priceChecker.checkPrice();
            }
        }).start();

        int quantity = getQuantity();

        while (price == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Buying product, price = " + price + " , quantity = " + quantity);
    }

    private int getQuantity() {
        return new Random().nextInt(100);
    }
}
