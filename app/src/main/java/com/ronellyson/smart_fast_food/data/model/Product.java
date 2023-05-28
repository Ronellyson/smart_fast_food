package com.ronellyson.smart_fast_food.data.model;

public class Product {

    private String id;
    private String name;
    private String image;
    private Number price;
    private String description;
    private int rate;

    public Product(String name, String urlImage, double responsePrice, String description, Number price) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Number getPrice() {
        return price;
    }
}
