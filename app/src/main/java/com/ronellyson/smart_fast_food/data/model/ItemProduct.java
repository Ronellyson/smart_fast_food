package com.ronellyson.smart_fast_food.data.model;

public class ItemProduct {

    private Product product;
    private int quantity;

    public ItemProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}