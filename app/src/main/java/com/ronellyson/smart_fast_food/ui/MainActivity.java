package com.ronellyson.smart_fast_food.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentHomePage;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentProductCartPage;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String CART_BUTTON_CLICKED_KEY = "cartButtonClicked";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Verifica se a chave CART_BUTTON_CLICKED_KEY já existe no SharedPreferences
        // Caso não exista, definimos o valor como false
        if (!sharedPreferences.contains(CART_BUTTON_CLICKED_KEY)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CART_BUTTON_CLICKED_KEY, false);
            editor.apply();
        }

        boolean isCartButtonClicked = sharedPreferences.getBoolean(CART_BUTTON_CLICKED_KEY, false);

        if (savedInstanceState == null) {
            updateCartButtonState(isCartButtonClicked);
        }
    }

    public void updateCartButtonState(boolean isCartButtonClicked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CART_BUTTON_CLICKED_KEY, isCartButtonClicked);
        editor.apply();

        if (isCartButtonClicked) {
            showProductCartPageFragment();
        } else {
            showHomePageFragment();
        }
    }

    public void showHomePageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentProductCartPage productCartPageFragment = (FragmentProductCartPage) fragmentManager.findFragmentByTag("ProductCartPage");
        if (productCartPageFragment != null) {
            fragmentTransaction.remove(productCartPageFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("HomePage");
        if (fragment == null) {
            fragmentTransaction.add(R.id.container_root, FragmentHomePage.newInstance(), "HomePage");
        }

        fragmentTransaction.commit();
    }

    public void showProductCartPageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentHomePage homePageFragment = (FragmentHomePage) fragmentManager.findFragmentByTag("HomePage");
        if (homePageFragment != null) {
            fragmentTransaction.remove(homePageFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("ProductCartPage");
        if (fragment == null) {
            FragmentProductCartPage productCartPageFragment = FragmentProductCartPage.newInstance();
            fragmentTransaction.add(R.id.container_root, productCartPageFragment, "ProductCartPage");
        }

        fragmentTransaction.commit();
    }
}
