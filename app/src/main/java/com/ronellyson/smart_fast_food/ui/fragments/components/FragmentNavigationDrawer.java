package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
                Log.d("btnOpenDrawer", String.valueOf(sharedPreferences.getBoolean("isNavigationDrawerOpen", false)));

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
                Log.d("btnOpenDrawer", String.valueOf(sharedPreferences.getBoolean("isNavigationDrawerOpen", false)));

                // Define o estado do Navigation Drawer como fechado no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNavigationDrawerOpen", false);
                editor.apply();
            }
        });

        return rootView;
    }
}
