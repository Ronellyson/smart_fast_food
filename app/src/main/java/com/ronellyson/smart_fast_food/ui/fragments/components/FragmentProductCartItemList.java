package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCartItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentProductCartItemList extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

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
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Create the adapter
        adapter = new ProductCartItemAdapter(getProductCartItems(), sharedPreferences);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the shared preference change listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the shared preference change listener
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Handle the shared preference change event
        if (key.equals("MyPrefs")) {
            // Update the product cart items
            adapter.setProductCartItems(getProductCartItems());
        }
    }

    private List<ProductCartItem> getProductCartItems() {
        List<ProductCartItem> productCartItems = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                String json = (String) value;
                // Convert JSON string to ProductCartItem object
                ProductCartItem productCartItem = jsonToProductCartItem(json);
                if (productCartItem != null) {
                    productCartItems.add(productCartItem);
                }
            }
        }
        return productCartItems;
    }

    private ProductCartItem jsonToProductCartItem(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ProductCartItem.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e("FragmentProductCartItemList", "Error parsing JSON: " + e.getMessage());
        }
        return null;
    }
}
