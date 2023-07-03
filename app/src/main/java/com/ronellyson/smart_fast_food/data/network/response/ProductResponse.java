package com.ronellyson.smart_fast_food.data.network.response;

import com.squareup.moshi.Json;

import java.util.UUID;

public class ProductResponse {
    @Json(name = "id")
    private String id;

    @Json(name = "name")
    private String name;
    @Json(name = "img")
    private String image;

    @Json(name = "dsc")
    private String description;

    @Json(name = "price")
    private double price;

    @Json(name = "rate")
    private int rate;



    public ProductResponse(String id, String name, String image, String description, double price, int rate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.rate = rate;
    }

    public String getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    public int getRate() {
        return rate;
    }
}
