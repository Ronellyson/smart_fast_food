package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCartItemAdapter;

public class FragmentProductCartItemList extends Fragment {

    private RecyclerView recyclerView;
    private ProductCartItemAdapter adapter;

    private SharedPreferences sharedPreferences;

    public static FragmentProductCartItemList newInstance() {
        return new FragmentProductCartItemList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_cart_item_list, container, false);
        recyclerView = view.findViewById(R.id.product_cart_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get the shared preferences
        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Create the adapter
        adapter = new ProductCartItemAdapter(sharedPreferences);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

