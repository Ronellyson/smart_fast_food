package com.ronellyson.smart_fast_food.data.model;

import com.ronellyson.smart_fast_food.data.model.enums.Status;

import java.util.Date;
import java.util.List;

public class DeliveryOrder {
    private Date orderDateTime;
    private Status orderStatus;
    private List<Product> products;

    public DeliveryOrder(Date orderDateTime, Status orderStatus, List<Product> products) {
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
