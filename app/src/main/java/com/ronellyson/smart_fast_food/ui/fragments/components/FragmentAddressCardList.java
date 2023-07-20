package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.ui.adapters.AddressCardListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddressCardList extends Fragment {

    private RecyclerView recyclerView;
    private AddressCardListAdapter addressCardListAdapter;
    private Boolean showCheckBoxes;
    private SharedPreferences sharedPreferences;

    public static FragmentAddressCardList newInstance(SharedPreferences sharedPreferences, Boolean showCheckBoxes) {
        FragmentAddressCardList fragment = new FragmentAddressCardList();
        fragment.sharedPreferences = sharedPreferences;
        fragment.showCheckBoxes = showCheckBoxes;
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
        addressCardListAdapter = new AddressCardListAdapter(sharedPreferences, showCheckBoxes);
        recyclerView.setAdapter(addressCardListAdapter);

        return view;
    }

    public AddressCardListAdapter getAddressCardListAdapter() {
        return addressCardListAdapter;
    }
}
