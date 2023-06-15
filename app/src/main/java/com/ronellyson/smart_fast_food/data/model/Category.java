package com.ronellyson.smart_fast_food.data.model;

public class Category {
    private Integer id;
    private String name;

    public Category(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
