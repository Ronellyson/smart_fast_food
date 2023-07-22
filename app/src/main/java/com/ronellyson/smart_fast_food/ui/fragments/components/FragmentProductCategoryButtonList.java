package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductCategoryButtonList extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PRODUCT_CATEGORY_BUTTON_SELECTED_KEY = "productCategoryButtonSelected";

    private SharedPreferences sharedPreferences;

    public static FragmentProductCategoryButtonList newInstance() {
        return new FragmentProductCategoryButtonList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_category_button_list, container, false);

        // Inicializar RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.product_category_button_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Inicializar lista de categorias de produtos
        List<Category> productCategoryList = new ArrayList<>();
        productCategoryList.add(new Category(1, "best-foods"));
        productCategoryList.add(new Category(2, "breads"));
        productCategoryList.add(new Category(3, "burgers"));
        productCategoryList.add(new Category(4, "chocolates"));
        productCategoryList.add(new Category(5, "desserts"));
        productCategoryList.add(new Category(6, "drinks"));
        productCategoryList.add(new Category(7, "fried-chicken"));
        productCategoryList.add(new Category(8, "ice-cream"));
        productCategoryList.add(new Category(9, "pizzas"));
        productCategoryList.add(new Category(10, "porks"));
        productCategoryList.add(new Category(11, "sandwiches"));
        productCategoryList.add(new Category(12, "sausages"));
        productCategoryList.add(new Category(13, "steaks"));
        productCategoryList.add(new Category(14, "our-foods"));

        // Inicializar o SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Configurar Adapter
        ProductCategoryAdapter adapter = new ProductCategoryAdapter(productCategoryList, sharedPreferences);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static String getProductCategoryButtonSelectedKey() {
        return PRODUCT_CATEGORY_BUTTON_SELECTED_KEY;
    }
}
