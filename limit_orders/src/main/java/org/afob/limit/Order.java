package org.afob.limit;

class Order {
    private boolean buy;
    private String productId;
    private int amount;
    private double limit;

    public Order(boolean buy, String productId, int amount, double limit) {
        this.buy = buy;
        this.productId = productId;
        this.amount = amount;
        this.limit = limit;
    }

    public boolean isBuy() {
        return buy;
    }

    public String getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public double getLimit() {
        return limit;
    }
}
