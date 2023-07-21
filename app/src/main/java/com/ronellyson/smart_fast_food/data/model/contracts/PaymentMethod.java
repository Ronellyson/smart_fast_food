package com.ronellyson.smart_fast_food.data.model.contracts;

import java.math.BigDecimal;

public interface PaymentMethod {
    void makePayment(BigDecimal amount);
}
