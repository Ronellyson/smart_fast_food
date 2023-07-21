package com.ronellyson.smart_fast_food.data.model;

import com.ronellyson.smart_fast_food.data.model.enums.Status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DeliveryOrder {
    private UUID orderId;
    private Date orderDateTime;
    private Status orderStatus;
    private Address address;
    private CreditDebitCard creditDebitCard;
    private List<ProductCartItem> productCartItems;

    private BigDecimal totalValue;

    public DeliveryOrder(Date orderDateTime, Status orderStatus, Address address, CreditDebitCard creditDebitCard, List<ProductCartItem> productCartItems) {
        this.orderId = UUID.randomUUID();
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.creditDebitCard = creditDebitCard;
        this.productCartItems = productCartItems;
        this.totalValue = getTotalValue(this.productCartItems);
    }

    public UUID getOrderId() {
        return orderId;
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

    public CreditDebitCard getCreditDebitCard() {
        return creditDebitCard;
    }

    public void setCreditDebitCard(CreditDebitCard creditDebitCard) {
        this.creditDebitCard = creditDebitCard;
    }

    public List<ProductCartItem> getProductCartItems() {
        return productCartItems;
    }

    public void setProductCartItems(List<ProductCartItem> productCartItems) {
        this.productCartItems = productCartItems;
    }

    public BigDecimal getTotalValue() {
        return totalValue.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getTotalValue(List<ProductCartItem> productCartItems) {
        BigDecimal totalValue = BigDecimal.ZERO;

        for (ProductCartItem productCartItem : productCartItems) {
            BigDecimal itemPrice = productCartItem.getProduct().getPrice();
            int itemQuantity = productCartItem.getProductCartItemQuantity();
            BigDecimal itemTotal = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));
            totalValue = totalValue.add(itemTotal);
        }

        return totalValue;
    }
}
