package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ronellyson.smart_fast_food.R;

public class FragmentProductCard extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PRODUCT_CARD_ADDED_KEY = "productCardAdded";

    private SharedPreferences sharedPreferences;
    private Button addButton;

    public static FragmentProductCard newInstance() {
        return new FragmentProductCard();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_card, container, false);

        return rootView;
    }
}



