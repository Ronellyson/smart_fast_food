package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCardListAdapter;
import com.ronellyson.smart_fast_food.ui.presenters.ProductCardListPresenter;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductCardList extends Fragment {
    private RecyclerView recyclerView;
    private ProductCardListAdapter adapter;

    private SharedPreferences sharedPreferences;
    private ProductCardListPresenter presenter;
    private List<Product> products;
    private String currentSearchQuery;

    public static FragmentProductCardList newInstance() {
        return new FragmentProductCardList();
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

        // Initialize the shared preferences
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        adapter = new ProductCardListAdapter(products, position -> {
            // Handle add button click
            // This is an empty listener implementation as per your requirement
        }, sharedPreferences);

        recyclerView.setAdapter(adapter);

        // Create an instance of the presenter
        presenter = new ProductCardListPresenter(this);

        // Call the method to fetch products by category
        Category category = new Category(0, "best-foods"); // Replace with your actual category name
        presenter.getProductsFiltered(category, getSearchQuery());

        // Add a listener to the shared preferences to detect changes in searchQuery
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the shared preferences listener when the view is destroyed
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    // Listener to handle changes in searchQuery
    private SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences, key) -> {
        if (key.equals("searchQuery")) {
            String newSearchQuery = sharedPreferences.getString("searchQuery", "");
            if (!newSearchQuery.equals(currentSearchQuery)) {
                currentSearchQuery = newSearchQuery;
                Category category = new Category(0, "best-foods"); // Replace with your actual category name
                presenter.getProductsFiltered(category, currentSearchQuery);
            }
        }
    };

    private String getSearchQuery() {
        // Retrieve the searchQuery from shared preferences
        return sharedPreferences.getString("searchQuery", "");
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        adapter.updateProducts(products); // Update the products list in the adapter
    }
}
