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

    private SharedPreferences sharedPreferences;

    private List<Address> addressList;

    public static FragmentAddressCardList newInstance(SharedPreferences sharedPreferences) {
        FragmentAddressCardList fragment = new FragmentAddressCardList();
        fragment.sharedPreferences = sharedPreferences;
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
        addressList = new ArrayList<>();
        addressCardListAdapter = new AddressCardListAdapter(addressList);
        recyclerView.setAdapter(addressCardListAdapter);

        // Adicione os endereços de exemplo à lista
        addExampleAddresses();

        return view;
    }

    // Método para adicionar endereços de exemplo à lista
    private void addExampleAddresses() {
        addressList.add(new Address("John Doe", "123 Main St", "Apt 4", "Downtown", "12345",
                "California", "USA", "+1 123-456-7890", false));
        addressList.add(new Address("Jane Smith", "456 Elm St", "Apt 8", "Suburb", "67890",
                "New York", "USA", "+1 987-654-3210", false));
        addressList.add(new Address("David Johnson", "789 Oak St", "Unit 12", "City Center", "54321",
                "Texas", "USA", "+1 555-123-4567", false));

        // Notifique o adaptador de que os dados foram alterados
        addressCardListAdapter.notifyDataSetChanged();
    }
}
