package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ronellyson.smart_fast_food.R;

public class FragmentOrderSummary extends Fragment {
    private SharedPreferences sharedPreferences;

    public static FragmentOrderSummary newInstance(SharedPreferences sharedPreferences) {
        FragmentOrderSummary fragment = new FragmentOrderSummary();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.order_summary, container, false);
        return view;
    }
}
