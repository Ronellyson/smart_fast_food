package com.ronellyson.smart_fast_food.data.model;

public class Product {
    private final String name;
    private final String urlImage;
    private final String description;
    private final Number price;

    public Product(String name, String urlImage, String description, Number price) {
        this.name = name;
        this.urlImage = urlImage;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDescription() {
        return description;
    }

    public Number getPrice() {
        return price;
    }
}
