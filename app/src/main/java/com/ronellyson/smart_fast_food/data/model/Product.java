package com.ronellyson.smart_fast_food.data.model;

import com.caverock.androidsvg.SVG;

public class Product {

    private String id;
    private String name;
    private String image;

    private SVG svg;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SVG getSvg() {
        return svg;
    }

    public void setSvg(SVG svg) {
        this.svg = svg;
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
