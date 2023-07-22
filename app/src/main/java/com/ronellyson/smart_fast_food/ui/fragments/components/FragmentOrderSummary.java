package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FragmentOrderSummary extends Fragment{
    private TextView textSelectedPaymentMethod;
    private TextView textAddressDetails;
    private LinearLayout productCartItemsContainer;
    private TextView textTotalValue;

    private Gson gson = new Gson();
    private Address selectedAddress;
    private CreditDebitCard selectedPaymentMethod;
    private List<ProductCartItem> productCartItems;
    private BigDecimal totalValue;

    public static FragmentOrderSummary newInstance(Address selectedAddress, CreditDebitCard selectedPaymentMethod, List<ProductCartItem> productCartItems, BigDecimal totalValue) {
        FragmentOrderSummary fragment = new FragmentOrderSummary();
        fragment.selectedAddress = selectedAddress;
        fragment.selectedPaymentMethod = selectedPaymentMethod;
        fragment.productCartItems = productCartItems;
        fragment.totalValue = totalValue;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_summary, container, false);
        textSelectedPaymentMethod = view.findViewById(R.id.textSelectedPaymentMethod);
        textAddressDetails = view.findViewById(R.id.textAddressDetails);
        productCartItemsContainer = view.findViewById(R.id.productCartItemsContainer);
        textTotalValue = view.findViewById(R.id.textTotalValue);

        // Update the UI with the initial data
        updateSelectedAddress(selectedAddress);
        updateSelectedPaymentMethod(selectedPaymentMethod);
        updateOrderDetails(productCartItems);
        updateTotalValue(totalValue);

        return view;
    }

    private void updateSelectedAddress(Address selectedAddress) {
        if (selectedAddress != null) {
            String addressDetails = "Street: " + selectedAddress.getStreet() +
                    "\nNumber: " + selectedAddress.getNumber() +
                    "\nNeighborhood: " + selectedAddress.getNeighborhood() +
                    "\nZip Code: " + selectedAddress.getZipCode() +
                    "\nState: " + selectedAddress.getState() +
                    "\nCountry: " + selectedAddress.getCountry() +
                    "\nPhone: " + selectedAddress.getPhone();

            textAddressDetails.setText(addressDetails);
        } else {
            textAddressDetails.setText("No address selected.");
        }
    }

    private void updateSelectedPaymentMethod(CreditDebitCard selectedPaymentMethod) {
        if (selectedPaymentMethod != null) {
            String paymentMethod = selectedPaymentMethod.isCredit() ? "Credit Card" : "Debit Card";

            // Get the last four digits of the card number
            String lastFourDigits = selectedPaymentMethod.getCardNumber().substring(selectedPaymentMethod.getCardNumber().length() - 4);

            // Mask the rest of the card number with asterisks (*)
            StringBuilder maskedCardNumber = new StringBuilder();
            for (int i = 0; i < selectedPaymentMethod.getCardNumber().length() - 4; i++) {
                maskedCardNumber.append("*");
            }
            maskedCardNumber.append(lastFourDigits);

            String cardDetails = "Card Type: " + paymentMethod +
                    "\nCard Number: " + maskedCardNumber.toString() +
                    "\nCard Holder: " + selectedPaymentMethod.getCardHolderName() +
                    "\nExpiry Date: " + selectedPaymentMethod.getExpirationDate() +
                    "\nCVV: " + selectedPaymentMethod.getSecurityCode();

            textSelectedPaymentMethod.setText(cardDetails);
        } else {
            textSelectedPaymentMethod.setText("No payment method selected.");
        }
    }

    private void updateOrderDetails(List<ProductCartItem> productCartItems){
        if (!productCartItems.isEmpty()){
            for (ProductCartItem productCartItem : productCartItems) {
                View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.product_cart_item_details, productCartItemsContainer, false);
                TextView productNameTextView = itemView.findViewById(R.id.productCartItemName);
                TextView productQuantityTextView = itemView.findViewById(R.id.productCartItemQuantity);
                TextView productPriceTextView = itemView.findViewById(R.id.productCartItemPrice);

                productNameTextView.setText(productCartItem.getProduct().getName());
                productQuantityTextView.setText("Quantity: " + productCartItem.getProductCartItemQuantity());
                productPriceTextView.setText("Price: R$ " + productCartItem.getProduct().getPrice().setScale(2, RoundingMode.HALF_UP).toString());

                productCartItemsContainer.addView(itemView);
            }
        }
    }

    public void updateTotalValue(BigDecimal totalValue) {
        if (totalValue.compareTo(BigDecimal.ZERO) > 0) {
            // Format the BigDecimal to display with two decimal places
            String formattedValue = totalValue.setScale(2, RoundingMode.HALF_UP).toString();

            // Set the formatted value to the TextView
            textTotalValue.setText("R$: " + formattedValue);
        }
    }
}
