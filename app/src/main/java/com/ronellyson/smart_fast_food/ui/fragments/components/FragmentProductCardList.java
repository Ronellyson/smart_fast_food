package com.ronellyson.smart_fast_food.ui.fragments.components;

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
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCardListAdapter;
import com.ronellyson.smart_fast_food.ui.presenters.ProductCardListPresenter;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductCardList extends Fragment {
    private RecyclerView recyclerView;
    private ProductCardListAdapter adapter;

    private ProductCardListPresenter presenter;
    private List<Product> products;
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

        adapter = new ProductCardListAdapter(products);
        recyclerView.setAdapter(adapter);

        // Create an instance of the presenter
        presenter = new ProductCardListPresenter(this);

        // Call the method to fetch products by category
        Category category = new Category(0, "best-foods"); // Replace with your actual category name
        presenter.getProductsByCategory(category);
    }


    public void setProducts(List<Product> products) {
        this.products = products;
        adapter.updateProducts(products); // Update the products list in the adapter
    }
}

