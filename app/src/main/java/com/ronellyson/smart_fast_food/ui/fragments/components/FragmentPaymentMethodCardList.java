package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.ui.adapters.AddressCardListAdapter;
import com.ronellyson.smart_fast_food.ui.adapters.PaymentMethodCardListAdapter;
import com.ronellyson.smart_fast_food.ui.contracts.OnPaymentMethodSelectedListener;

public class FragmentPaymentMethodCardList extends Fragment{

    private RecyclerView recyclerView;
    private PaymentMethodCardListAdapter paymentMethodCardListAdapter;
    private Boolean showCheckBoxes;
    private Boolean showActionButtons;
    private SharedPreferences sharedPreferences;

    public static FragmentPaymentMethodCardList newInstance(SharedPreferences sharedPreferences, Boolean showCheckBoxes, Boolean showActionButtons) {
        FragmentPaymentMethodCardList fragment = new FragmentPaymentMethodCardList();
        fragment.sharedPreferences = sharedPreferences;
        fragment.showCheckBoxes = showCheckBoxes;
        fragment.showActionButtons = showActionButtons;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_method_card_list, container, false);

        recyclerView = view.findViewById(R.id.payment_method_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the payment method card list adapter
        paymentMethodCardListAdapter = new PaymentMethodCardListAdapter(sharedPreferences, showCheckBoxes, showActionButtons);

        // Set the listener for item click events
        paymentMethodCardListAdapter.setOnItemClickListener(new OnPaymentMethodSelectedListener() {
            @Override
            public void onPaymentMethodSelected(CreditDebitCard selectedPayment) {
                paymentMethodCardListAdapter.setSelectedPaymentMethod(selectedPayment);
            }
        });

        recyclerView.setAdapter(paymentMethodCardListAdapter);

        return view;
    }
}
