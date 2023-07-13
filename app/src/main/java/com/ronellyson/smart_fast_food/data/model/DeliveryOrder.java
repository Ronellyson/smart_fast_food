package com.ronellyson.smart_fast_food.data.model;

import com.ronellyson.smart_fast_food.data.model.enums.Status;

import java.util.Date;
import java.util.List;

public class DeliveryOrder {
    private Date orderDateTime;
    private Status orderStatus;

    private Address address;
    private List<ProductCartItem> productCartItems;

    public DeliveryOrder(Date orderDateTime, Status orderStatus, Address address, List<ProductCartItem> productCartItems) {
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.productCartItems = productCartItems;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ProductCartItem> getProductCartItems() {
        return productCartItems;
    }

    public void setProductCartItems(List<ProductCartItem> productCartItems) {
        this.productCartItems = productCartItems;
    }
}
