package com.ronellyson.smart_fast_food.ui.contracts;

import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;

public interface OnPaymentMethodSelectedListener {
    void onPaymentMethodSelected(CreditDebitCard selectedPayment);
}
