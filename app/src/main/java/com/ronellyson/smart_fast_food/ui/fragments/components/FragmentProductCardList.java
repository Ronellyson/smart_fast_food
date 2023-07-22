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

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCardListAdapter;
import com.ronellyson.smart_fast_food.ui.presenters.ProductCardListPresenter;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductCardList extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private ProductCardListAdapter adapter;

    private SharedPreferences sharedPreferences;
    private ProductCardListPresenter presenter;
    private List<Product> products;
    private String currentSearchQuery;

    private String currentCategoryName;

    public static FragmentProductCardList newInstance(SharedPreferences sharedPreferences) {
        FragmentProductCardList fragment = new FragmentProductCardList();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_card_list, container, false);
        recyclerView = view.findViewById(R.id.product_card_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the products list before using it in the adapter
        products = new ArrayList<>();

        adapter = new ProductCardListAdapter(products, position -> {
            // Handle add button click
            // This is an empty listener implementation as per your requirement
        }, sharedPreferences);

        recyclerView.setAdapter(adapter);

        // Create an instance of the presenter
        presenter = new ProductCardListPresenter(this);

        // Call the method to fetch products by category
        String categoryName = getCategoryName();
        if (categoryName.isEmpty()) {
            categoryName = "best-foods"; // Valor padrão quando vazio
        }
        presenter.getProductsFiltered(categoryName, getSearchQuery());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // Listener to handle changes in searchQuery and productCategoryButtonSelected
    @Override
    public void onResume() {
        super.onResume();
        // Register the listener for changes in SharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener for changes in SharedPreferences
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("searchQuery") || key.equals(FragmentProductCategoryButtonList.getProductCategoryButtonSelectedKey())) {
            String newSearchQuery = getSearchQuery();
            String newCategoryName = getCategoryName();
            if (!newSearchQuery.equals(currentSearchQuery) || !newSearchQuery.equals(currentCategoryName)) {
                currentSearchQuery = newSearchQuery;
                currentCategoryName = newCategoryName;
                if (currentCategoryName.isEmpty()) {
                    currentCategoryName = "best-foods";
                }
                Log.d("getCategoryName", getCategoryName());
                presenter.getProductsFiltered(currentCategoryName, currentSearchQuery);
            }
        }
    }

    private String getSearchQuery() {
        // Retrieve the searchQuery from shared preferences
        return sharedPreferences.getString("searchQuery", "");
    }

    private String getCategoryName() {
        // Retrieve the categoryName from shared preferences
        return sharedPreferences.getString(FragmentProductCategoryButtonList.getProductCategoryButtonSelectedKey(), "");
    }

    public void setProducts(List<Product> products) {
        adapter.updateProducts(products); // Update the products list in the adapter
    }
}
