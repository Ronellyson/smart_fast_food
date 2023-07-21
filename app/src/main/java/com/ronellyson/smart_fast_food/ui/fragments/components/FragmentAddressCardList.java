package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.ui.adapters.AddressCardListAdapter;
import com.ronellyson.smart_fast_food.ui.adapters.PaymentMethodCardListAdapter;
import com.ronellyson.smart_fast_food.ui.contracts.OnAddressSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddressCardList extends Fragment implements OnAddressSelectedListener{

    private RecyclerView recyclerView;
    private AddressCardListAdapter addressCardListAdapter;
    private Boolean showCheckBoxes;
    private Boolean showActionButtons;
    private SharedPreferences sharedPreferences;

    private OnAddressSelectedListener onAddressSelectedListener;

    public static FragmentAddressCardList newInstance(SharedPreferences sharedPreferences, Boolean showCheckBoxes, Boolean showActionButtons) {
        FragmentAddressCardList fragment = new FragmentAddressCardList();
        fragment.sharedPreferences = sharedPreferences;
        fragment.showCheckBoxes = showCheckBoxes;
        fragment.showActionButtons = showActionButtons;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.address_card_list, container, false);

        recyclerView = view.findViewById(R.id.address_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inicializa a lista de endereços
        addressCardListAdapter = new AddressCardListAdapter(sharedPreferences, showCheckBoxes, showActionButtons);

        addressCardListAdapter.setOnItemClickListener(new AddressCardListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Address address) {
                Log.d("addressCardListAdapterOnItemClick", address.getHolder());
                addressCardListAdapter.setSelectedAddress(address);
                handleSelectionChange(address);
            }
        });

        recyclerView.setAdapter(addressCardListAdapter);

        return view;
    }


    // Method to handle selection changes and notify the parent fragment
    private void handleSelectionChange(Address selectedAddress) {
        // Notify the parent fragment about the selection change
        if (onAddressSelectedListener != null) {
            onAddressSelectedListener.onAddressSelected(selectedAddress);
        }
    }

    public AddressCardListAdapter getAddressCardListAdapter() {
        return addressCardListAdapter;
    }

    @Override
    public void onAddressSelected(Address address) {
        // Handle the selected address here
        // Save it or perform any other actions as needed
        // For example, you can update the selected address in the PaymentMethodCardListAdapter.
        if (addressCardListAdapter != null) {
            // Update the selected address in the paymentMethodCardListAdapter
            addressCardListAdapter.setSelectedAddress(address);
            // Notify the adapter to refresh the RecyclerView
            addressCardListAdapter.notifyDataSetChanged();
        }
    }
}
