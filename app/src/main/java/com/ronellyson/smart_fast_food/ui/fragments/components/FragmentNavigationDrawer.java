package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ronellyson.smart_fast_food.R;

public class FragmentNavigationDrawer extends Fragment {
    private SharedPreferences sharedPreferences;
    private static final String SELECTED_NAVIGATION_OPTION = "selectedNavigationOption";
    private int selectedScreen = -1;

    public static FragmentNavigationDrawer newInstance(SharedPreferences sharedPreferences) {
        FragmentNavigationDrawer fragment = new FragmentNavigationDrawer();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.navigation_drawer, container, false);

        ImageButton closeDrawerButton = rootView.findViewById(R.id.close_drawer_button);
        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como fechado no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNavigationDrawerOpen", false);
                editor.apply();
            }
        });

        Button closeDrawerAreaButton = rootView.findViewById(R.id.close_drawer_area_button);
        closeDrawerAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como fechado no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNavigationDrawerOpen", false);
                editor.apply();
            }
        });

        Button homePageButton = rootView.findViewById(R.id.home_page_button);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como aberto no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SELECTED_NAVIGATION_OPTION, homePageButton.getText().toString().toLowerCase().replace(" ","_"));
                editor.apply();
            }
        });

        Button orderHistoryButton = rootView.findViewById(R.id.order_history_button);
        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como aberto no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SELECTED_NAVIGATION_OPTION, orderHistoryButton.getText().toString().toLowerCase().replace(" ","_"));
                editor.apply();
            }
        });

        Button addressManagementButton = rootView.findViewById(R.id.address_management_button);
        addressManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como aberto no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SELECTED_NAVIGATION_OPTION, addressManagementButton.getText().toString().toLowerCase().replace(" ","_"));
                editor.apply();
            }
        });

        Button paymentManagementButton = rootView.findViewById(R.id.payment_management_button);
        paymentManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como aberto no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SELECTED_NAVIGATION_OPTION, paymentManagementButton.getText().toString().toLowerCase().replace(" ","_"));
                editor.apply();
            }
        });

        return rootView;
    }
}
