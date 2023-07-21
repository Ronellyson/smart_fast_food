package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.data.model.DeliveryOrder;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;
import com.ronellyson.smart_fast_food.data.model.enums.Status;
import com.ronellyson.smart_fast_food.ui.contracts.OnAddressSelectedListener;
import com.ronellyson.smart_fast_food.ui.contracts.OnPaymentMethodSelectedListener;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentAddressCardList;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentOrderSummary;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentPaymentMethodCardList;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentOrderDetailsPage extends Fragment{

    SharedPreferences sharedPreferences;
    private DeliveryOrder deliveryOrder;

    private Gson gson = new Gson();
    public static FragmentOrderDetailsPage newInstance(SharedPreferences sharedPreferences) {
        FragmentOrderDetailsPage fragment = new FragmentOrderDetailsPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_details_page, container, false);

        // Retrieve the initial values of selectedAddress and selectedPaymentMethod
        Address selectedAddress = getSelectedAddress();
        CreditDebitCard selectedPaymentMethod = getSelectedPaymentMethod();
        List<ProductCartItem> productCartItems = getProductCartItems();
        BigDecimal totalValue = getTotalValue();

        ImageButton backButton = rootView.findViewById(R.id.product_cart_page_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém a referência à FragmentDynamicPage
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();

                // Navigate back to the previous view
                fragmentDynamicPage.onBackToCartPagePressed();
            }
        });

        Button placeOrderButton = rootView.findViewById(R.id.btnPlaceOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address selectedAddress = getSelectedAddress();
                CreditDebitCard selectedPaymentMethod = getSelectedPaymentMethod();
                List<ProductCartItem> productCartItems = getProductCartItems();

                if (selectedAddress == null || selectedPaymentMethod == null || productCartItems.isEmpty()) {
                    // Show an error message if any required data is missing
                    showErrorDialog("Error", "Please select an address, payment method, and add items to your cart before placing the order.");
                } else {
                    BigDecimal totalValue = getTotalValue();

                    // Create a Date object for the order date
                    Date orderDate = new Date();

                    deliveryOrder = new DeliveryOrder(orderDate, Status.INPROGRESS, selectedAddress, selectedPaymentMethod, productCartItems);
                    showPlaceOrderPopup();
                }
            }
        });

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentAddressCardList fragmentAddressCardList = FragmentAddressCardList.newInstance(sharedPreferences, true, false);

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_details_page_address_card_list_container, fragmentAddressCardList);

        // Cria uma instância do fragmento que você deseja exibir
        FragmentPaymentMethodCardList fragmentPaymentMethodCardList = FragmentPaymentMethodCardList.newInstance(sharedPreferences, true, false);

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_details_page_payment_method_card_container, fragmentPaymentMethodCardList);

        // Cria uma instância do fragmento que você deseja exibir
        FragmentOrderSummary fragmentOrderSummary = FragmentOrderSummary.newInstance(selectedAddress, selectedPaymentMethod, productCartItems, totalValue);

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_resume_container, fragmentOrderSummary);

        // Confirma a transação
        fragmentTransaction.commit();


        return rootView;
    }

    private void saveDeliveryOrderToHistory(DeliveryOrder deliveryOrder) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the existing order history list from SharedPreferences
        List<DeliveryOrder> orderHistory = getDeliveryOrderHistory();

        // Add the current order to the history list
        orderHistory.add(deliveryOrder);

        // Convert the order history list to JSON format
        String orderHistoryJson = gson.toJson(orderHistory);

        // Save the order history JSON string in SharedPreferences
        editor.putString("orderHistory", orderHistoryJson);
        editor.apply();
    }

    private List<DeliveryOrder> getDeliveryOrderHistory() {
        String orderHistoryJson = sharedPreferences.getString("orderHistory", null);
        if (orderHistoryJson != null) {
            return gson.fromJson(orderHistoryJson, new TypeToken<List<DeliveryOrder>>() {}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    // Method to show an AlertDialog for error handling
    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to show the address registration confirmation pop-up
    public void showPlaceOrderPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Pedido realizado");
        builder.setMessage("O pedido foi realizado com sucesso!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();

                // Save the current deliveryOrder to the order history
                saveDeliveryOrderToHistory(deliveryOrder);

                // Navigate back to the previous view
                fragmentDynamicPage.onBackToHomePagePressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Address getSelectedAddress() {
        String selectedAddressJson = sharedPreferences.getString("selectedAddress", "");
        return new Gson().fromJson(selectedAddressJson, Address.class);
    }

    private CreditDebitCard getSelectedPaymentMethod() {
        String selectedPaymentMethodJson = sharedPreferences.getString("selectedPaymentMethod", "");
        return new Gson().fromJson(selectedPaymentMethodJson, CreditDebitCard.class);
    }

    private List<ProductCartItem> getProductCartItems() {
        String productCartItemsJson = sharedPreferences.getString("productCartItems", null);
        if (productCartItemsJson != null) {
            return gson.fromJson(productCartItemsJson, new TypeToken<List<ProductCartItem>>() {}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    public BigDecimal getTotalValue(){
        String productCartItemsJson = sharedPreferences.getString("productCartItems", null);
        if (productCartItemsJson != null) {
            List<ProductCartItem> productCartItems = gson.fromJson(productCartItemsJson, new TypeToken<List<ProductCartItem>>() {}.getType());

            BigDecimal totalValue = BigDecimal.ZERO;

            for (ProductCartItem productCartItem : productCartItems) {
                BigDecimal itemPrice = productCartItem.getProduct().getPrice();
                int itemQuantity = productCartItem.getProductCartItemQuantity();
                BigDecimal itemTotal = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));
                totalValue = totalValue.add(itemTotal);
            }

            return totalValue;
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the selectedPaymentMethod and selectedAddress from SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("selectedPaymentMethod");
        editor.remove("selectedAddress");
        editor.apply();
    }
}

