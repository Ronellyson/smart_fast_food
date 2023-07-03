package com.ronellyson.smart_fast_food.data.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private String id;
    private String name;
    private String urlImage;

    private BigDecimal price;
    private String description;
    private int rate;

    public Product(String id,String name, String urlImage, BigDecimal price, String description, int rate) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
