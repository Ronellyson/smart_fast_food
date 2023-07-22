package com.ronellyson.smart_fast_food.data.model;

public class ProductCartItem {
    private String id;
    private Product product;
    private Integer productCartItemQuantity;
    private boolean isProductAddedToCart;

    public ProductCartItem() {}

    public ProductCartItem(String id, Product product, Integer productCartItemQuantity, boolean isProductAddedToCart) {
        this.id = id;
        this.product = product;
        this.productCartItemQuantity = productCartItemQuantity;
        this.isProductAddedToCart = isProductAddedToCart;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductCartItemQuantity() {
        return productCartItemQuantity;
    }

    public void setProductCartItemQuantity(Integer productCartItemQuantity) {
        this.productCartItemQuantity = productCartItemQuantity;
    }

    public boolean isProductAddedToCart() {
        return isProductAddedToCart;
    }

    public void setProductAddedToCart(boolean productAddedToCart) {
        isProductAddedToCart = productAddedToCart;
    }
}

