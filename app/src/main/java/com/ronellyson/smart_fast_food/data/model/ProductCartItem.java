package com.ronellyson.smart_fast_food.data.model;

public class ProductCartItem {
    private String productCartItemTitle;
    private String productCartItemImage;
    private String productCartItemPrice;
    private Integer productCartItemQuantity;

    public ProductCartItem(String productCartItemTitle, String productCartItemImage, String productCartItemPrice, Integer productCartItemQuantity) {
        this.productCartItemTitle = productCartItemTitle;
        this.productCartItemImage = productCartItemImage;
        this.productCartItemPrice = productCartItemPrice;
        this.productCartItemQuantity = productCartItemQuantity;
    }

    public String getProductCartItemTitle() {
        return productCartItemTitle;
    }

    public String getProductCartItemImage() {
        return productCartItemImage;
    }

    public String getProductCartItemPrice() {
        return productCartItemPrice;
    }

    public Integer getProductCartItemQuantity() {
        return productCartItemQuantity;
    }
}

