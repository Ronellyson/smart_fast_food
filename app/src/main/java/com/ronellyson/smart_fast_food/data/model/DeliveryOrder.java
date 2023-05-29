package com.ronellyson.smart_fast_food.data.model;

import com.ronellyson.smart_fast_food.data.model.enums.Status;

import java.util.Date;
import java.util.List;

public class DeliveryOrder {
    private Date orderDateTime;
    private Status orderStatus;
    private List<ItemProduct> itemProducts;

    public DeliveryOrder(Date orderDateTime, Status orderStatus, List<ItemProduct> itemProducts) {
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.itemProducts = itemProducts;
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

    public List<ItemProduct> getProducts() {
        return itemProducts;
    }

    public void setProducts(List<ItemProduct> products) {
        this.itemProducts = products;
    }
}
