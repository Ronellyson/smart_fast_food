package com.ronellyson.smart_fast_food.data.model;

public class Product {

    private String id;
    private String name;
    private String urlImage;

    private Number price;
    private String description;
    private int rate;

    public Product(String name, String urlImage, double responsePrice, String description, Number price) {
        this.name = name;
        this.urlImage = urlImage;
        this.price = price;
        this.description = description;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
